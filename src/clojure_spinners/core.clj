(ns clojure-spinners.core
  (:require
    [clojure-spinners.util.wide-char-ranges :as wcr]
    [clojure.edn :as edn]))

(def codes
  {:clear-line "\033[K"
   :cursor-show "\033[?25h"
   :cursor-hide "\033[?25l"
   :fg-reset "\033[39m"})

(def color-map
  {:black 0
   :red 1
   :green 2
   :yellow 3
   :blue 4
   :magenta 5
   :cyan 6
   :white 7})

;; TODO: check ANSI_COLORS_DISABLED

;; Global conf
(def spinner-conf (atom {}))

;; Load built-in spinners
(def spinners (atom (-> "spinners.edn" slurp edn/read-string)))

(defn split-string-by-code-points
  [s]
  (loop [result []
         offset 0]
    (let [next-offset (.offsetByCodePoints s offset 1)
          uc (.substring s offset next-offset)
          r (conj result uc)]
      (if (< next-offset (.length s))
        (recur r next-offset)
        r))))

(defn char-width
  [c]
  (let [cnt (.length c)]
    (condp = cnt
      1 (if (#{8203 8204 8205 65038 65039} (int (.charAt c 0))) ;; \u200[bcd] \ufe0[ef]
          0
          (if (wcr/wide-char? (.codePointAt c 0)) 2 1))
      2 (if (Character/isSurrogatePair (.charAt c 0) (.charAt c 1))
          (cond
            (wcr/wide-char? (.codePointAt c 0)) 2
            (wcr/ris? (.codePointAt c 0)) 2
            :else 1)
          2)
      2 ;; There might be exceptions?
      )))

(defn string-width
  [s]
  (loop [cnt 0
         chars (split-string-by-code-points s)]
    (if-let [c (first chars)]
      (let [w (char-width c)]
        (if (zero? w)
          (condp = (.codePointAt c 0)
            8205 (recur cnt (drop 2 chars))
            ;; 8203 8204 65038 65039
            (recur cnt (rest chars)))
          (if (and (wcr/ris? (.codePointAt c 0))
                   (second chars)
                   (wcr/ris? (.codePointAt (second chars) 0)))
            (recur (+ cnt w) (drop 2 chars)) ;; country flag (maybe)
            (recur (+ cnt w) (rest chars)))))
      cnt)))

(defn set-spinner-conf!
  [conf]
  (let [settings (get @spinners (keyword (:spinner conf)))
        max-width (->> (:frames settings)
                       ;; (map count)
                       (reduce (fn [acc frame]
                                 (conj acc (string-width frame)))
                               [])
                       (apply max))]
    (reset! spinner-conf (assoc conf
                                :spinner settings
                                :max-width max-width))))

(defn animate
  []
  (try
    (print (:cursor-hide codes))
    (loop [i 0]
      (let [interval (get-in @spinner-conf [:spinner :interval])
            frames (get-in @spinner-conf [:spinner :frames])
            text (get @spinner-conf :text "")
            max-width (get @spinner-conf :max-width)
            frame-idx (mod i (count frames))
            frame (nth frames frame-idx)
            color (if-let [c (:color @spinner-conf)]
                    (format "\033[38;5;%dm" (color-map c))
                    "")
            s (if (= (keyword (:placement @spinner-conf)) :right)
                (format "\r%s%s%s%s" text color frame (:fg-reset codes))
                (format (str "\r%" max-width "s%s\r%s%s%s") "" text color frame (:fg-reset codes)))]
        ;; Clear line, print spinner and message and flush
        (print "\r" (:clear-line codes))
        (print s)
        (flush)
        (Thread/sleep interval)
        (recur (inc i))))
    (catch Exception _
      (comment "stop! causes InterruptedException, just ignore it"))
    (finally
      (print (:cursor-show codes)))))

(defn create!
  ([] (create! {}))
  ([conf]
   (if (:spinner conf)
     (set-spinner-conf! conf)
     (set-spinner-conf! (merge {:spinner :dots} conf)))
   (doto (Thread. #(animate))
     (.setDaemon true))))

(defn start!
  [^Thread spinner]
  (when-not (.isAlive spinner)
    (.start spinner))
  nil)

(defn stop!
  [^Thread spinner & [option]]
  (when (.isAlive spinner)
    (doto spinner
      (.interrupt)
      (.join))
    (if (:persist option)
      (println "") ;; \n
      (do
        (print "\r")
        (print "\033[K\r"))))
  nil)

(defmacro spin!
  ([f]
   `(spin! {} ~f))
  ([opts f]
   `(let [s# (create! ~opts)
          _# (start! s#)
          v# ~f]
      (stop! s# {:persist (:persist ~opts)})
      v#)))

(defn get-spinners
  []
  @spinners)

(defn load-spinners
  [spinners-map]
  (swap! spinners merge spinners-map))

(defn change-spinner!
  [conf]
  (set-spinner-conf! (merge @spinner-conf conf)))

(defn insert-msg
  [msg]
  (println (format "\r%s%s" (:clear-line codes) msg)))
