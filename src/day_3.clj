(ns day-3
  (:require [clojure.string :as string]))

(defn- parse-input []
  (mapv #(string/split % #"")
       (string/split-lines (slurp "src/inputs/day-3.txt"))))

(defn- transpose [m]
  (apply mapv vector m))

(defn- max-frequency-bit [frequencies-map]
  (if (>= (get frequencies-map "1") (get frequencies-map "0"))
    "1"
    "0"))

(defn- flip [binary-string]
  (string/join (map #(if (= \1 %) "0" "1") binary-string)))

(defn part1 [parsed-input]
  (let [binary-gamma-rate (->> parsed-input
                               transpose
                               (map frequencies)
                               (map max-frequency-bit)
                               (apply str))
        binary-epsilon-rate (flip binary-gamma-rate)]
    
    (* (Long/parseLong binary-gamma-rate 2)
       (Long/parseLong binary-epsilon-rate 2))))

(defn- keep-numbers-with [input current-bit-position bit-position-value]
  (filter #(= bit-position-value
              (nth % current-bit-position))
          input))

(defn- least-frequency-bit [frequencies-map]
  (if (<= (get frequencies-map "0") (get frequencies-map "1"))
    "0"
    "1"))

;; O(n^2) where n is the count of the readings from the file
(defn life-support-rating
  ([compare-fn input] (life-support-rating compare-fn 0 input))
  ([compare-fn current-bit-position input]
   (if (= 1 (count input))
     (-> input first string/join)
     (->> input
          (map #(nth % current-bit-position))
          frequencies
          compare-fn
          (keep-numbers-with input current-bit-position)
          (life-support-rating compare-fn (inc current-bit-position) )))))

(defn part2 [parsed-input]
  (let [oxygen-reading (life-support-rating max-frequency-bit parsed-input)
        co2-reading (life-support-rating least-frequency-bit parsed-input)]

    (* (Long/parseLong oxygen-reading 2)
       (Long/parseLong co2-reading 2))))

;; (part2 (parse-input))
