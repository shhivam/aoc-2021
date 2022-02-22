(ns day-5
  (:require [clojure.string :as string]))

(defn- parse-line [co-ords-line]
  (-> co-ords-line
      (string/split #" -> |,")
      (#(assoc {}
              :x1 (Long/parseLong (first %))
              :y1 (Long/parseLong (second %))
              :x2 (Long/parseLong (nth % 2))
              :y2 (Long/parseLong (nth % 3))))))

(defn- parse-input []
  (->> (slurp "src/inputs/day-5.txt")
       string/split-lines
       (map parse-line)))

(defn- good-line?
  "Only horizontal and vertical lines are good lines"
  [{:keys [x1 x2 y1 y2]}]
  (or (= x1 x2)
      (= y1 y2)))

(defn- horizontal? [{:keys [y1 y2]}]
  (= y1 y2))

(defn- vertical? [{:keys [x1 x2]}]
  (= x1 x2))

(defn range'
  ([start end]
   (if (<= start end)
     (range start (inc end))
     (range start (dec end) -1))))

(defn points [{:keys [x1 x2 y1 y2] :as line}]
  (cond
    (vertical? line) (map (fn [y] [x1 y])
                            (range' y1 y2))
    (horizontal? line) (map (fn [x] [x y1])
                            (range' x1 x2))
    :else (map vector (range' x1 x2) (range' y1 y2))))

(defn part1 []
  (let [input (parse-input)
        non-diagonal-lines (filter good-line? input)]
    (->> (mapcat points non-diagonal-lines)
         frequencies
         vals
         frequencies)))

(defn part2 []
  (let [input (parse-input)]
    (->> (mapcat points input)
         frequencies
         vals
         frequencies)))
