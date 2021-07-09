(ns clojure-spinners.util.wide-char-ranges
  (:require
    [clojure.string :as str]))

(def wide-char-ranges
  [[4352 4447]     ;; 1100 ... 115F
   [8986 8987]     ;; 231A .... 231B
   [9001 9002]     ;; 2329 .... 232A
   [9193 9196]     ;; 23E9 .... 23EC
   [9200 9200]     ;; 23F0 .... 23F0
   [9203 9203]     ;; 23F3 .... 23F3
   [9725 9726]     ;; 25FD .... 25FE
   [9748 9749]     ;; 2614 .... 2615
   [9800 9811]     ;; 2648 .... 2653
   [9855 9855]     ;; 267F .... 267F
   [9875 9875]     ;; 2693 .... 2693
   [9889 9889]     ;; 26A1 .... 26A1
   [9898 9899]     ;; 26AA .... 26AB
   [9917 9918]     ;; 26BD .... 26BE
   [9924 9925]     ;; 26C4 .... 26C5
   [9934 9934]     ;; 26CE .... 26CE
   [9940 9940]     ;; 26D4 .... 26D4
   [9962 9962]     ;; 26EA .... 26EA
   [9970 9971]     ;; 26F2 .... 26F3
   [9973 9973]     ;; 26F5 .... 26F5
   [9978 9978]     ;; 26FA .... 26FA
   [9981 9981]     ;; 26FD .... 26FD
   [9989 9989]     ;; 2705 .... 2705
   [9994 9995]     ;; 270A .... 270B
   [10024 10024]   ;; 2728 .... 2728
   [10060 10060]   ;; 274C .... 274C
   [10062 10062]   ;; 274E .... 274E
   [10067 10069]   ;; 2753 .... 2755
   [10071 10071]   ;; 2757 .... 2757
   [10133 10135]   ;; 2795 .... 2797
   [10160 10160]   ;; 27B0 .... 27B0
   [10175 10175]   ;; 27BF .... 27BF
   [11035 11036]   ;; 2B1B .... 2B1C
   [11088 11088]   ;; 2B50 .... 2B50
   [11093 11093]   ;; 2B55 .... 2B55
   [11904 11929]   ;; 2E80 .... 2E99
   [11931 12019]   ;; 2E9B .... 2EF3
   [12032 12245]   ;; 2F00 .... 2FD5
   [12272 12283]   ;; 2FF0 .... 2FFB
   [12288 12288]   ;; 3000 .... 3000
   [12289 12350]   ;; 3001 .... 303E
   [12353 12438]   ;; 3041 .... 3096
   [12441 12543]   ;; 3099 .... 30FF
   [12549 12591]   ;; 3105 .... 312F
   [12593 12686]   ;; 3131 .... 318E
   [12688 12771]   ;; 3190 .... 31E3
   [12784 12830]   ;; 31F0 .... 321E
   [12832 12871]   ;; 3220 .... 3247
   [12880 19903]   ;; 3250 .... 4DBF
   [19968 42124]   ;; 4E00 .... A48C
   [42128 42182]   ;; A490 .... A4C6
   [43360 43388]   ;; A960 .... A97C
   [44032 55203]   ;; AC00 .... D7A3
   [63744 64255]   ;; F900 .... FAFF
   [65040 65049]   ;; FE10 .... FE19
   [65072 65106]   ;; FE30 .... FE52
   [65108 65126]   ;; FE54 .... FE66
   [65128 65131]   ;; FE68 .... FE6B
   [65281 65376]   ;; FF01 .... FF60
   [65504 65510]   ;; FFE0 .... FFE6
   [94176 94180]   ;; 16FE0 .... 16FE4
   [94192 94193]   ;; 16FF0 .... 16FF1
   [94208 100343]  ;; 17000 .... 187F7
   [100352 101589] ;; 18800 .... 18CD5
   [101632 101640] ;; 18D00 .... 18D08
   [110592 110878] ;; 1B000 .... 1B11E
   [110928 110930] ;; 1B150 .... 1B152
   [110948 110951] ;; 1B164 .... 1B167
   [110960 111355] ;; 1B170 .... 1B2FB
   [126980 126980] ;; 1F004 .... 1F004
   [127183 127183] ;; 1F0CF .... 1F0CF
   [127374 127374] ;; 1F18E .... 1F18E
   [127377 127386] ;; 1F191 .... 1F19A
   [127488 127490] ;; 1F200 .... 1F202
   [127504 127547] ;; 1F210 .... 1F23B
   [127552 127560] ;; 1F240 .... 1F248
   [127568 127569] ;; 1F250 .... 1F251
   [127584 127589] ;; 1F260 .... 1F265
   [127744 127776] ;; 1F300 .... 1F320
   [127789 127797] ;; 1F32D .... 1F335
   [127799 127868] ;; 1F337 .... 1F37C
   [127870 127891] ;; 1F37E .... 1F393
   [127904 127946] ;; 1F3A0 .... 1F3CA
   [127951 127955] ;; 1F3CF .... 1F3D3
   [127968 127984] ;; 1F3E0 .... 1F3F0
   [127988 127988] ;; 1F3F4 .... 1F3F4
   [127992 128062] ;; 1F3F8 .... 1F43E
   [128064 128064] ;; 1F440 .... 1F440
   [128066 128252] ;; 1F442 .... 1F4FC
   [128255 128317] ;; 1F4FF .... 1F53D
   [128331 128334] ;; 1F54B .... 1F54E
   [128336 128359] ;; 1F550 .... 1F567
   [128378 128378] ;; 1F57A .... 1F57A
   [128405 128406] ;; 1F595 .... 1F596
   [128420 128420] ;; 1F5A4 .... 1F5A4
   [128507 128591] ;; 1F5FB .... 1F64F
   [128640 128709] ;; 1F680 .... 1F6C5
   [128716 128716] ;; 1F6CC .... 1F6CC
   [128720 128722] ;; 1F6D0 .... 1F6D2
   [128725 128727] ;; 1F6D5 .... 1F6D7
   [128747 128748] ;; 1F6EB .... 1F6EC
   [128756 128764] ;; 1F6F4 .... 1F6FC
   [128992 129003] ;; 1F7E0 .... 1F7EB
   [129292 129338] ;; 1F90C .... 1F93A
   [129340 129349] ;; 1F93C .... 1F945
   [129351 129400] ;; 1F947 .... 1F978
   [129402 129483] ;; 1F97A .... 1F9CB
   [129485 129535] ;; 1F9CD .... 1F9FF
   [129648 129652] ;; 1FA70 .... 1FA74
   [129656 129658] ;; 1FA78 .... 1FA7A
   [129664 129670] ;; 1FA80 .... 1FA86
   [129680 129704] ;; 1FA90 .... 1FAA8
   [129712 129718] ;; 1FAB0 .... 1FAB6
   [129728 129730] ;; 1FAC0 .... 1FAC2
   [129744 129750] ;; 1FAD0 .... 1FAD6
   [131072 196605] ;; 20000 .... 2FFFD
   [196608 262141] ;; 30000 .... 3FFFD
   ])

(defn code-point->int
  [code-point]
  (Integer/parseInt code-point 16))

(defn wide-char?
  "code-point := int"
  [code-point]
  (let [[min-point _] (first wide-char-ranges)
        [_ max-point] (last wide-char-ranges)]
    (if (< min-point code-point max-point)
      (loop [vecs wide-char-ranges]
        (if-let [[s e] (first vecs)]
          (if (<= s code-point e)
            true
            (recur (rest vecs)))
          false))
      false)))
