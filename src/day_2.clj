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



(defn- eval-line [{:keys [aim depth forward] :as current-state} string]
  (let [int-value (extract-int-value string)]
    (cond
      (string/starts-with? string "up")
      (assoc current-state :aim (- aim int-value))
      (string/starts-with? string "down")
      (assoc current-state :aim (+ aim int-value))
      (string/starts-with? string "forward")
      (assoc current-state
             :forward (+ forward int-value)
             :depth (+ depth (* aim int-value)))
      :else current-state)))

(defn part2 [parsed-input]
  (let [{:keys [depth forward]} (reduce eval-line
                                        {:aim 0 :depth 0 :forward 0}
                                        parsed-input)]
    (* depth forward)))
