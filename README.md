clojure-dynamic
===============

Examples of functional dynamic programming in Clojure

Use
---

Assuming you have maven installed, you're good to go:

    git clone git@github.com:m0wfo/clojure-dynamic.git
    cd clojure-dynamic
    mvn clojure:repl

then play with some of the examples:

    (require '[com.mowforth.dynamic.fib :as fib])
    user=> (time (fib/pure 40))
    "Elapsed time: 4442.101648 msecs"
    102334155

...etc
