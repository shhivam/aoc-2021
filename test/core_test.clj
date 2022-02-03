(ns core-test
  (:require [clojure.test :refer [deftest is testing]]
            [core :as core]))

(deftest plus-one
  (testing "Should return us the incremented value"
    (is (= 3 (core/plus-one 2)))))
