(ns clojure-spinners.examples.spin-and-persist
  (:require
    [clojure-spinners.core :as s]))

;; default is dots with no message
(let [s (s/create!)]
  (s/start! s)
  (Thread/sleep 2000)
  (s/stop! s))

(let [opts {:spinner :hearts
            :text "Hearts!!"}
      s (s/create! opts)]
  (s/start! s)
  (Thread/sleep 2000)
  (s/stop! s {:persist true}))

;; Do the same with spin!
(s/spin! (Thread/sleep 2000))

(s/spin! {:spinner :arrow
          :text "This works too!!"
          :persist true}
         (Thread/sleep 2000))

