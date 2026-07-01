/*
* Comando che applica uno zoom-in sulla lavagna in corrispondenza del punto cliccato.
*
* Incrementa la scala del gruppo di figure zoomabili con un fattore di 1.1
* e trasla la vista in funzione del click sulla lavagna.
* Non è annullabile.
*
* Autori:
*  - Michele
*/
package com.example.Command;

import com.example.View.LavagnaView;
import javafx.geometry.Point2D;
import javafx.scene.Group;


public class ZoomInCommand implements Command {
    private Group figureZoomabili;
    private double scaleFactor = 1.1;
    private double x, y;


    public ZoomInCommand(double x, double y) {
        this.figureZoomabili = LavagnaView.getInstance().getFigureZoomabili();
        this.x = x;
        this.y = y;
    }

    public void execute() {

        // Trasforma le coordinate locali del punto cliccato in coordinate della scena
        Point2D puntoNellaScena = figureZoomabili.localToScene(x, y);

        // Applica lo zoom
        figureZoomabili.setScaleX(figureZoomabili.getScaleX() * scaleFactor);
        figureZoomabili.setScaleY(figureZoomabili.getScaleY() * scaleFactor);

        // Ricalcola la posizione del punto dopo lo zoom
        Point2D nuovoPuntoNellaScena = figureZoomabili.localToScene(x, y);

        // Calcola differenza e trasla il contenuto per mantenere il punto sotto il mouse
        double dx = nuovoPuntoNellaScena.getX() - puntoNellaScena.getX();
        double dy = nuovoPuntoNellaScena.getY() - puntoNellaScena.getY();

        figureZoomabili.setTranslateX(figureZoomabili.getTranslateX() - dx);
        figureZoomabili.setTranslateY(figureZoomabili.getTranslateY() - dy);

        System.out.println("Zoom In\n");
    }

    @Override
    public void undo() {
        return;
    }
    @Override
    public boolean isUndoable() {
        return false;
    }
}

