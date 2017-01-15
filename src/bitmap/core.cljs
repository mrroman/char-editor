(ns bitmap.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(println "This text is printed from src/bitmap/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:char [0 0 0 0 0 0 0 0]}))

(defn set-pixel [ch i j]
  (update-in ch [i] bit-flip j))

(defn char-class [ch-byte n]
  (if (bit-test ch-byte n)
    "char-pixel pixel-on"
    "char-pixel"))

(defn char-table [ch]
  [:table.char
   (for [i (range 8)]
     [:tr.char-line
      {:key i}
      (for [j (range 8)]
        [:td.char-pixel {:className (char-class (get ch i) j)
                         :key j
                         :onClick #(swap! app-state update :char set-pixel i j)}])])])

(defn hello-world []
  [:div
    [:h1 (:text @app-state)]
    [char-table (:char @app-state)]])

(reagent/render-component [hello-world]
                          (. js/document (getElementById "app")))

(defn on-js-reload [])
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
