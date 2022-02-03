(ns day-1
  (:require [clojure.string :as string]))



(defn part1 []
  (let [contents (slurp "src/inputs/input-day-1.txt")
        readings-list (string/split contents #"\n")
        deltas (map-indexed (fn [index value]
                              (- (Integer/parseInt (nth readings-list
                                                        (inc index)
                                                        value))
                                 (Integer/parseInt value)))
                            readings-list)]
    (count (filter pos? deltas))))


