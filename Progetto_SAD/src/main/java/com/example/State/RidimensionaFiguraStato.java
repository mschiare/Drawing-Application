/**
 * Stato che gestisce l'operazione di ridimensionamento di una figura selezionata sulla lavagna,
 * secondo il pattern State.
 *
 * Durante l'interazione dell'utente:
 *  - onMousePressed(): memorizza il punto iniziale di ridimensionamento e crea la figura temporanea visualizzata durante l'interazione.
 *  - onMouseDragged(): aggiorna dinamicamente la rappresentazione temporanea della figura durante il ridimensionamento.
 *  - onMouseReleased(): applica il ridimensionamento definitivo tramite l'invocazione del comando RidimensionaFiguraCommand
 *    (pattern Command), permettendo la gestione centralizzata dell'undo/redo.
 *
 * La gestione grafica temporanea durante l'interazione è delegata al pattern Strategy (FiguraTemporaneaStrategy),
 * che permette di generare e aggiornare il preview visivo in modo uniforme per tutte le figure.
 *
 * Autori:
 *  - Maria Silvana (implementazione dello stato di ridimensionamento, integrazione con Strategy e Command)
 */


 package com.example.State;

import com.example.Command.Command;
import com.example.Command.Invoker;
import com.example.Command.RidimensionaFiguraCommand;
import com.example.Model.*;
import com.example.Strategy.FiguraTemporaneaStrategy;
import com.example.View.LavagnaView;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;




public class RidimensionaFiguraStato implements Stato {

    private double x1, y1;

    Figura figura = FiguraSelezionataManager.getInstance().get();

    double x1_init = FiguraSelezionataManager.getInstance().get().getX1();
    double y1_init = FiguraSelezionataManager.getInstance().get().getY1();

    Node figuraTemporaneaFX = null;
    FiguraTemporaneaStrategy strategy = figura.getTemporaryRenderStrategy();




    @Override
    public void onMousePressed(MouseEvent event) {

        Point2D punto = LavagnaView.getInstance().getFigureZoomabili().sceneToLocal(event.getSceneX(), event.getSceneY());
        x1 = punto.getX();
        y1 = punto.getY();

        figuraTemporaneaFX = strategy.crea(x1, y1,figura.getRotazione());
        LavagnaView.getInstance().getFigureZoomabili().getChildren().add(figuraTemporaneaFX);
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        Point2D punto = LavagnaView.getInstance().getFigureZoomabili().sceneToLocal(e.getSceneX(), e.getSceneY());
        double x2 = punto.getX();
        double y2 = punto.getY();


        strategy.aggiorna(figuraTemporaneaFX, x1_init, y1_init, x2, y2, figura.getRotazione());
    }


    @Override
    public void onMouseReleased(MouseEvent e) {

        LavagnaView.getInstance().getFigureZoomabili().getChildren().remove(figuraTemporaneaFX);

        Point2D punto = LavagnaView.getInstance().getFigureZoomabili().sceneToLocal(e.getSceneX(), e.getSceneY());
        double x2 = punto.getX();
        double y2 = punto.getY();

        if(x1<0 || y1<0 || x2<0 || y2<0) {
            figuraTemporaneaFX = null;
            return;
        }

        Command cmd = new RidimensionaFiguraCommand(x2, y2, x1, y1);
        Invoker.getInstance().executeCommand(cmd);
        StatoManager.getInstance().setStato(new SelezionaFiguraStato());

    }
    @Override
    public void onSliderChanged(double sliderValue) {return;}
    @Override
    public void onSliderReleased(double sliderValue){return;}
    @Override
    public void onMouseClicked(MouseEvent event) {
        return;
    }
}
