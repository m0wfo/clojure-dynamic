(ns com.mowforth.dynamic.levenshtein
  (:use [clojure.core.match :only (match)]))

(defn pure [a b]
  "A simple recursive definition of the Levenshtein function"
  (match [(count a) (count b)]
         [0 _] (count b)
         [_ 0] (count a)
         [_ _] (let [cost (if (= (last a) (last b)) 0 1)]
                 (apply min [(+ (pure (butlast a) b) 1)
                             (+ (pure a (butlast b)) 1)
                             (+ (pure (butlast a) (butlast b)) cost)]))))

(defn top-down [a b]
  "A top-down implementation using memoization"
  (with-redefs [pure (memoize pure)]
    (pure a b)))

(defn bottom-up [a b]
  "A bottom-up dynamic implementation using two vectors"
  (match [(count a) (count b)]
    ; couple of degenerate cases
    [0 _] (count b)
    [_ 0] (count a)
    [_ _] (let [count-a (count a)
                count-b (count b)
                init-v0 (vec (range (+ 1 count-b)))
                init-v1 (vec (take (+ 1 count-b) (iterate (constantly 0) 0)))]
            (last (take (count a)
                    (iterate (fn [[i v0 v1]]
                               (let [updated-v1 (assoc v1 0 i)
                                     inner (last (take (count b)
                                                   (iterate (fn [[j v1-inner]]
                                                              (let [cost (if (= (get a i) (get b j)) 0 1)
                                                                    min-val (min
                                                                              (+ 1 (get v1-inner j))
                                                                              (+ 1 (get v0 (+ 1 j)))
                                                                              (+ cost (get v0 j)))]
                                                                [(+ 1 j)
                                                                 (assoc v1-inner (+ 1 j) min-val)]))
                                                     [0 updated-v1])))]
                                 [(+ 1 i) (last inner) v0])) [0 init-v0 init-v1]))))))
