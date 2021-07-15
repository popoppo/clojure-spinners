(ns clojure-spinners.examples.basics
  (:require
    [clojure-spinners.core :as s]))

(let [opts {:spinner :balloon
            :text " Text spinner"}
      s (s/create! opts)]
  (s/start! s)
  (Thread/sleep 4000)
  (s/change-spinner! {:spinner :balloon
                      :text " Colored"
                      :color :magenta})
  (Thread/sleep 4000)
  (s/insert-msg "and ...")
  (s/change-spinner! {:spinner :hearts
                      :text " Any unicode chars"})
  (Thread/sleep 4000)
  (s/stop! s {:persist true})
  (println "😄"))

