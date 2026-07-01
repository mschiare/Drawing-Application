/*
* Classe che rappresenta una griglia disegnata sulla lavagna.
*
* Permette di creare un nodo ('Group') composto da linee verticali e orizzontali,
* in base al numero di righe e colonne specificato. Il metodo 'creaNodoJavaFX' restituisce
* la griglia come nodo JavaFX.
*
* Autori:
*  -Michele
*/
package com.example.Model;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Griglia {

    double nRighe;
    double nColonne;
    double larghezzaLavagna;
    double altezzaLavagna;
    Color strokeColor;



    public Griglia(double nRighe, double nColonne, double x2, double y2, Color strokeColor) {
        this.nRighe = nRighe;
        this.nColonne = nColonne;
        this.larghezzaLavagna = x2;
        this.altezzaLavagna = y2;
        this.strokeColor = strokeColor;
    }

    public Node creaNodoJavaFX() {
        Group griglia = new Group();
        double spazioTraRighe = altezzaLavagna / nRighe;
        double spazioTraColonne = larghezzaLavagna/nColonne;

        //linee verticali
        for (int i = 1; i < nColonne; i++) {
            double x = i * spazioTraColonne;
            Line line = new Line(x, 0, x, altezzaLavagna);
            line.setStroke(strokeColor);
            line.setStrokeWidth(1);
            griglia.getChildren().add(line);
        }

        //linee orizzonatali
        for (int i = 1; i < nRighe; i++) {
            double y = i * spazioTraRighe;
            Line line = new Line(0, y, larghezzaLavagna,y);
            line.setStroke(strokeColor);
            line.setStrokeWidth(1);
            griglia.getChildren().add(line);
        }

        griglia.setUserData(this);

        return griglia;
    }
}
