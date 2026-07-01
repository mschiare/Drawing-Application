/**
 * Factory specializzata per la creazione di poligoni arbitrari.
 *
 * A differenza delle factory per figure classiche segmento, rettangolo ed ellisse,
 * che generano le figure sulla base di coordinate standard (x1, y1, x2, y2),
 * questa factory utilizza una lista di punti (List<Double>) per costruire
 * una figura di tipo PoligonoArbitrario.
 *
 * I punti rappresentano direttamente i vertici del poligono e permettono
 * la definizione di forme con qualsiasi numero di lati.
 *
 * Il metodo creaFigura} ignora le coordinate fornite come parametri,
 * affidandosi unicamente alla lista di punti passata nel costruttore della factory.
 * Questo approccio consente la creazione di figure più complesse.
 */
package com.example.Factory;

import com.example.Model.Figura;
import com.example.Model.PoligonoArbitrario;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PoligonoArbitrarioFactory implements FiguraFactory{
    private List<Double> punti;

    public PoligonoArbitrarioFactory(List<Double> punti) {
        this.punti = new ArrayList<>(punti);
    }

    @Override
    public Figura creaFigura(double x1, double y1, double x2, double y2, Color strokeColor, Color fillColor, int fontSize) {
        // Implementazione per creare un poligono arbitrario
        // Utilizza i punti forniti per creare la figura
        return new PoligonoArbitrario(punti, strokeColor, fillColor);
    }
}
