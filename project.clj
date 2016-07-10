(defproject clozure-storage "0.1.0-SNAPSHOT"
  :description "Clojure wrapper around the azure-storage-java (Microsoft Azure Storage SDK for Java) library."
  :url "https://github.com/etiago/clozure-storage"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.microsoft.azure/azure-storage "4.0.0"]
                 [org.javassist/javassist "3.20.0-GA"]]
  :java-source-paths ["src/java"]
  :main ^:skip-aot clozure-storage.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
