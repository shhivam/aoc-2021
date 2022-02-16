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


(defn calc-score-of-last-winner [boards [first-num & rest-nums] last-number-winner last-bingoed-board]
  (let [marked-boards (mapv #(mark-bingo-with-num % first-num) boards)
        {new-bingoed-boards true
         un-bingoed-boards false} (group-by bingo? marked-boards)]
    (if (or (empty? boards)
            (not first-num))
      (calc-score last-bingoed-board last-number-winner)
      (calc-score-of-last-winner
       un-bingoed-boards
       rest-nums
       (when (not-empty new-bingoed-boards)
         first-num)
       (when (not-empty new-bingoed-boards)
         (last new-bingoed-boards))))))


(defn part2 []
  (let [{:keys [numbers bingo-boards]} (parse-input)]
    (calc-score-of-last-winner bingo-boards numbers nil nil)))
