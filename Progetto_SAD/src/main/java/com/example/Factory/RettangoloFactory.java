/*
* Factory concreta per la creazione di oggetti 'Rettangolo'.
*
* Implementa l'interfaccia 'FiguraFactory' e fornisce un'istanza di 'Rettangolo'
* basata sulle coordinate e sui colori specificati.
*
* Autori:
*  - Michele
*
*/
package com.example.Factory;

import com.example.Model.Figura;
import com.example.Model.Rettangolo;
import javafx.scene.paint.Color;

public class RettangoloFactory implements FiguraFactory {

    @Override
    public Figura creaFigura(double x1, double y1, double x2, double y2, Color strokeColor, Color fillColor, int fontSize) {
        return new Rettangolo(x1, y1, x2, y2, strokeColor, fillColor);
    }

}
