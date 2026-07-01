/**
 * Strategia concreta per la gestione della rappresentazione temporanea di un rettangolo durante
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RettangoloTemporaneoStrategy implements FiguraTemporaneaStrategy {

    @Override
    public Node crea(double x1, double y1, double rotazione) {
        Rectangle r = new Rectangle(x1, y1, 0, 0);
        r.setStroke(Color.BLACK);
        r.setFill(Color.LIGHTGRAY.deriveColor(1, 1, 1, 0.4));
        r.setRotate(rotazione);
        return r;
    }

    @Override
    public void aggiorna(Node node, double x1, double y1, double x2, double y2,double rotazione) {
        Rectangle r = (Rectangle) node;
        r.setX(Math.min(x1, x2));
        r.setY(Math.min(y1, y2));
        r.setWidth(Math.abs(x2 - x1));
        r.setHeight(Math.abs(y2 - y1));
        r.setRotate(rotazione);
    }


}
