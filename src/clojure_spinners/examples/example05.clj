(ns clojure-spinners.examples.example05
  (:require
    [clojure-spinners.core :as s]))

(s/spin! {:spinner :aesthetic
          :text "Spin with spin!"}
         (Thread/sleep 2000))
