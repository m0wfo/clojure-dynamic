(ns com.mowforth.dynamic.fib.pure
  (:gen-class)
  (:use [clojure.core.match :only (match)]))

(defn pure [n]
  "A pure recursive definition of a Fibonacci function."
  (match [n]
         [0] 0
         [1] 1
         [_] (+ (pure (- n 1)) (pure (- n 2)))))



(defn top-down [n]
  "A top-down dynamic implementation using memoization."
  (with-redefs [pure (memoize pure)]
    (pure n)))

(defn bottom-up [n]
  "A bottom-up dynamic implementation"
  ; TODO
  )