/**
 * Classe che rappresenta un Segmento come entità del modello.
 * Contiene le informazioni geometriche e di stile necessarie per descrivere la figura Segmento.
 * I dati sono utilizzati dalla View per generare la rappresentazione grafica sulla lavagna.
 * Inoltre la classe fornisce la strategia di rendering temporaneo tramite il pattern Strategy.
 *
 * Autori:
 *  - Maria Silvana (costruttore, rendering statico del nodo Line a partire dai dati del Model, metodo getTemporaryRenderStrategy())
 *  - Michele (metodi getClone() e toString())
 *  - Kevin (gestione rotazione)
 */


package com.example.Model;

import com.example.State.FiguraSelezionataManager;
import com.example.Strategy.FiguraTemporaneaStrategy;
import com.example.Strategy.SegmentoTemporaneoStrategy;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class Segmento extends Figura {


    public Segmento(double x1, double y1, double x2, double y2, Color strokeColor, Color fillColor) {
        super(x1, y1, x2, y2, strokeColor, fillColor,0); // salva tutto nella superclasse
    }

    @Override
    public Line creaNodoJavaFX() {

        Line line = new Line(x1, y1, x2, y2);

        // collegamento tra un'istanza di Segmento e la corrispondente istanza di Line
        this.setNodo(line);

        line.setRotate(rotazione);
        line.setStrokeWidth(2);
        line.setStroke(strokeColor);
        line.setUserData(this);



        if (FiguraSelezionataManager.getInstance().get() == this) {
            line.setEffect(new DropShadow(20, Color.GREY));
        }

        return line;

    }

    public FiguraTemporaneaStrategy getTemporaryRenderStrategy() {
        return new SegmentoTemporaneoStrategy();
    }

    @Override
    public Figura getClone() {
        int dx = 20;
        Segmento s = new Segmento(x1+dx, y1+dx, x2+dx, y2+dx, strokeColor, fillColor);
        s.setRotazione(rotazione);
        return s;
    }

    public String toString(){
        return "Segmento";
    }
}
