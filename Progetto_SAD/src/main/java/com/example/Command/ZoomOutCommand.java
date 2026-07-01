/*
* Comando che gestisce lo zoom-out sulla lavagna centrato sul punto cliccato.
*
* Riduce gradualmente la scala del gruppo di figure zoomabili, mantenendo il focus
* sul punto di interazione. Se già alla scala iniziale, ripristina anche le traslazioni.
* Non è annullabile.
*
* Autori:
*  - Michele
*
*/
package com.example.Command;

import com.example.View.LavagnaView;
import javafx.geometry.Point2D;
import javafx.scene.Group;

public class ZoomOutCommand implements Command {

    private Group figureZoomabili;
    private LavagnaView lavagnaView;
    private double x, y;


    public ZoomOutCommand(double x, double y) {
        this.lavagnaView = LavagnaView.getInstance();
        this.figureZoomabili = lavagnaView.getFigureZoomabili();
        this.x = x;
        this.y = y;
    }


    public void execute() {
        double scalaCorrente = figureZoomabili.getScaleX();
        double scalaTarget = 1.0;

        // Se scalaCorrente - scalaTarget è minima, fai solo reset dello zoom
        if (Math.abs(scalaCorrente - scalaTarget) < 0.01) {
            figureZoomabili.setScaleX(1.0);
            figureZoomabili.setScaleY(1.0);
            figureZoomabili.setTranslateX(0);
            figureZoomabili.setTranslateY(0);
            return;
        }


        double scalaNuova = scalaCorrente - (scalaCorrente - scalaTarget) * 0.2;

        Point2D prima = figureZoomabili.localToScene(x, y);

        figureZoomabili.setScaleX(scalaNuova);
        figureZoomabili.setScaleY(scalaNuova);

         Point2D dopo = figureZoomabili.localToScene(x, y);
         double dx = dopo.getX() - prima.getX();
         double dy = dopo.getY() - prima.getY();

         figureZoomabili.setTranslateX((figureZoomabili.getTranslateX() - dx) * 0.8);
         figureZoomabili.setTranslateY((figureZoomabili.getTranslateY() - dy) * 0.8);

        System.out.println("Zoom Out\n");

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
