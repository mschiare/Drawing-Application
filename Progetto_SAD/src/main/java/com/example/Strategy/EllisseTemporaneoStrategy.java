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
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class EllisseTemporaneoStrategy implements FiguraTemporaneaStrategy {

    @Override
    public Node crea(double x1, double y1, double rotazione) {
        Ellipse e = new Ellipse(x1, y1, 0, 0); // centro = x1,y1 iniziale
        e.setStroke(Color.BLACK);
        e.setFill(Color.LIGHTGRAY.deriveColor(1, 1, 1, 0.4));
        e.setRotate(rotazione);
        return e;
    }

    @Override
    public void aggiorna(Node node, double x1, double y1, double x2, double y2, double rotazione) {
        Ellipse e = (Ellipse) node;
        double centerX = (x1 + x2) / 2;
        double centerY = (y1 + y2) / 2;
        double radiusX = Math.abs(x2 - x1) / 2;
        double radiusY = Math.abs(y2 - y1) / 2;

        e.setCenterX(centerX);
        e.setCenterY(centerY);
        e.setRadiusX(radiusX);
        e.setRadiusY(radiusY);
        e.setRotate(rotazione);
    }

}
