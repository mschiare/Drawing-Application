/**
* Durante l'interazione dell'utente:
*  - onMousePressed(): memorizza la posizione iniziale della figura selezionata e il punto di partenza dello spostamento.
*  - onMouseDragged(): aggiorna dinamicamente la visualizzazione temporanea della figura durante lo spostamento,
*    applicando uno spostamento relativo (delta) rispetto alla posizione iniziale.
*  - onMouseReleased(): completa lo spostamento effettivo della figura, delegando l'operazione al
*    comando SpostamentoFiguraCommand (pattern Command), che gestisce l'aggiunta alla pila undo/redo.
*
* La visualizzazione temporanea durante il trascinamento viene gestita tramite il pattern Strategy
* (FiguraTemporaneaStrategy), permettendo l'aggiornamento grafico continuo senza modificare il Model fino al rilascio.
*
* Autori:
* - Maria Silvana: (implementazione dello stato di spostamento, integrazione con Strategy e Command)
* - Michele: adattamento della classe allo zoom
*/


package com.example.State;

import com.example.Command.Command;
import com.example.Command.Invoker;
import com.example.Command.SpostamentoFiguraCommand;
import com.example.Model.Figura;
import com.example.Strategy.FiguraTemporaneaStrategy;
import com.example.View.LavagnaView;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class SpostamentoFiguraStato implements Stato {

    double handle_x_iniziale, handle_y_iniziale;

    double figuraX1_iniziale;
    double figuraY1_iniziale;
    double figuraX2_iniziale;
    double figuraY2_iniziale;

    Figura figura = FiguraSelezionataManager.getInstance().get();
    FiguraSelezionataManager figuraSelezionataManager = FiguraSelezionataManager.getInstance();
    FiguraTemporaneaStrategy figuraTemporaneaStrategy = null;
    Node figuraTemporanea = null;


    @Override
    public void onMousePressed(MouseEvent event){


        // Memorizzo la posizione iniziale della figra selezionata
        figuraX1_iniziale = figuraSelezionataManager.get().getX1();
        figuraY1_iniziale = figuraSelezionataManager.get().getY1();
        figuraX2_iniziale = figuraSelezionataManager.get().getX2();
        figuraY2_iniziale = figuraSelezionataManager.get().getY2();

        // Memorizzo dove ho cliccato
        Point2D punto = LavagnaView.getInstance().getFigureZoomabili().sceneToLocal(event.getSceneX(), event.getSceneY());
        handle_x_iniziale = punto.getX();
        handle_y_iniziale = punto.getY();

        figuraTemporaneaStrategy = figuraSelezionataManager.get().getTemporaryRenderStrategy();

        figuraTemporanea = figuraTemporaneaStrategy.crea(handle_x_iniziale, handle_y_iniziale,figura.getRotazione());

        //Aggiungo la figura temporanea alla lavagna
        LavagnaView.getInstance().getFigureZoomabili().getChildren().add(figuraTemporanea);

    }

    @Override
    public void onMouseDragged(MouseEvent event){

        // Calcolo dello spostamento rispetto al punto iniziale
        Point2D punto = LavagnaView.getInstance().getFigureZoomabili().sceneToLocal(event.getSceneX(), event.getSceneY());
        double deltaX = punto.getX() - handle_x_iniziale;
        double deltaY = punto.getY() - handle_y_iniziale;

        // Applico lo spostamento alla posizione iniziale della figura
        double nuovoX1 = figuraX1_iniziale + deltaX;
        double nuovoY1 = figuraY1_iniziale + deltaY;
        double nuovoX2 = figuraX2_iniziale + deltaX;
        double nuovoY2 = figuraY2_iniziale + deltaY;

        figuraTemporaneaStrategy.aggiorna(figuraTemporanea, nuovoX1,nuovoY1, nuovoX2,nuovoY2, figura.getRotazione());
    }

    @Override
    public void onMouseReleased(MouseEvent event){

        LavagnaView.getInstance().getFigureZoomabili().getChildren().remove(figuraTemporanea);

        Point2D punto = LavagnaView.getInstance().getFigureZoomabili().sceneToLocal(event.getSceneX(), event.getSceneY());
        double x2 = punto.getX();
        double y2 = punto.getY();

        // figura non può essere spostata fuori dal foglio
        if(x2<0 || y2<0) {
            return;
        }

        Command cmd = new SpostamentoFiguraCommand(figuraSelezionataManager.get(),x2,y2,handle_x_iniziale,handle_y_iniziale);
        Invoker.getInstance().executeCommand(cmd);
        StatoManager.getInstance().setStato(new SelezionaFiguraStato());


    }
    @Override
    public void onSliderChanged(double sliderValue) {return;}
    @Override
    public void onSliderReleased(double sliderValue){return;}

    public void onMouseClicked(MouseEvent event){return;}

}
