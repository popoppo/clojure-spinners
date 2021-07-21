(ns clojure-spinners.examples.country-flags
  (:require
    [clojure-spinners.core :as s]))

(def country-flags
  {:flags {:interval 200
           :frames ["\ud83c\udde6\ud83c\uddeb"
                    "\ud83c\uddff\ud83c\uddfc"]}
   ;; If you want to use Regional Indicator Symbols as they are,
   ;; put \u200b (Zero Width Space) or \u200c (Zero Width Non-Joiner)
   ;; between RISs.
   :riss {:interval 200
          :frames ["\ud83c\udde6\u200b\ud83c\uddeb"
                   "\ud83c\uddff\u200c\ud83c\uddfc"]}})

(s/load-spinners country-flags)

(let [opts {:spinner :flags
            :text "Contry flags"}
      s (s/create! opts)]
  (s/start! s)
  (Thread/sleep 4000)
  (s/change-spinner! {:spinner :riss
                      :text "RISs"})
  (Thread/sleep 4000)
  (s/stop! s))

