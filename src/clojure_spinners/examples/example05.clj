(ns clojure-spinners.examples.example05
  (:require
    [clojure-spinners.spinner :as s]))

(s/spin! {:text "Spin with spin!"}
         (Thread/sleep 2000))
