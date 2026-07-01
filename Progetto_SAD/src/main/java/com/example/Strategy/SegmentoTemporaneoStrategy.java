/**
 * Strategia concreta per la gestione della rappresentazione temporanea di un segmento durante
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
import javafx.scene.shape.Line;

public class SegmentoTemporaneoStrategy implements FiguraTemporaneaStrategy {

    @Override
    public Node crea(double x1, double y1, double rotazione) {
        Line l = new Line(x1, y1, x1, y1); // inizio e fine uguali
        l.setStroke(Color.BLACK);
        l.setRotate(rotazione);
        return l;
    }

    @Override
    public void aggiorna(Node node, double x1, double y1, double x2, double y2, double rotazione) {
        Line l = (Line) node;
        l.setStartX(x1);
        l.setStartY(y1);
        l.setEndX(x2);
        l.setEndY(y2);
        l.setRotate(rotazione);
    }

}
