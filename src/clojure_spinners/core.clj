(ns clojure-spinners.core
  (:require
    [clojure-spinners.util.wide-char-ranges :as wcr]
    [clojure.edn :as edn]))

(def codes
  {:clear-line "\033[K"
   :cursor-show "\033[?25h"
   :cursor-hide "\033[?25l"})

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

(defn set-spinner-conf!
  [conf]
  (let [settings (get @spinners (keyword (:spinner conf)))
        max-width (->> (:frames settings)
                       ;; (map count)
                       (reduce (fn [acc frame]
                                 (let [cs (split-string-by-code-points frame)]
                                   (->> (reduce
                                          (fn [width c]
                                            (+ width
                                               (if (wcr/wide-char? (.codePointAt c 0)) 2 1)))
                                          0
                                          cs)
                                        (conj acc))))
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
            spfmt (str "\r%" max-width "s%s\r%s")]
        ;; Clear line, print spinner and message and flush
        (print "\r" (:clear-line codes))
        (print (format spfmt "" text frame))
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
      (println "")
      (do
        (print "\r")
        (print "\033[K\r")))
    (when-let [msg (:text option)]
      (println msg)))
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
