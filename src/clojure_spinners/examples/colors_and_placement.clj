(ns clojure-spinners.examples.colors-and-placement
  (:require
    [clojure-spinners.core :as s]))

(s/spin! {:spinner :hearts
          :text "Spin with spin!"}
         (Thread/sleep 2000))

(s/spin! {:spinner :aesthetic
          :color :blue
          :text "Colored spinner"}
         (Thread/sleep 2000))

(s/spin! {:spinner :aesthetic
          :color :magenta
          :placement :right
          :text "Another placement=>"}
         (Thread/sleep 2000))

(let [c {:spinner :aesthetic
         :color :black
         :text "More colors"}
      s (s/create! c)]
  (s/start! s)
  (doall
    (for [r (range 0 256 80)
          g (range 0 256 80)
          b (range 0 256 80)]
      (do
        (Thread/sleep 100)
        (s/change-spinner! {:color [r g b]}))))
  (s/stop! s))
