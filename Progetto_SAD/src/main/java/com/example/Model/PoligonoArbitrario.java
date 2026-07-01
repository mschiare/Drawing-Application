/**
 * Modello che rappresenta un poligono arbitrario, definito da una lista di vertici (x, y).
 *
 * A differenza delle figure geometriche classiche rettangolo, segmento, ellisse
 * che vengono descritte tramite coordinate standard (x1, y1, x2, y2), il
 * PoligonoArbitrario utilizza una lista dinamica di punti List<Double>
 * per costruire forme personalizzate con un numero variabile di lati.
 *
 * Caratteristiche principali:
 * - Il metodo creaNodoJavaFX() costruisce un Polygon di JavaFX
 *   utilizzando direttamente i vertici specificati nella lista.
 * - Le coordinate x1, y1, x2, y2 ereditate dalla superclasse Figura
 *   vengono utilizzate solo per compatibilità con l'interfaccia comune.
 *
 * - Implementa il metodo getClone() spostando il poligono di un offset fisso
 *   (utile per i comand di Copia , Taglia e Incolla).
 * - Integra un effetto grafico per evidenziare la selezione tramite DropShadow.
 * - Fornisce una strategia di ridimensionamento dedicata tramite PoligonoArbitrarioStrategy.
 *
 * Questa classe permette quindi la rappresentazione e manipolazione di figure geometriche
 * non regolari o non predefinite, ampliando la flessibilità del sistema grafico.
 */

package com.example.Model;

import com.example.State.FiguraSelezionataManager;
import com.example.Strategy.FiguraTemporaneaStrategy;
import com.example.Strategy.PoligonoArbitrarioStrategy;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import java.util.ArrayList;
import java.util.List;

public class PoligonoArbitrario extends Figura{
    private final List<Double> punti;

    public PoligonoArbitrario(List<Double> punti, Color stroke, Color fill) {
        super(0,0,0,0, stroke, fill,0); // Valori dummy per x1, y1, x2, y2
        this.punti = punti;
    }

    public List<Double> getPunti() {
        return punti;
    }

    @Override
    public Node creaNodoJavaFX(){
        Polygon poligono = new Polygon();
        poligono.getPoints().addAll(punti);
        poligono.setStrokeWidth(1);
        poligono.setStroke(getStrokeColor());
        poligono.setFill(getFillColor());
        poligono.setUserData(this);
        poligono.setRotate(rotazione);

        if (FiguraSelezionataManager.getInstance().get() == this) {
            poligono.setEffect(new DropShadow(20, Color.GREY));
        }
        this.setNodo(poligono);
        return poligono;
    }
    @Override
    public FiguraTemporaneaStrategy getTemporaryRenderStrategy() {return new PoligonoArbitrarioStrategy();}
    @Override
    public Figura getClone() {
        int dx = 20;
        List<Double> punticlone = new ArrayList<>(punti);
        for (int i = 0; i < punticlone.size(); i++) {
            if (i % 2 == 0) { // X coordinate
                punticlone.set(i, punticlone.get(i) + dx);
            } else { // Y coordinate
                punticlone.set(i, punticlone.get(i) + dx);
            }
        }
        return new PoligonoArbitrario(punticlone, getStrokeColor(), getFillColor());

    }
    @Override
    public double getX1() {
        return punti.get(0);
    }
    @Override
    public double getX2() {
        if( (punti.size() / 2) % 2 == 1) {
            return punti.get(punti.size()/2-1);
        }
        return punti.get(punti.size()/2);
    }
    @Override
    public double getY1() {
        return punti.get(1);
    }
    @Override
    public double getY2() {
        if( (punti.size() / 2) % 2 == 1) {
            return punti.get(punti.size()/2);
        }
        return punti.get(punti.size()/2+1);
    }

    @Override
    public void setX2(double x2) {
        if( (punti.size() / 2) % 2 == 1) {
            punti.set(punti.size() / 2 - 1, x2);

        }
        punti.set(punti.size() / 2, x2);
    }
    @Override
    public void setY2(double y2) {
        if( (punti.size() / 2) % 2 == 1) {
            punti.set(punti.size() / 2, y2);
        }
        punti.set(punti.size() / 2 + 1, y2);
    }

    @Override
    public String toString(){
        return "Poligono";
    }

}
