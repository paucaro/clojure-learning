;; Chapter 3: Do Things: A Clojure Crash Course
;; https://www.braveclojure.com/do-things/

;; ----- Exercise 1
;; Use the str, vector, list, hash-map, and hash-set functions.
;; -> str
(str "Hi!, Clojure" " and Calva" " and something")
;; => "Hi!, Clojure and Calva and something"

;; -> vector
(get [8 7 "Hi" {:a 1}] 3)
;; => {:a 1}

(vector "Maria" 34.2 "moon")
;; => ["Maria" 34.2 "moon"]

(conj [3 4 5] 9.8)
;; => [3 4 5 9.8]

;; -> list
(conj '(7 6 2 {:a 1 :b 4}) "Hi")
;; => ("Hi" 7 6 2 {:a 1, :b 4})

(nth '(:a :b :c) 1)
;; => :b

(list 1 "two" 3 4 {5 6} {7 8})
;; => (1 "two" 3 4 {5 6} {7 8})

;; -> hash-map
(hash-map :one 1 :two 2 :three 3)
;; => {:one 1, :three 3, :two 2}

(get-in {:one 1 :two {:calva "calva" :clj "clojure"}} [:two :clj])
;; => "clojure"

;; -> functions
(defn function-a
  [name]
  (str "HI " name ", you're so pretty"))
(function-a "Pau")
;; => "HI Pau, you're so pretty"

;; anonymous function
((fn [name] (str "HI, " name)) "Cami")
;; => "HI, Cami"

(#(str "Hi, " %) "Luci")
;; => "Hi, Luci"


;; ----- Exercise 2
;; Write a function that takes a number and adds 100 to it.
(defn add100
  [x]
  (+ x 100))

(add100 2)
;; => 102

(add100 200)
;; => 300

(add100 12)
;; => 112


;; ----- Exercise 3
;;  Write a function, dec-maker, that works exactly like the function 
;;  inc-maker except with subtraction:
;;  (def dec9 (dec-maker 9))
;;  (dec9 10)
; => 1
(defn dec-maker
  [n]
  #(- % n))
(def dec9 (dec-maker 9))
(dec9 10)
;; => 1


;; ----- Exercise 4
;; Write a function, mapset, that works like map except the return value is a set:
;; (mapset inc [1 1 2 2])
; => #{2 3}
(defn mapset
  [f numbers]
  (set (map f numbers)))
(mapset inc [1 1 2 2])
;; => #{3 2}


;; ----- Exercise 5
;; Create a function that’s similar to symmetrize-body-parts except that it has 
;; to work with weird space aliens with radial symmetry. Instead of two eyes, 
;; arms, legs, and so on, they have five.
(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])
(defn radial-matching-part
  [part]
  (map
   (fn [arg]
     {:name (clojure.string/replace (:name part) #"^left-" arg)
      :size (:size part)})
   ["first-" "second-" "third-" "four-" "five-"]))
(defn radial-symmetry-body-parts
  [body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set (radial-matching-part part))))
          []
          body-parts))
(radial-symmetry-body-parts asym-hobbit-body-parts)
;; => [{:name "head", :size 3}
;;     {:name "four-eye", :size 1}
;;     {:name "five-eye", :size 1}
;;     {:name "first-eye", :size 1}
;;     {:name "second-eye", :size 1}
;;     {:name "third-eye", :size 1}
;;     {:name "second-ear", :size 1}
;;     {:name "first-ear", :size 1}
;;     {:name "five-ear", :size 1}
;;     {:name "third-ear", :size 1}
;;     {:name "four-ear", :size 1}
;;     {:name "mouth", :size 1}
;;     {:name "nose", :size 1}
;;     {:name "neck", :size 2}
;;     {:name "second-shoulder", :size 3}
;;     {:name "five-shoulder", :size 3}
;;     {:name "first-shoulder", :size 3}
;;     {:name "third-shoulder", :size 3}
;;     {:name "four-shoulder", :size 3}
;;     {:name "third-upper-arm", :size 3}
;;     {:name "four-upper-arm", :size 3}
;;     {:name "second-upper-arm", :size 3}
;;     {:name "first-upper-arm", :size 3}
;;     {:name "five-upper-arm", :size 3}
;;     {:name "chest", :size 10}
;;     {:name "back", :size 10}
;;     {:name "first-forearm", :size 3}
;;     {:name "four-forearm", :size 3}
;;     {:name "second-forearm", :size 3}
;;     {:name "five-forearm", :size 3}
;;     {:name "third-forearm", :size 3}
;;     {:name "abdomen", :size 6}
;;     {:name "second-kidney", :size 1}
;;     {:name "five-kidney", :size 1}
;;     {:name "third-kidney", :size 1}
;;     {:name "first-kidney", :size 1}
;;     {:name "four-kidney", :size 1}
;;     {:name "first-hand", :size 2}
;;     {:name "second-hand", :size 2}
;;     {:name "four-hand", :size 2}
;;     {:name "third-hand", :size 2}
;;     {:name "five-hand", :size 2}
;;     {:name "five-knee", :size 2}
;;     {:name "four-knee", :size 2}
;;     {:name "second-knee", :size 2}
;;     {:name "first-knee", :size 2}
;;     {:name "third-knee", :size 2}
;;     {:name "third-thigh", :size 4}
;;     {:name "five-thigh", :size 4}
;;     {:name "second-thigh", :size 4}
;;     {:name "four-thigh", :size 4}
;;     {:name "first-thigh", :size 4}
;;     {:name "third-lower-leg", :size 3}
;;     {:name "second-lower-leg", :size 3}
;;     {:name "four-lower-leg", :size 3}
;;     {:name "first-lower-leg", :size 3}
;;     {:name "five-lower-leg", :size 3}
;;     {:name "five-achilles", :size 1}
;;     {:name "second-achilles", :size 1}
;;     {:name "four-achilles", :size 1}
;;     {:name "third-achilles", :size 1}
;;     {:name "first-achilles", :size 1}
;;     {:name "third-foot", :size 2}
;;     {:name "five-foot", :size 2}
;;     {:name "four-foot", :size 2}
;;     {:name "first-foot", :size 2}
;;     {:name "second-foot", :size 2}]

;; ----- Exercise 6
;; Create a function that generalizes symmetrize-body-parts and the function 
;; you created in Exercise 5. The new function should take a collection of body 
;; parts and the number of matching body parts to add. If you’re completely 
;; new to Lisp languages and functional programming, it probably won’t be obvious 
;; how to do this. If you get stuck, just move on to the next chapter and 
;; revisit the problem later.

(defn generalize-matching-part
  [part n]
  (map
   (fn [arg]
     {:name (clojure.string/replace (:name part) #"^left-" (str arg "-"))
      :size (:size part)})
   (vec (range 1 (inc n)))))

(defn generalize-symmetry-body-parts
  [body-parts n]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set (generalize-matching-part part n))))
          []
          body-parts))
(generalize-symmetry-body-parts asym-hobbit-body-parts 5)

;; => [{:name "head", :size 3}
;;     {:name "5-eye", :size 1}
;;     {:name "3-eye", :size 1}
;;     {:name "2-eye", :size 1}
;;     {:name "1-eye", :size 1}
;;     {:name "4-eye", :size 1}
;;     {:name "3-ear", :size 1}
;;     {:name "1-ear", :size 1}
;;     {:name "2-ear", :size 1}
;;     {:name "4-ear", :size 1}
;;     {:name "5-ear", :size 1}
;;     {:name "mouth", :size 1}
;;     {:name "nose", :size 1}
;;     {:name "neck", :size 2}
;;     {:name "5-shoulder", :size 3}
;;     {:name "4-shoulder", :size 3}
;;     {:name "2-shoulder", :size 3}
;;     {:name "1-shoulder", :size 3}
;;     {:name "3-shoulder", :size 3}
;;     {:name "4-upper-arm", :size 3}
;;     {:name "5-upper-arm", :size 3}
;;     {:name "3-upper-arm", :size 3}
;;     {:name "2-upper-arm", :size 3}
;;     {:name "1-upper-arm", :size 3}
;;     {:name "chest", :size 10}
;;     {:name "back", :size 10}
;;     {:name "2-forearm", :size 3}
;;     {:name "4-forearm", :size 3}
;;     {:name "1-forearm", :size 3}
;;     {:name "5-forearm", :size 3}
;;     {:name "3-forearm", :size 3}
;;     {:name "abdomen", :size 6}
;;     {:name "2-kidney", :size 1}
;;     {:name "4-kidney", :size 1}
;;     {:name "5-kidney", :size 1}
;;     {:name "3-kidney", :size 1}
;;     {:name "1-kidney", :size 1}
;;     {:name "5-hand", :size 2}
;;     {:name "2-hand", :size 2}
;;     {:name "4-hand", :size 2}
;;     {:name "1-hand", :size 2}
;;     {:name "3-hand", :size 2}
;;     {:name "3-knee", :size 2}
;;     {:name "1-knee", :size 2}
;;     {:name "5-knee", :size 2}
;;     {:name "2-knee", :size 2}
;;     {:name "4-knee", :size 2}
;;     {:name "3-thigh", :size 4}
;;     {:name "2-thigh", :size 4}
;;     {:name "5-thigh", :size 4}
;;     {:name "4-thigh", :size 4}
;;     {:name "1-thigh", :size 4}
;;     {:name "1-lower-leg", :size 3}
;;     {:name "4-lower-leg", :size 3}
;;     {:name "5-lower-leg", :size 3}
;;     {:name "3-lower-leg", :size 3}
;;     {:name "2-lower-leg", :size 3}
;;     {:name "2-achilles", :size 1}
;;     {:name "5-achilles", :size 1}
;;     {:name "3-achilles", :size 1}
;;     {:name "4-achilles", :size 1}
;;     {:name "1-achilles", :size 1}
;;     {:name "2-foot", :size 2}
;;     {:name "5-foot", :size 2}
;;     {:name "3-foot", :size 2}
;;     {:name "1-foot", :size 2}
;;     {:name "4-foot", :size 2}]

