;; Chapter 4: Core Functions in Depth

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

;; take while -. take n elements from a collection
(take-while #(< (:month %) 3) food-journal)
;; => ({:month 1, :day 1, :human 5.3, :critter 2.3}
;;     {:month 1, :day 2, :human 5.1, :critter 2.0}
;;     {:month 2, :day 1, :human 4.9, :critter 2.1}
;;     {:month 2, :day 2, :human 5.0, :critter 2.5})

;; drop while -. drop n elements from a collection
(drop-while #(< (:month %) 3) food-journal)
;; => ({:month 3, :day 1, :human 4.2, :critter 3.3}
;;     {:month 3, :day 2, :human 4.0, :critter 3.8}
;;     {:month 4, :day 1, :human 3.7, :critter 3.9}
;;     {:month 4, :day 2, :human 3.7, :critter 3.6})

;; Those are more efficient than: 
;; filter, filter elements from a collection
(filter #(< (:month %) 3) food-journal)
;; => ({:month 1, :day 1, :human 5.3, :critter 2.3}
;;     {:month 1, :day 2, :human 5.1, :critter 2.0}
;;     {:month 2, :day 1, :human 4.9, :critter 2.1}
;;     {:month 2, :day 2, :human 5.0, :critter 2.5})

;; some, if collection contains any value that etst true
(some #(> (:critter %) 5) food-journal)
;; => nil

(some #(> (:critter %) 3) food-journal) ; <- only return true or false
;; => true

(some #(and (> (:critter %) 5) %) food-journal) ; <- return entry
;; => {:month 3, :day 1, :human 4.2, :critter 3.3}

;; sort to sort elements, and sort-by to apply function to the elements of a sequence
(sort [3 5 2 1 4.6])
;; => (1 2 3 4.6 5)

(sort-by count ["aaa" "c" "bb"])
;; => ("c" "bb" "aaa")

;; concat to appends members to one sequence to the end of another
(concat ["hi" 3.2 4] [4 1])
;; => ("hi" 3.2 4 4 1)

(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true  :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true  :name "McMackson"}
   2 {:makes-blood-puns? true,  :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true,  :has-pulse? true  :name "Mickey Mouse"}})

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

(defn identify-vampire
  [social-security-numbers]
  (first (filter vampire?
                 (map vampire-related-details social-security-numbers))))

(time (vampire-related-details 0)) ; time how much time it takes to run, only REPL
; "Elapsed time: 1000.2303 msecs"
;; => {:makes-blood-puns? false, :has-pulse? true, :name "McFishwich"}
;; 
(time (identify-vampire (range 0 1000000)))
;; => {:makes-blood-puns? true, :has-pulse? false, :name "Damon Salvatore"}


(time (def mapped-details (map vampire-related-details (range 0 1000000))))
; "Elapsed time: 0.058597 msecs"
;; => #'user/mapped-details

;; Infinite sequenes
(concat (take 9 (repeat "la")) ["Con altura!"]) ; using repeat
;; => ("la" "la" "la" "la" "la" "la" "la" "la" "la" "Con altura!")

(take 3 (repeatedly (fn [] (rand-int 10)))) ; using repeatedly
;; => (7 5 2)

(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))
(take 10 (even-numbers))
;; => (0 2 4 6 8 10 12 14 16 18)

(cons 0 '(2 5 8)) ; append element to list
;; => (0 2 5 8)

(empty? []) ; about the whole, like count and every?
;; => true

(empty? ["no!"])
;; => false

;; into - convert return value seq back into original value
(map identity {:sunlight-reaction "Glitter!"})
;; => ([:sunlight-reaction "Glitter!"])

(into {} (map identity {:sunlight-reaction "Glitter!"}))
;; => {:sunlight-reaction "Glitter!"}

(into {:favorite-emotion "gloomy"} [[:sunlight-reaction "Glitter!"]])
;; => {:favorite-emotion "gloomy", :sunlight-reaction "Glitter!"}

(into ["cherry"] '("pine" "spruce"))
;; => ["cherry" "pine" "spruce"]

;; conj add elements but is different from into
;; conj takes a rest parameter
;; into takes a seq data structure
(conj [0] [1])
;; => [0 [1]]

(into [0] [1])
;; => [0 1]

(conj [0] 1)
;; => [0 1]

(conj [0] 1 2 3 4 5)
;; => [0 1 2 3 4 5]

;; define conj in terms of into
(defn my-conj
  [target & additions]
  (into target additions))
(my-conj [0] 1 2 3) ; -> define conj in base of into
;; => [0 1 2 3]

;; define into in terms of conj
(defn my-into
  [target additions]
  (apply conj target additions)) ; apply to every element of additions
(my-into [0] [1 2 3])
;; => [0 1 2 3]


;; apply, when use max to get the maximun of a seq, it only works in single elements
;; can use apply to a data structure to get the max
(max 0 1 2) ; it works for numbers
;; => 2
 
;(max [0 1 2]) ; return entire data structure
;; => [0 1 2]

(apply max [0 1 2]) ; use apply to get max in data structures
;; => 2

;; partial
(def add10 (partial + 10))
(add10 3)
;; => 13

(def add-missing-elements
  (partial conj ["water" "earth" "air"]))
(add-missing-elements "unobtainium" "adamantium")
;; => ["water" "earth" "air" "unobtainium" "adamantium"]

(defn my-partial
  [partialized-fn & args]
  (fn [& more-args]
    (apply partialized-fn (into args more-args))))
(def add20 (my-partial + 20))
(add20 3)
;; => 23

;; WHEN to use partial? when find repeating the same combination of function
;; and arguments in many different contexts
(defn lousy-logger
  [log-level message]
  (condp = log-level
    :warn (clojure.string/lower-case message)
    :emergency (clojure.string/upper-case message)))
(def warn (partial lousy-logger :warn))
(warn "Red light ahead")
;; => "red light ahead"

(def emergency (partial lousy-logger :emergency))
(emergency "Red light ahead")
;; => "RED LIGHT AHEAD"

;; complement
(defn identify-humans
  [social-security-numbers]
  (filter #(not (vampire? %))
          (map vampire-related-details social-security-numbers)))

(def not-vampire? (complement vampire?)) ; complement
(defn identify-humans
  [social-security-numbers]
  (filter not-vampire?
          (map vampire-related-details social-security-numbers)))













