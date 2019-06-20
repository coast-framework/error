(ns error.core-test
  (:require [error.core :as error]
            [clojure.test :refer :all])
  (:import (clojure.lang ExceptionInfo)))


(deftest raise-test
  (testing "raise with one argument"
    (is (thrown? ExceptionInfo (error/raise "an error"))))

  (testing "raise with one argument checking for the error message"
    (is (thrown-with-msg? ExceptionInfo #"error.core/raise" (error/raise "an error"))))

  (testing "raise with two args"
    (try
      (error/raise {:a 1} :id)
      (catch ExceptionInfo e
        (is (= {:a 1} (:error.core/e (ex-data e))))))))


(deftest rescue-test
  (testing "rescue with one arg"
    (is (= [nil "error"] (error/rescue
                          (error/raise "error")))))

  (testing "rescue with a non-string arg"
    (is (= [nil {:a 1}] (error/rescue
                         (error/raise {:a 1})))))

  (testing "rescue with two arguments"
    (is (= [nil "error"] (error/rescue
                          (error/raise "error" :error)
                          :error))))

  (testing "rescue without matching id"
    (is (thrown? ExceptionInfo (error/rescue
                                 (error/raise "error" :error)
                                 :error1)))))


(deftest try*-test
  (testing "try*"
    (is (= "error" (.getMessage
                    (second
                      (error/try*
                       (throw (Exception. "error")))))))))