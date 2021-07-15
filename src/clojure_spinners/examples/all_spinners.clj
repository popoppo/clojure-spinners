(ns clojure-spinners.examples.all-spinners
  (:require
    [clojure-spinners.core :as s]))

(let [ks (sort (keys (s/get-spinners)))
      s (s/create! {:spinner (first ks)
                    :text (first ks)})]
  (s/start! s)
  (loop [ks (rest ks)]
    (Thread/sleep 2000)
    (s/change-spinner! {:spinner (first ks)
                        :text (first ks)})
    (when-let [r (seq (rest ks))]
      (recur r)))
  (s/stop! s))
