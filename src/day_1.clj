(ns day-1
  (:require [clojure.string :as string]))


(defn- parse-input []
  (map #(Integer/parseInt %)
       (string/split (slurp "src/inputs/input-day-1.txt")
                     #"\n")))

(defn part1 [parsed-input]
  (->> parsed-input
       (partition 2 1)
       (filter (fn [[a b]] (< a b)))
       count))

;; Partitioning the input into lists of 3 values
;; Then summing the values
;; Then essentially calling part1 on that summed up list
(defn part2 [parsed-input]
  (->> parsed-input
       (partition 3 1)
       (map (fn [[n1 n2 n3]] (+ n1 n2 n3)))
       part1))

;; (part1 (parse-input))

;; (part2 (parse-input))
