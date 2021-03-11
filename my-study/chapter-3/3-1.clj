; シーカブルの基本の関数。まずはリストに対して適用してみよう。
(first '(1 2 3))

(rest '(1 2 3))

(cons 0 '(1 2 3))

; Clojure では ベクタにもつかえるぜ！
(first [1 2 3])

(rest [1 2 3])

(cons 0 [1 2 3])


; 適用結果はクラス的に言うと適用前のコレクションではなくシーケンスだよ。
(class (rest [1 2 3]))
; clojure.lang.PersistentVector$ChunkedSeq


; マップとセットにも使えるぜ！ただし順序をあてにするなよ！
(first {:fname "Aaron" :lname "Bedra"})

(rest {:fname "Aaron" :lname "Bedra"})

(cons [:mname "James"] {:fname "Aaron" :lname "Bedra"})

(first #{:the :quick :brown :fox})

; 順序を保証したければこうする：
(first (sorted-map :c 3 :b 2 :a 1))
(first (sorted-set :the :quick :brown :fox))


; シーカブルの関数もう2つ。
(conj '(1 2 3) :a)
(conj '(1 2 3) :a :b)
(into '(1 2 3) '(:a :b :c))
(into '(1 2 3) #{:the :quick :brown :fox})

(conj [1 2 3] :a)
(conj [1 2 3] :a :b)
(into [1 2 3] '(:a :b :c))
(into [1 2 3] #{:the :quick :brown :fox})

; conj はコレクションと入れたい要素の結合だな。
; into はコレクション同士の結合。
; 具体的なデータ構造に応じて、効率的に結合してくれる！

; 注意。
; 上記の結果はいずれも REPL にて リストつまり () で返ってきてるように見えるが、
; あくまで関数適用の結果は上述した通りシーケンスである。REPLで表示するためにリストとして表示しているというだけ。