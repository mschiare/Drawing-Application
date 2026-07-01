/**
 * Classe che rappresenta una Ellisse come entità del modello.
 * Contiene le informazioni geometriche e di stile necessarie per descrivere la figura Ellisse.
 * I dati sono utilizzati dalla View per generare la rappresentazione grafica sulla lavagna.
 * Inoltre la classe fornisce la strategia di rendering temporaneo tramite il pattern Strategy.
 *
 * Autori:
 *  - Maria Silvana (costruttore, rendering statico del nodo Ellipse a partire dai dati del Model, metodo getTemporaryRenderStrategy())
 *  - Michele (metodi getClone() e toString())
 *  - Kevin (gestione rotazione)
 */


package com.example.Model;

import com.example.State.FiguraSelezionataManager;
import com.example.Strategy.EllisseTemporaneoStrategy;
import com.example.Strategy.FiguraTemporaneaStrategy;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;


public class Ellisse extends Figura {

    public Ellisse(double x1, double y1, double x2, double y2, Color strokeColor, Color fillColor) {
        super(x1, y1, x2, y2, strokeColor, fillColor,0); // salva tutte le coordinate nella superclasse
    }


    @Override
    public Node creaNodoJavaFX() {
        // Calcola centro e raggi
        double centerX = (x1 + x2) / 2;
        double centerY = (y1 + y2) / 2;
        double radiusX = Math.abs(x2 - x1) / 2;
        double radiusY = Math.abs(y2 - y1) / 2;

        Ellipse e = new Ellipse(centerX, centerY, radiusX, radiusY);

        // collegamento tra nodo JavaFX ellipse e figura corrispondente Ellisse
        this.setNodo(e);

        e.setRotate(rotazione);
        e.setStrokeWidth(1);
        e.setStroke(strokeColor);
        e.setFill(fillColor);

        // salvo nell'istanza Ellipse il riferimento all'istanza Ellisse per poi poter recuperare la figura Ellisse dal model, è un metadato.
        e.setUserData(this);


        if (FiguraSelezionataManager.getInstance().get() == this) {
            e.setEffect(new DropShadow(20, Color.GREY));
        }

        return e;
    }

    @Override
    public FiguraTemporaneaStrategy getTemporaryRenderStrategy() {
        return new EllisseTemporaneoStrategy();
    }


    // serve per Paste()

    @Override
    public Figura getClone() {
        int dx = 20;
        Ellisse e = new Ellisse(x1+dx, y1+dx, x2+dx, y2+dx, strokeColor, fillColor);
        e.setRotazione(rotazione);
        return e;
    }


    @Override
    public String toString(){
        return "Ellisse";
    }



}
