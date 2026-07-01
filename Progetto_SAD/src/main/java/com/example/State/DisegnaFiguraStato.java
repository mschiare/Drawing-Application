/**
 * Stato che gestisce la modalità di disegno di una nuova figura sulla lavagna, secondo il pattern State.
 *
 * Durante l'interazione dell'utente con il mouse:
 *  - onMousePressed(): viene inizializzata la creazione di una figura temporanea visualizzata durante il trascinamento;
 *  - onMouseDragged(): la figura temporanea viene aggiornata dinamicamente seguendo il movimento del mouse;
 *  - onMouseReleased(): al termine dell'interazione viene effettivamente creata e aggiunta la figura al modello,
 *    tramite l'invocazione del comando AggiungiFiguraCommand.
 *
 * La creazione della figura concreta avviene tramite il pattern Factory, che astrae il tipo specifico di figura
 * (rettangolo, ellisse, poligono, testo, segmento) e consente di mantenere il codice indipendente dalle classi concrete.
 *
 * La gestione della figura temporanea viene demandata al pattern Strategy (FiguraTemporaneaStrategy), che gestisce
 * la generazione e l'aggiornamento visivo della view durante il disegno.
 *
 * Questa classe viene istanziata nel Controller.
 *
 * Autori:
 *  - Maria Silvana: (implementazione dello stato di disegno, integrazione con Factory, Strategy e Command Pattern)
 *  - Michele: adattamento della classe allo Zoom
 */

 package com.example.State;

import com.example.Command.AggiungiFiguraCommand;
import com.example.Command.Command;
import com.example.Command.Invoker;
import com.example.Factory.FiguraFactory;
import com.example.Model.LavagnaModel;
import com.example.Strategy.FiguraTemporaneaStrategy;
import com.example.View.LavagnaView;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DisegnaFiguraStato implements Stato{
    private double x1, y1;
    private Node figuraTemporanea;
    private AnchorPane lavagna;
    private LavagnaModel model;
    private ColorPicker strokeColor, fillColor;
    private ComboBox fontSizeComboBox;
    private Group figureInserite;
    private FiguraFactory figuraFactory;
    private FiguraTemporaneaStrategy figuraTemporaneaStrategy;


    public DisegnaFiguraStato(FiguraFactory figuraFactory, AnchorPane lavagna, LavagnaModel model, ColorPicker strokeColor, ColorPicker fillColor, ComboBox fontSizeComboBox) {
        this.figuraFactory = figuraFactory;
        this.lavagna = lavagna;
        this.model = model;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
        this.figureInserite = LavagnaView.getInstance().getFigureZoomabili();
        this.fontSizeComboBox = fontSizeComboBox;

    }

    @Override
    public void onMousePressed(MouseEvent event) {

        Point2D punto = figureInserite.sceneToLocal(event.getSceneX(), event.getSceneY());
        x1 = punto.getX();
        y1 = punto.getY();

        figuraTemporaneaStrategy = figuraFactory.creaFigura(0,0,0,0,null, null,0).getTemporaryRenderStrategy();
        figuraTemporanea = figuraTemporaneaStrategy.crea(x1,y1,0);
        figureInserite.getChildren().add((figuraTemporanea));
    }

    @Override
    public void onMouseDragged(MouseEvent e) {

        Point2D punto = figureInserite.sceneToLocal(e.getSceneX(), e.getSceneY());
        double x2 = punto.getX();
        double y2 = punto.getY();

        figuraTemporaneaStrategy.aggiorna(figuraTemporanea,x1,y1,x2,y2,0);
    }


    @Override
    public void onMouseReleased(MouseEvent e) {
        Point2D punto = figureInserite.sceneToLocal(e.getSceneX(), e.getSceneY());
        double x2 = punto.getX();
        double y2 = punto.getY();


        figureInserite.getChildren().remove((figuraTemporanea));

       if(x1<0 || y1<0 || x2<0 || y2<0) {
            figuraTemporanea = null;
            return;
        }


        // dimensione minima figura al click sulla lavagna
        if(x2==x1 && y2==y1) {
            x2 = x1 + 30;
            y2 = y1 + 30;
        }


        // Usa Command se vuoi supportare Undo
        Command cmd = new AggiungiFiguraCommand(model, figuraFactory, lavagna, x1, y1, x2, y2, strokeColor.getValue(), fillColor.getValue(),(int)fontSizeComboBox.getValue());
        Invoker.getInstance().executeCommand(cmd);

        figuraTemporanea = null;
    }
    @Override
    public void onSliderChanged(double sliderValue) {return;}
    @Override
    public void onSliderReleased(double sliderValue){return;}
    @Override
    public void onMouseClicked(MouseEvent event) {}


}
