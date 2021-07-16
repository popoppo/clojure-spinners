(ns clojure-spinners.examples.basics
  (:require
    [clojure-spinners.core :as s]))

(let [opts {:spinner :bouncingBall
            :text " Text spinner"}
      s (s/create! opts)]
  (s/start! s)
  (Thread/sleep 4000)
  (s/change-spinner! {:spinner :bouncingBall
                      :text " Colored"
                      :color :green})
  (Thread/sleep 4000)
  (s/change-spinner! {:spinner :hearts
                      :text "Any unicode chars"})
  (Thread/sleep 4000)
  (s/stop! s)
  (println "😄👍"))
