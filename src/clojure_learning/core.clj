(ns clojure-learning.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

;; Chapter 3: Do Things: A Clojure Crash Course
;; https://www.braveclojure.com/do-things/

;; Use the str, vector, list, hash-map, and hash-set functions.
;; -> str
(str "Hi!, Clojure" " and Calva" " and something")
;; -> vector
(get [8 7 "Hi" {:a 1}] 3)
(vector "Maria" 34.2 "moon")
(conj [3 4 5] 9.8)
;; -> list
(conj '(7 6 2 {:a 1 :b 4}) "Hi")
(nth '(:a :b :c) 1)
(list 1 "two" 3 4 {5 6} {7 8})
;; -> hash-map
(hash-map :one 1 :two 2 :three 3)
(get-in {:one 1 :two {:calva "calva" :clj "clojure"}} [:two :clj])
;; -> functions


;; Write a function that takes a number and adds 100 to it.
(defn add100
  [x]
  (+ x 100))

(add100 2)
