(ns clojure-news.index
   (:require [jsoup.soup :as soup]
             [http.async.client :as http]
             [net.cgrand.enlive-html :as enlive]
             [clojure.java.io :as io]
             [clojure-csv.core :as csv]
             [clojure.string :as str]
             [clj-http.client :as client]
             [criterium.core :as cr]
             [postal.core :as postal]
             [incanter [core :refer [$] 
                             :as incanter$]
                       [core :as incanter]
                       [stats :as stats] 
                       [io :as io2] 
                       [charts :as charts] 
                       [datasets :as dataset]])


(import [java.net URL])
(import [java.io File])
;(:gen-class)
)


;(count urls)

;(def data_ (io2/read-dataset "/Users/doubleviolin/Documents/R_files/edgar/data/t_.csv" :header true))







;(def tics (incanter$/$ :tic data_))




(defn get-html
  [index-url]
  (try (->
    index-url
    
    (client/get {:socket-timeout 10000 :conn-timeout 10000})
    (:body)
    )
       (catch Exception e (.getMessage e))
       (finally "Cant process index page")
       )
     
)


(defn get-index-links
  [html_]
  (-> html_ 
    (enlive/html-snippet)
    (enlive/select [:title ])
    
)
)



(defn get-index-titles
  [html_]
  (-> html_ 
    (enlive/html-snippet)
    (enlive/select [:span.titletext])
    
)
)








(defn filter-merger-news
[titles]
(filter #(re-find #"[Aa]cqui|[Bb]uy|strateg|[mM]erge|[Tt]akeover|[Ss]ale" %) titles
) 
)

(defn find-company-url
[company-name]
(str "https://news.google.com/news/section?cf=all&ned=us&q=" (str/join "+" (str/split company-name #" ")) )
)


(defprotocol Textful
  (extract-text [x]
    "This returns the string for string, but if nested struc returns content"))

(extend-protocol Textful
  java.lang.String
  (extract-text [x] (identity x))

  clojure.lang.PersistentStructMap
  (extract-text [x]
      (apply str (:content x))
)


  clojure.lang.LazySeq
  (extract-text [x]
      (apply str (map extract-text x))
)



)

(defn find-company-news2
[company-name]
(conj (filter #(re-find #"[Aa]cqui|[Bb]uyout|strateg|[mM]erge|[Tt]akeover|[Ss]ale|[Rr]umor|[Ee]xplores [Ss]ale|mulling purchase|trying to buy" %) 

(map extract-text (map :content (get-index-titles (get-html (find-company-url company-name)))))

) company-name)
)


(defn find-company-news3
[company-name]
(println company-name)
(filter #(re-find #"[Aa]cqui|[Bb]uyout|[Ss]trategic|[mM]erge|[Tt]akeover|[Pp]otential [Ss]ale|[Pp]otential [Bb]uyers" %) 

(map extract-text (map :content (get-index-titles (get-html (find-company-url company-name)))))

) )







(defn get-index-dates
  [html_]
  (-> html_ 
    (enlive/html-snippet)
    (enlive/select [:span.al-attribution-timestamp])
    
)
)

;(def dates (get-index-dates html_))

;



   (def data_ (io2/read-dataset "/Users/doubleviolin/clojure/clojure-download2/data/list_.csv" :header true))

   (def url "http://news.google.com/news?q=humana&output=rss")


   (def companies (incanter$/$ :NewsName data_))
   (println companies)
   (def companies ["HUMANA" "BROADCOM" "APPLE" "KAGGLE"])
   (def output (doall (map find-company-news3 companies)))


   (def output3 (filter #(not= 0 (count %)) output  ))
   (flatten output3)
   
   (def output4 (with-out-str (clojure.pprint/pprint (flatten output3))))
   (println output4)
   

   (postal/send-message {:host "smtp.gmail.com"
                         :user "p.parthasarathy"
                         :pass "Musiri123"
                         :ssl :yes!!!11}
                        {:from "papa"
                         :to "puru.part@gmail.com"
                         :subject "News Today"
                         :body output4})


;



; filter for caompanies greater than 200 million on listed exchanges
