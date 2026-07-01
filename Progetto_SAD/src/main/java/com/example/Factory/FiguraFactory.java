/*
* Interfaccia `FiguraFactory` viene utilizzata per implementare il pattern Factory.
*
* Consente la creazione di diverse figure geometriche attraverso il metodo 'creaFigura(...)'.
* Il metodo 'creaFigura()' accetta le coordinate iniziali e finali (x1, y1, x2, y2) e i colori di bordo e riempimento,
* restituendo un oggetto 'Figura' corrispondente alla specifica implementazione della factory.
*
* Autori:
*  - Michele
*
*/
package com.example.Factory;

import com.example.Model.Figura;
import javafx.scene.paint.Color;

public interface FiguraFactory {

    public Figura creaFigura(double x1, double y1, double x2, double y2, Color strokeColor, Color fillColor, int fontSize);
    /* Bisogna creare la classe Figura per poter utilizzare il metodo creaFigura*/
}
