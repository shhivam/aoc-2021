(ns day-1
  (:require [clojure.string :as string]))


;; Parsing the input complexity:
;; O(n) where n is the number of lines in the file
(defn- parse-input []
  (map #(Integer/parseInt %)
       (string/split (slurp "src/inputs/day-1.txt")
                     #"\n")))

;; Part 1 complexity
;; O(n)
(defn part1 [parsed-input]
  (->> parsed-input
       (partition 2 1)
       (filter (fn [[a b]] (< a b)))
       count))

;; Partitioning the input into lists of 3 values
;; Then summing the values
;; Then essentially calling part1 on that summed up list
;; O(n)
(defn part2 [parsed-input]
  (->> parsed-input
       (partition 3 1)
       (map (fn [[n1 n2 n3]] (+ n1 n2 n3)))
       part1))

;; (part1 (parse-input))

;; (part2 (parse-input))
