(ns clojure-spinners.examples.example02
  (:require
    [clojure-spinners.core :as s]))

(let [opts {:spinner :hearts
            :text "Hearts!!"}
      s (s/create! opts)]
  (s/start! s)
  (Thread/sleep 2000)
  (s/stop! s))

(s/spin! (Thread/sleep 2000))

(s/spin! {:spinner :arrow
          :text "This works too!!"
          :persist true}
         (Thread/sleep 2000))

