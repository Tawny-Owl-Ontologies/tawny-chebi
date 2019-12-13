(ns tawny-chebi.chebi
  (:refer-clojure :only [partial println])
  (:require [tawny read memorise])
  (:import [java.util.zip GZIPInputStream]))

;; version information
(clojure.core/defonce available-versions
  ^{:private true
    :doc "Map showing available ChEBI versions. Lite provides Ids,
  name, subsets and relationships. Core provides the same features as
  Lite plus chemical data (mass, charge, formula) and
  structures (inchis, inchikeys, smiles. Full provides the same
  features as Core plus name synonyms and manually added
  cross-reference. "}
  {0, "chebi.owl"
   1, "chebi_core.owl"
   2, "chebi_lite.owl"})

;; chosen version
(clojure.core/defonce version (available-versions 2))
;; (println version)

;; ungzip file if it does not exist in resources folder
(clojure.core/when (clojure.core/not (clojure.java.io/resource version))
  (clojure.core/let
      [stream
       (GZIPInputStream.
        (clojure.java.io/input-stream
         (clojure.java.io/resource
          (clojure.core/str version ".gz"))))]
    (clojure.java.io/copy
     stream
     (clojure.java.io/file
      (clojure.core/str "./resources/" version)))))

;; TOFIX Make into function with variable location (default local
;; resource) should be able to use latest from bioontology.
;; "Reads in the chebi ontology from resource"
(tawny.read/defread chebi
  :location
  ;; runs out of memory
  ;; (GZIPInputStream.
  ;;  (clojure.java.io/input-stream
  ;;   (clojure.java.io/resource "chebi_lite.owl.gz")))
  (tawny.owl/iri (clojure.java.io/resource version))
  :iri "http://purl.obolibrary.org/obo/chebi.owl"
  :viri "http://purl.obolibrary.org/obo/chebi/147/chebi.owl"
  :prefix "chebi:"
  :filter
  (partial tawny.read/iri-starts-with-filter
           "http://purl.obolibrary.org/obo/CHEBI")
  :filter
  tawny.read/label-transform
  
  :transform
  tawny.read/exception-nil-label-transform 
  )

;; (println chebi)
;; (clojure.core/ns-publics 'tawny-chebi.chebi)
;; TODO
;; used when you update the local ontology; adds new mappings to
;; existing mappings
;; (tawny.memorise/remember "./src/tawny_chebi/chebi_memorise.edn")

;; stores key,value mappings to file
;; (tawny.memorise/memorise "./src/tawny_chebi/chebi_memorise.edn")
