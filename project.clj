(defproject clojure-spinners "0.1.0"
  :description "Various spinners for Clojure"
  :url "https://github.com/popoppo/clojure-spinners"
  :license {:name "MIT"
            :url "https://choosealicense.com/licenses/mit"}
  :dependencies [[org.clojure/clojure "1.10.1"]]
  :deploy-repositories [["clojars" {:url "https://repo.clojars.org"
                                    :username :env/clojars_username
                                    :password :env/clojars_password
                                    :sign-releases false}]]

  :plugins [[lein-exec "0.3.7"]]

  :src-paths ["src"]
  :test-paths ["test"]

  :repl-options {:init-ns clojure-spinners.spinner})
