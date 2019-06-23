# error
_easy clojure error handling_

## Installation

Make your `deps.edn` look like this:

```clojure
; ... paths probably

{:deps {coast-framework/error {:mvn/version "1.0.1"}}}
        ;... your other libraries}}

; ... rest of deps.edn
```

## Usage

Require it like this

```clojure
(ns your-project
  (:require [error.core :as error]))
```

Use it like this

```clojure
(defn validate-email [email]
  (if (re-matches #".+@.+\..+" email)
    email
    (error/raise (str "Please enter a valid email address, not " email))))

(let [[email e] (error/rescue (validate-email "not an email"))]
  (if (some? e)
    {:error e}
    {:email email}))
```

or like this

```clojure
(defn validate-email [email]
  (if (re-matches #".+@.+\..+" email)
    email
    (error/raise (str "Please enter a valid email address, not " email))))
      :error/email

(let [[email e] (error/rescue (validate-email "not an email")
                  :error/email)]
  (if (some? e)
    {:error/email e}
    {:email email}))
```

It will still throw on exceptions if the identifier you specify isn't the same

```clojure
(error/rescue
  (error/raise "error" :error)
  :error1)

; :error1 != :error so the following exception will be called
(throw (ex-info "error" {:error.core/e "error" :error.core/id :error}))
```

It also works with regular exceptions with `try*`

```clojure
(defn validate-email [email]
  (if (re-matches #".+@.+\..+" email)
    email
    (throw (Exception. (str "Please enter a valid email address, not " email)))))

(let [[email e] (error/try* (validate-email "not an email"))]
  (if (some? e)
    {:error/email (.getMessage e)}
    {:email email}))
```

## Testing

```sh
git clone ...
cd error
make test
```

## License

MIT

## Contribution

Any and all issues or pull requests welcome!

## Too Small

This library is really small, it's true. Too small? Could be.
