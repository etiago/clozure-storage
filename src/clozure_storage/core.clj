(ns clozure-storage.core
  (:import (com.microsoft.azure.storage CloudStorageAccount)
           (com.microsoft.azure.storage.table TableServiceEntity
                                              TableOperation)
           (MapToTableServiceEntityFactory))
  (:gen-class))

(defn- get-table-reference [table-client table-name]
  (.getTableReference table-client table-name))

(defn- get-table-client [connection-string]
  (.createCloudTableClient
   (CloudStorageAccount/parse
    connection-string)))

(defn insert-data-in-table [account-name account-key table-name data]
  "Takes the account name, account key, table name and a map with at least
partitionKey and rowKey (mandatory for Azure) and any other key:value pairs
(so far only strings are allowed) to be inserted."
  (let [table
        (get-table-reference
         (get-table-client
          (str "DefaultEndpointsProtocol=https;"
               "AccountName=" account-name ";"
               "AccountKey=" account-key))
         table-name)]
    (.createIfNotExists table)
    (try
      (.execute table (TableOperation/insert (MapToTableServiceEntityFactory/create data)))
      (catch Exception e (fn [] nil)))))
