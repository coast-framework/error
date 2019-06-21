(ns error.core
  (:import (clojure.lang ExceptionInfo)))


(defn raise
  ([e]
   (raise e true))
  ([e id]
   (if (string? e)
     (throw (ex-info e {::e e ::id id}))
     (throw (ex-info "error.core/raise" {::e e ::id id})))))


(defmacro rescue
  "A macro that wraps exceptions in try/catch
   and emits a tuple (vector) instead of throwing"
  ([f id]
   `(try
     [~f nil]
     (catch ExceptionInfo ex-info#
       (let [ex-data# (ex-data ex-info#)
             e# (get ex-data# ::e)
             id# (get ex-data# ::id)]
         (if (= ~id id#)
          [nil e#]
          (throw ex-info#))))))
  ([f]
   `(rescue ~f true)))


(defmacro try*
  "try* is the same as rescue except it catches any Exception"
  [f]
  `(try
     [~f nil]
     (catch Exception e#
       [nil e#])))
