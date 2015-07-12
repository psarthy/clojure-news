(defproject clojure-news "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :jvm-opts ["-Xms768m" "-Xmx768m"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [http.async.client "0.5.2"]
                 [org.slf4j/slf4j-simple "1.7.2"]
                 [clojure-csv/clojure-csv "2.0.1"]
                 [org.clojure/clojure "1.6.0"]
                 [incanter "1.5.6"]
		 [enlive "1.1.1"]
                 [criterium "0.4.3"]
                 [clj-soup/clojure-soup "0.1.2"]
                 [clj-http "1.0.1"]
		 [com.draines/postal "1.11.3"]
		 ]
  :source-paths ["."]
;:main clojure-news.index   
)
