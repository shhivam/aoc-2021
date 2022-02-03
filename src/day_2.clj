(ns day-2
  (:require [clojure.string :as string]))

(defn- parse-input []
  (string/split (slurp "src/inputs/day-2.txt")  #"\n"))

(defn- char->int [char]
  (Integer/parseInt (str char)))

(defn- extract-int-value [str]
  (char->int (last str)))

(defn- calc-depth [parsed-input]
  (apply + (map #(cond
                   (string/starts-with? % "down") (extract-int-value %)
                   (string/starts-with? % "up")  (- 0 (extract-int-value %))
                   :else 0)
                parsed-input)))

(defn- calc-forward [parsed-input]
  (apply + (map #(if (string/starts-with? % "forward")
                   (Integer/parseInt (str (last %)))
                   0)
                parsed-input)))

;; "Elapsed time: 54.820625 msecs"
;; (time (dotimes [_ 100]
;;         (part1 (parse-input))))

;; "Elapsed time: 361.985708 msecs" 
;; (time (dotimes [_ 1000]
;;         (part1 (parse-input))))

(defn part1 [parsed-input]
  (* (calc-depth parsed-input) (calc-forward parsed-input)))
