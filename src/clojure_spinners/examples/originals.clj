(ns clojure-spinners.examples.originals
  (:require
    [clojure-spinners.core :as s]))

(def original-spinners
  {:orig1 {:interval 200
           :frames ["<" "V" ">" "A"]}
   :orig2 {:interval 200
           :frames
           [(format "\u3000\u3000\u3000\u3000%c%c" (int 0x1f6a2) (int 0x1f4a8))
            (format "\u3000\u3000\u3000%c%c\u3000" (int 0x1f6a2) (int 0x1f4a8))
            (format "\u3000\u3000%c%c\u3000\u3000" (int 0x1f6a2) (int 0x1f4a8))
            (format "\u3000%c%c\u3000\u3000\u3000" (int 0x1f6a2) (int 0x1f4a8))
            (format "%c%c\u3000\u3000\u3000\u3000" (int 0x1f6a2) (int 0x1f4a8))]}})

(s/load-spinners original-spinners)

(let [opts {:spinner :orig1
            :text " Original one!!"}
      s (s/create! opts)]
  (s/start! s)
  (Thread/sleep 2000)
  (s/change-spinner! {:spinner :orig2 :text " And one more."})
  (Thread/sleep 2000)
  (s/stop! s))

