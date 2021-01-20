; シーケンスライブラリは、ざっくり次の4つにわけると理解しやすい。(厳密なものでなく便宜上のもの)
; 
; 1. シーケンスを生成する関数
; 2. シーケンスをフィルタする関数
; 3. シーケンスに対する述語
; 4. シーケンスを変換する関数




; 1. シーケンスを生成する関数
 
; range
(range 10) ; end
(class (range 10)) ; どうやらこれは シーケンスではなく Range らしい
; clojure.lang.LongRange
(range) ; infinite lazy seq
(range 10) ; end
(range 10 20) ; start and stop
(range 10 20 2) ; start, stop, and step

; repeat
(repeat 5 2) ; return 2 sequence in length 5
(repeat 10 "x")
(repeat "x") ; return "x" infinite sequence
(take 10 (repeat "x"))

; iterate 
; (これは自分の勝手な解釈だが
; 引数に関数を渡せるように repeat を拡張したという見方がわかりやすいな)
(iterate inc 1)
(take 10 (iterate inc 1))

(take 10 (iterate #(+ % 2) 1))
; おぉ！これ自作してみたんだけどうまく動いた！やった！！きもちいい！！
; ようは inc のところに引数一つ取る関数わたせばいいんでしょと直感した。

; cycle
; 有限のシーケンスをインプットにして、循環する無限の遅延シーケンスをくれる。
(take 10 (cycle (range 3)))

; interleave
; Scala で言う zip ...とはちょっと違うか。交互にはさみ混んでいく感じだ。
(defn whole-numbers [] (iterate inc 1))
(interleave (whole-numbers) ["A" "B" "C" "D" "E" "F"])

; interpose
; interleave の逆っぽいことをする
(interpose "," ["apples" "bananas" "grapes"])
(str (interpose "," ["apples" "bananas" "grapes"]))
(apply str (interpose "," ["apples" "bananas" "grapes"]))

; (apply str (interpose separator seq)) はよく使うので、join 関数が用意されてる。
(use '[clojure.string :only (join)])
(join "," ["apples" "bananas" "grapes"])

; str の仕様の復習。以下の結果はおなじなのだった。
; リストの要素を "ばらして" 関数に渡したい時に apply するのであった。
(apply str [1 2 3])
(str 1 2 3)

; 具体的なデータ構造に変換する関数
(list 1 2 3)
(vector 1 2 3)
(vec [1 2 3])
; vector と引数の取り方が違うことに注意。まぁむしろ別の渡し方をしたくなるから2つの "入り口" が用意されてるんだろう。
(hash-set 1 2 3)
(set [1 2 3])
; 引数の取り方が（ry
(hash-map 1 2 3 4)




; 2. シーケンスをフィルタする関数

; 基本は filter.
(take 10 (filter even? (whole-numbers)))
(take 10 (filter odd? (whole-numbers)))

; take-while
(take-while (complement #{\a \i \u \e \o}) "the-quick-brown-fox")
; ちょっと補足。complement は 述語を逆にする。(true を false に、false を true に。)
(#{\a \e \i \o \u} \a) ; \a
(#{\a \e \i \o \u} \j) ; nil
((complement #{\a \e \i \o \u}) \a) ; false
((complement #{\a \e \i \o \u}) \j) ; true

; drop-while
(drop-while (complement #{\a \i \u \e \o}) "the-quick-brown-fox")

; もっとかんたんな例だと仕様が理解しやすい：
(take-while even? '(4 2 3 6))
; (4 2)
; predicate が false になるまで take するってことっぽい。take した seq を返す。
(drop-while even? '(4 2 3 6))
; (3 6)
; predicate が false になるまで drop するってことっぽい。残りの drop して残った seq を返す。
; つまり、take-while と drop-while で "止まる" 条件は一緒で、返却するのが違う。


; split-at
(range 10 20 2) ; (10 12 14 16 18)
(split-at 3 (range 10 20 2)) ; [(10 12 14) (16 18)]

; split-with (split-at の述語版)
(defn divisible-by-3 [x] (= (rem x 3) 0))
(divisible-by-3 3)
(divisible-by-3 4)
(divisible-by-3 5)
(divisible-by-3 6)

(split-with #(= (rem % 3) 0) (range 10 20 2))
(split-with divisible-by-3 (range 10 20 2))
(split-with #(<= % 15) (range 10 20 2))

; split-with の挙動がわからん！！！
; あ！VSCode 上でフォーカスあててドキュメント読んだらわかった！
; (take-while pred coll) と (drop-while pred coll) にわけるのね！！！であれば理解！
; だとすると最後の例が一番いい例だな。#(<= % 15) の例。




; 3. シーケンスに対する述語
; こっから！！！
; 4. シーケンスを変換する関数