(ns com.mowforth.dynamic.fib
  (:use [clojure.core.match :only (match)]))

(defn pure [n]
  "A simple recursive definition of a Fibonacci function."
  (match [n]
         [0] 0
         [1] 1
         [_] (+ (pure (- n 1)) (pure (- n 2)))))

(defn top-down [n]
  "A top-down implementation using memoization."
  (with-redefs [pure (memoize pure)] ; we need to rebind the recursive call inside 'pure'
    (pure n)))

(defn bottom-up [n]
  "A bottom-up dynamic implementation"
    (map first
      (iterate
        (fn [[x y]]
          [y (+ x y)]) [0 1])))
