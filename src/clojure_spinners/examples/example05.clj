(ns clojure-spinners.examples.example05
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
