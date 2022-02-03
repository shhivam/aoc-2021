(ns day-1
  (:require [clojure.string :as string]))


(defn- parse-input []
  (map #(Integer/parseInt %)
       (string/split (slurp "src/inputs/input-day-1.txt")
                     #"\n")))

(defn part1 []
  (->> (parse-input)
       (partition 2 1)
       (filter (fn [[a b]] (< a b)))
       count))
