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
  )
