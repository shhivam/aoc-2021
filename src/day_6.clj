(ns day-6
  (:require [clojure.string :as string]))


(defn- parse-input []
  (mapv #(Long/parseLong %)
       (string/split (slurp "src/inputs/day-6.txt") #",")))

(defn- count-occurence [value coll]
  (->> coll
       (filter #(= value %))
       count))

(defn- decrement-num-days [num-days]
  (if (= 0 num-days)
    6
    (dec num-days)))

(defn fishes-after-a-day [fishes-day]
  (let [zeros-occurence (count-occurence 0 fishes-day)]
    (->> fishes-day
         (mapv decrement-num-days)
         (concat (repeatedly zeros-occurence (constantly 8))))))

(defn part1 
  ([] (part1 (parse-input) 0))
  ([fishes-days iteration-count]
   (if (= iteration-count 80)
     (count fishes-days)
     (part1 (fishes-after-a-day fishes-days) 
            (inc iteration-count)))))


