/**
 * Strategia concreta per la gestione della rappresentazione temporanea di un'ellisse durante
 * le operazioni interattive, secondo il pattern Strategy.
 *
 * Questa classe definisce come viene generata e aggiornata graficamente l'anteprima temporanea
 * dell'ellisse durante le fasi di disegno, spostamento e ridimensionamento, senza modificare
 * immediatamente lo stato persistente del modello.
 *
 * Autori:
 *  - Maria Silvana
 */



package com.example.Strategy;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TextAreaTemporaneoStrategy implements FiguraTemporaneaStrategy {


    @Override
    public Node crea(double x1, double y1,double rotazione) {
        TextArea textArea = new TextArea();
        textArea.setLayoutX(x1);
        textArea.setLayoutY(y1);
        textArea.setPrefWidth(0);
        textArea.setPrefHeight(0);
        textArea.setWrapText(true);
        textArea.setVisible(false);
        textArea.setStyle("-fx-background-color:white;-fx-border-color:black;");
        textArea.setRotate(rotazione);
        return textArea;
    }

    @Override
    public void aggiorna(Node nodo, double x1, double y1, double x2, double y2, double rotazione) {
        TextArea textArea = (TextArea) nodo;
        textArea.setLayoutX(Math.min(x1,x2));
        textArea.setLayoutY(Math.min(y1,y2));
        textArea.setPrefWidth(Math.abs(x2 - x1));
        textArea.setPrefHeight(Math.abs(y2 - y1));
        textArea.setWrapText(true);
        textArea.setMinWidth(20);
        textArea.setMinHeight(20);
        textArea.setStyle("-fx-background-color:white;-fx-border-color:black;");
        textArea.setVisible(true);
        textArea.setRotate(rotazione);

    }

}
