(ns day-3
  (:require [clojure.string :as string]))

(defn- parse-input []
  (mapv #(string/split % #"")
       (string/split (slurp "src/inputs/day-3.txt") #"\n")))

(defn- transpose [m]
  (apply mapv vector m))

(defn- element-with-max-frequency [frequencies-map]
  (if (> (get frequencies-map "1") (get frequencies-map "0"))
    "1"
    "0"))

(defn- flip [binary-string]
  (string/join (map #(if (= \1 %) "0" "1") binary-string)))

(defn part1 [parsed-input]
  (let [binary-gamma-rate (->> parsed-input
                               transpose
                               (map frequencies)
                               (map element-with-max-frequency)
                               (apply str))
        binary-epsilon-rate (flip binary-gamma-rate)]
    
    (* (Long/parseLong binary-gamma-rate 2)
       (Long/parseLong binary-epsilon-rate 2))))
