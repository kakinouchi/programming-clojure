; (atom などの リファレンスは特別として)
; ミュータブルな変数なしに Clojure はどうプログラムを組んでいくか？

; Apacche Commons の StringUtils.indexOfAny の Clojure ver. を作ることで体験しよう。

(defn indexed [coll] (map-indexed vector coll))

(indexed "abcde")


(defn index-filter [pred coll]
  (when pred
    (for [[idx elt] (indexed coll) :when (pred elt)] idx)))

(index-filter #{\a \b} "abcdbbb")
; まぁこの辺は Scala は Haskell のリスト内包表記の強力さと同様だな。すごいよね。

(defn index-filter2 [pred coll]
  (for [[idx elt] (indexed coll) :when (pred elt)] idx))
(index-filter2 #{\a \b} "abcdbbb")

; でも セット が関数のようにはたらくのが Clojure の妙味だ。
; つまり、(詳しくは 3-2 でやるが) シーケンス内包表記の :when 節における
; (pred elt) のところで、elt を含むか？という predicate として機能している。