/**
 * Classe che rappresenta un Rettangolo come entità del modello.
 * Contiene le informazioni geometriche e di stile necessarie per descrivere la figura Rettangolo.
 * I dati sono utilizzati dalla View per generare la rappresentazione grafica sulla lavagna.
 * Inoltre la classe fornisce la strategia di rendering temporaneo tramite il pattern Strategy.
 *
 * Autori:
 *  - Maria Silvana (costruttore, rendering statico del nodo Rectangle a partire dai dati del Model, metodo getTemporaryRenderStrategy())
 *  - Michele (metodi getClone() e toString())
 *  - Kevin (gestione rotazione)
 */

package com.example.Model;

import com.example.State.FiguraSelezionataManager;
import com.example.Strategy.FiguraTemporaneaStrategy;
import com.example.Strategy.RettangoloTemporaneoStrategy;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Rettangolo extends Figura {

    private double larghezza;
    private double altezza;


    public Rettangolo(double x1, double y1, double x2, double y2, Color strokeColor, Color fillColor) {

        super(x1, y1, x2, y2, strokeColor, fillColor,0);

        larghezza = Math.abs(x2 - x1);
        altezza = Math.abs(y2 - y1);
        this.x1 = Math.min(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.x2 = Math.max(x1, x2);
        this.y2 = Math.max(y1, y2);


    }

    @Override
    public FiguraTemporaneaStrategy getTemporaryRenderStrategy() {
        return new RettangoloTemporaneoStrategy();
    }

    @Override
    public Figura getClone() {
        int dx = 20;
        Rettangolo r = new Rettangolo(x1+dx, y1+dx, x2+dx, y2+dx, strokeColor, fillColor);
        r.setRotazione(rotazione);
        return r;
    }

    @Override
    public String toString(){
        return "Rettangolo";
    }

    @Override
    public Rectangle creaNodoJavaFX() {

        larghezza = Math.abs(x2 - x1);
        altezza = Math.abs(y2 - y1);

        Rectangle r = new Rectangle(Math.min(x1, x2), Math.min(y1, y2), larghezza, altezza);

        // collegamento tra un'istanza di Rettangolo e la corrispondente istanza di Rectangle
        this.setNodo(r);

        r.setRotate(rotazione);
        r.setStrokeWidth(1);
        r.setStroke(strokeColor);
        r.setFill(fillColor);

        // salvo nell'istanza Rectangle il riferimento all'istanza Rettangolo per poi poter recuperare la figura Rettangolo dal model, è un metadato.
        r.setUserData(this);


        if (FiguraSelezionataManager.getInstance().get() == this) {
            r.setEffect(new DropShadow(20, Color.GREY));
        }

        return r;
    }


}