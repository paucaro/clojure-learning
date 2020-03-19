;; A vampire data analysis program for the FWPD
;; Chapter 4 - exercise of learning

(ns clojure-learning.core)
(def filename "suspects.csv")
(slurp filename)
;; => "Edward Cullen,10\nBella Swan,0\nCharlie Swan,0\nJacob Black,3\nCarlisle Cullen,6"

(def vamp-keys [:name :glitter-index]) ; vector of keys to create vampire vamps

(defn str->int ; conver string to an integer
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert ; takes a vamp-kay and a value, returns converted value
  [vamp-key value]
  ((get conversions vamp-key) value))

(convert :glitter-index "3")
;; => 3

(defn parse ; take string and first splits it on the new line to create a sew of string
  ; then maps over sew of string splliting by ,
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(parse (slurp filename))
;; => (["Edward Cullen" "10"] ["Bella Swan" "0"] ["Charlie Swan" "0"] ["Jacob Black" "3"] ["Carlisle Cullen" "6"])

(defn mapify
  "Return a seq of maps like {:name \"Edward CUllen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(first (mapify (parse (slurp filename))))
;; => {:name "Edward Cullen", :glitter-index 10}

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(glitter-filter 3 (mapify (parse (slurp filename))))
;; => ({:name "Edward Cullen", :glitter-index 10}
;;     {:name "Jacob Black", :glitter-index 3}
;;     {:name "Carlisle Cullen", :glitter-index 6})

(def suspects-list (glitter-filter 3 (mapify (parse (slurp filename)))))


;; --------------------------------------------
;; The vampire analysis program you now have is already decades ahead of anything else on 
;; the market. But how could you make it better? I suggest trying the following:

;; Exercise 1
;; Turn the result of your glitter filter into a list of names.

(defn list-names
  [suspects]
  (map #(% :name) suspects))

(list-names suspects-list)
;; => ("Edward Cullen" "Jacob Black" "Carlisle Cullen")

(map :name suspects-list) ; another way
;; => ("Edward Cullen" "Jacob Black" "Carlisle Cullen")


;; Exercise 2
;; Write a function, append, which will append a new suspect to your list of suspects.

(defn append
  [suspects new-suspect]
  (conj suspects new-suspect))
(def suspects-list (append suspects-list {:name "Pau" :glitter-index 13}))
;; => ({:name "Pau", :glitter-index 13}
;;     {:name "Edward Cullen", :glitter-index 10}
;;     {:name "Jacob Black", :glitter-index 3}
;;     {:name "Carlisle Cullen", :glitter-index 6})


;; Exercise 3
;; Write a function, validate, which will check that :name and :glitter-index are present when you append. 
;; The validate function should accept two arguments: a map of keywords to validating functions, 
;; similar to conversions, and the record to be validated.

(defn validate
  [new-map]
  (and (contains? new-map :name) (contains? new-map :glitter-index))
)

(defn append-val
  [suspects new-suspect]
  (if (validate new-suspect)
      (conj suspects new-suspect)
      suspects))
(def suspects-list (append-val suspects-list {:name "Caro" :glitter-index 20}))
;; => ({:name "Caro", :glitter-index 20}
;;     {:name "Edward Cullen", :glitter-index 10}
;;     {:name "Jacob Black", :glitter-index 3}
;;     {:name "Carlisle Cullen", :glitter-index 6})


;; Exercise 4
;; Write a function that will take your list of maps and convert it back to a CSV string. 
;; You’ll need to use the clojure.string/join function.
(defn parse-revert
  "Convert map to filename"
  [string]
  (clojure.string/join "\n" (map #(clojure.string/join "," [(:name %) (:glitter-index %)]) string))
  )

(parse-revert suspects-list)
;; => "Caro,20\nPau,13\nEdward Cullen,10\nJacob Black,3\nCarlisle Cullen,6"
