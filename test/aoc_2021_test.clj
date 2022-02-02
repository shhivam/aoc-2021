(ns aoc-2021-test
  (:require [clojure.test :refer [deftest is testing]]
            [aoc-2021 :as aoc-2021]))

(deftest plus-one
  (testing "Should return us the incremented value"
    (is (= 4 (aoc-2021/plus-one 2)))))
