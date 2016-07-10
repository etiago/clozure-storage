# clozure-storage

[![Clojars Project](http://clojars.org/org.tiago/clozure-storage/latest-version.svg)](http://clojars.org/org.tiago/clozure-storage)

Clozure-storage provides an idiomatic Clojure wrapper around Microsoft's Azure SDK for Java ([Azure/azure-storage-java](https://github.com/Azure/azure-storage-java)).

Currently this is just a proof of concept to show that it is possible to use it. So far it only allows the insertion into table storage.

## Installation

Include the Clojars dependency in your project.

## Usage

Read the docstring for ```insert-data-in-table```.

## Examples

```clojure
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (insert-data-in-table
   "this_is_a_storage_account"
   "this_is_an_api_key"
   "this_is_a_table_name"
   {"partitionKey" "foo" "rowKey" "bar" "this_is_some_field" "this_is_a_value"}))
```
