(ns day-4
  (:require [clojure.string :as string]))

;; #"\s+" to break down things with multiple white spaces in them
(defn parse-input []
  (let [raw-input (->> (slurp "src/inputs/day-4.txt")
                       string/split-lines
                       (filter #(not (string/blank? %))))]

    {:numbers (mapv #(Long/parseLong %) (string/split (first raw-input) #","))
     :bingo-boards (->> raw-input
                        rest
                        (mapcat #(string/split % #"\s+"))
                        (remove string/blank?)
                        (mapv #(assoc {}
                                      :marked? false
                                      :value (Long/parseLong %)))
                        (partition 25))}))

(defn- mark-bingo-with-num [bingo-board number]
  (map #(if (= number (:value %))
          (assoc % :marked? true)
          %)
       bingo-board))

(defn- bingo? [board]
  (let [rows (partition 5 board)
        cols (apply mapv vector rows)]
    (->> (concat rows cols)
         (some #(every? :marked? %))
         some?)))

(defn calc-score [bingo-board number]
  (* number
     (reduce +
             (map #(if (:marked? %) 0 (:value %))
                  bingo-board))))

(defn mark-boards-and-score [bingo-boards remaining-numbers last-checked-number]
  (let [bingoed-boards (filter bingo? bingo-boards)]
    (if (not-empty bingoed-boards)
      (calc-score (first bingoed-boards) last-checked-number)
      (-> (mapv #(mark-bingo-with-num % (first remaining-numbers))
                bingo-boards)
          (mark-boards-and-score (rest remaining-numbers) (first remaining-numbers))))))

(defn part1 []
  (let [{:keys [numbers bingo-boards]} (parse-input)]
    (mark-boards-and-score bingo-boards numbers 0)))
