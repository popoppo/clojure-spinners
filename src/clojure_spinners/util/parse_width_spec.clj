(ns clojure-spinners.util.parse-width-spec
  (:require
    [clojure.java.io :as io]
    [clojure.string :as str]))

(def spec-file-name "EastAsianWidth.txt")
(def out-file-name "src/clojure_spinners/util/wide_char_ranges.clj")

(defn parse-spec
  [file-name]
  (with-open [r (io/reader file-name)]
    (doall
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
          acc)))))

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
        [s2 e2] (str/split code-range2 #"\.\.")
        e2 (or e2 s2)]
    (= (+ 1 (code-point->int e1)) (code-point->int s2))))

(defn merge-ranges
  [code-range1 code-range2]
;; should be range1 < range2
  (let [[s1 e1] (str/split code-range1 #"\.\.")
        e1 (or e1 s1)
        [s2 e2] (str/split code-range2 #"\.\.")
        e2 (or e2 s2)]
    (format "%s..%s" s1 e2)))

(defn merge-range-seq
  [ranges]
  (loop [current (->range (first ranges))
         ranges (rest ranges)
         result []]
    #_(println current ranges result)
    (if-let [next (->range (first ranges))]
      (if (can-merge? current next)
        (recur (merge-ranges current next) (rest ranges) result)
        (recur next (rest ranges) (conj result current)))
      (conj result current))))

(let [r (parse-spec spec-file-name)
      fulls (:full r)
      wides (:wide r)]
  #_(with-open [w (io/writer out-file-name)]
    (.write w "[\n")
    (doseq [l (concat (merge-range-seq fulls)
                      (merge-range-seq wides))]
      (.write w (format "\"%s\"\n" l)))
    (.write w "]\n"))

  #_(->> (concat (merge-range-seq fulls)
               (merge-range-seq wides))
       (interpose \newline)
       (apply str)
       (#(str "[" % "]"))
       (spit out-file-name))
  (spit out-file-name (pr-str (concat (merge-range-seq fulls)
                                      (merge-range-seq wides)))))
