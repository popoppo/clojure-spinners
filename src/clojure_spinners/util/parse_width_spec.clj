(ns clojure-spinners.util.parse-width-spec
  (:require
    [clojure.java.io :as io]
    [clojure.string :as str]))

(def spec-file-name "EastAsianWidth.txt")
(def emoji-file-name "BasicEmojis.txt")

(defn parse-width-spec
  [file-name]
  (with-open [r (io/reader file-name)]
    (loop [lines (line-seq r) #_(take 50 (line-seq r))
           acc {:full []
                :wide []}]
      (if-let [l (first lines)]
        (if (str/starts-with? l "#")
          (recur (rest lines) acc)
          (let [[code-point prop] (str/split l #"[; ]")
                acc (cond
                      ;;(= prop "A") (update acc :count-a (fnil inc 0))
                      (= prop "F") (update acc :full #(conj % code-point))
                      ;;(= prop "H") (update acc :count-h (fnil inc 0))
                      ;;(= prop "N") (update acc :count-n (fnil inc 0))
                      ;;(= prop "Na") (update acc :count-na (fnil inc 0))
                      (= prop "W") (update acc :wide #(conj % code-point))
                      :else acc)]
            (recur (rest lines) acc)))
        acc))))

(defn parse-emoji-codes
  [file-name]
  (with-open [r (io/reader file-name)]
    (loop [lines (line-seq r)
           codes []]
      (if-let [l (first lines)]
        (if (str/starts-with? l "#")
          (recur (rest lines) codes)
          (let [code (-> (str/split l #";")
                         first
                         str/trim
                         (str/split #" +")
                         first)]
            (recur (rest lines)
                   (conj codes (if (str/includes? code ".")
                                 code
                                 (str code ".." code))))))
        codes))))

(defn sort-code-points
  [l]
  (sort-by (fn [cp]
             (-> (str/split cp #"\.\.")
                 first
                 (Integer/parseInt 16)))
           l))

(defn code-point->int
  [code-point]
;; e.g. 3000
  (Integer/parseInt code-point 16))

(defn ->range
  [code-point]
  (when code-point
    (let [[s e] (str/split code-point #"\.\.")]
      (if e
        code-point ;; already range
        (format "%s..%s" s s)))))

(defn can-merge?
  [code-range1 code-range2]
;; should be range1 < range2
  (let [[s1 e1] (str/split code-range1 #"\.\.")
        e1 (or e1 s1)
        [s2 e2] (str/split code-range2 #"\.\.")]
    (or
      (= s1 s2) ;; => [s1 (max e1 e2)]
      (<= (code-point->int s2) (code-point->int e1)) ;; => [s1 e2]
      (= (+ 1 (code-point->int e1)) (code-point->int s2)))))

(defn merge-ranges
  [code-range1 code-range2]
  ;; should be range1 < range2
  (let [[s1 e1] (str/split code-range1 #"\.\.")
        e1 (or e1 s1)
        [s2 e2] (str/split code-range2 #"\.\.")
        e2 (or e2 s2)]
    (cond
      (= s1 s2) (format "%s..%s" s1 (if (< (code-point->int e1) (code-point->int e2)) e2 e1))
      :else (format "%s..%s" s1 e2))))

(defn merge-range-seq
  [ranges]
  (loop [current (->range (first ranges))
         ranges (rest ranges)
         result []]
    (if-let [next (->range (first ranges))]
      (if (can-merge? current next)
        (recur (merge-ranges current next) (rest ranges) result)
        (recur next (rest ranges) (conj result current)))
      (conj result current))))

(defn cp->int
  [range-seq]
  (map
    (fn [cp-range]
      (let [[s e] (str/split cp-range #"\.\.")]
        (format "[%d %d] ;; %s ... %s\n"
                (Integer/parseInt s 16) (Integer/parseInt e 16) s e)))
    range-seq))

(let [r (parse-width-spec spec-file-name)
      fulls (:full r)
      wides (:wide r)
      emoji-codes (-> emoji-file-name
                      parse-emoji-codes)]
  (println (-> (concat fulls wides emoji-codes)
               sort-code-points
               merge-range-seq
               distinct
               cp->int)))
