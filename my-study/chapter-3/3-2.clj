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
; くるくるする遅延シーケンスをくれる。
(take 10 (cycle (range 3)))

; interleave
; Scala で言う zip ...とはちょっと違うか。交互にはさみ混んでいく感じだ。
(defn whole-numbers [] (iterate inc 1))
(interleave (whole-numbers) ["A" "B" "C" "D" "E" "F"])

; interpose
; interleave の逆っぽいことをする
(interpose "," ["apples" "bananas" "grapes"])

; interpose のところから読む！

; 2. シーケンスをフィルタする関数
; 3. シーケンスに対する述語
; 4. シーケンスを変換する関数
