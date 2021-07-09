(ns clojure-spinners.examples.example03
  (:require
    [clojure-spinners.core :as s]))

(let [opts {:spinner :clock
            :text "Spin!!"}
      s (s/create! opts)]
  (s/start! s)
  (s/insert-msg "Running")
  (Thread/sleep 2000)
  (s/change-spinner! {:spinner :monkey :text "Changed"})
  (Thread/sleep 2000)
  (s/insert-msg "Wrapping up")
  (s/change-spinner! {:spinner :christmas})
  (Thread/sleep 2000)
  (s/stop! s {:persist false :text "Done"}))

