(defproject tawny-chebi "1.0.0-SNAPSHOT"
  :description "A mavenized version of ChEBI"
  :dependencies [[uk.org.russet/tawny-owl "2.0.0-SNAPSHOT"]]
  :scm {:url "https://github.com/jaydchan/tawny-chebi.git"
        :name "git"}
  :license {:name "LGPL"
            :url "http://www.gnu.org/licenses/lgpl-3.0.txt"
            :distribution :repo}
  :jvm-opts ["-Xmx1000m"]
  ;; :main tawny-chebi.core
  )
