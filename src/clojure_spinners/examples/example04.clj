(ns clojure-spinners.examples.examle04
  (:require
    [clojure-spinners.core :as s]))

(def original-spinners
  {:orig1 {:interval 200
           :frames ["<" "V" ">" "A"]}
   :orig2 {:interval 150
           :frames ["o" "O"]}})

(s/load-spinners original-spinners)

(let [opts {:spinner :orig1
            :text " This is Original!!"}
      s (s/create! opts)]
  (s/start! s)
  (Thread/sleep 2000)
  (s/change-spinner! {:spinner :orig2 :text " And one more."})
  (Thread/sleep 2000)
  (s/stop! s))

