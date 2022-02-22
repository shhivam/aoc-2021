(ns day-6
  (:require [clojure.string :as string]))


(defn- parse-input []
  (frequencies (mapv #(Long/parseLong %)
       (string/split (slurp "src/inputs/day-6.txt") #","))))

(defn fishes-after-a-day [fishes-day]
  (assoc fishes-day
         0 (get fishes-day 1)
         1 (get fishes-day 2)
         2 (get fishes-day 3)
         3 (get fishes-day 4)
         4 (get fishes-day 5)
         5 (get fishes-day 6)
         6 (+ (or (get fishes-day 7) 0)
              (or (get fishes-day 0) 0))
         7 (get fishes-day 8)
         8 (get fishes-day 0)))

(defn solve [fishes-days days-passed total-days]
  (if (= days-passed total-days)
    (reduce + (filter some? (vals fishes-days)))
    (solve (fishes-after-a-day fishes-days)
           (inc days-passed)
           total-days)))

(defn part1 []
  (solve (parse-input) 0 80))

(defn part2 []
  (solve (parse-input) 0 256))
