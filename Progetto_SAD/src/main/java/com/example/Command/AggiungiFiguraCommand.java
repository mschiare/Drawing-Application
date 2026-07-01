/**
* Classe che implementa il comando per l'aggiunta di una nuova figura alla lavagna,
* secondo il pattern Command, consentendo l'annullamento (undo) dell'operazione.
* La creazione della figura avviene tramite il pattern Factory, che astrae la logica
* di istanziazione e consente di generare dinamicamente diverse tipologie di figure
* (rettangolo, ellisse, segmento, testo) senza che il comando debba conoscere il tipo specifico di figura da creare.
* In questo modo il Command rimane indipendente dalle classi concrete delle figure.
*
* Questa classe viene istanziata nella classe DisegnaFiguraStato.
*
* Autori:
* - Maria Silvana: Costruttore, execute()
* - Mirko: gestione undo(), controlli ScrollPane
*/
package com.example.Command;

import com.example.Factory.FiguraFactory;
import com.example.Model.Figura;
import com.example.Model.LavagnaModel;
import com.example.State.FiguraSelezionataManager;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class AggiungiFiguraCommand implements Command {
    private LavagnaModel lavagnaModel;
    private FiguraFactory figuraFactory;
    private AnchorPane lavagna;
    private double x1, y1, x2, y2;
    Color strokeColor;
    Color fillColor;
    private double rotazione;
    private Figura figuraAggiunta;
    private int fontSize;


    public AggiungiFiguraCommand(LavagnaModel lavagnaModel, FiguraFactory factory, AnchorPane lavagna, double x1, double y1, double x2, double y2, Color strokeColor, Color fillColor, int fontSize) {
        this.lavagnaModel = lavagnaModel;
        this.figuraFactory = factory;
        this.lavagna = lavagna;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.strokeColor= strokeColor;
        this.fillColor = fillColor;
        this.fontSize = fontSize;
        this.rotazione = 0;

    }
    @Override
    public void execute() {
        // creaFigura rappresenta il Factory Method
        figuraAggiunta = figuraFactory.creaFigura(x1, y1, x2 , y2, strokeColor, fillColor, fontSize);
        lavagnaModel.aggiungiFigura(figuraAggiunta);

        // scroll pane
        double MaxX = Math.max(x1, x2);
        double MaxY = Math.max(y1,y2);

        if(MaxX > lavagna.getPrefWidth()) {
            lavagna.setPrefWidth(MaxX + 100);
        }
        if(MaxY > lavagna.getPrefHeight()) {
            lavagna.setPrefHeight(MaxY + 100);
        }

        System.out.println("Aggiunta: " + figuraAggiunta + "\n");
    }
    @Override
    public void undo() {
        if(figuraAggiunta != null) {
            FiguraSelezionataManager.getInstance().clear();
            lavagnaModel.rimuoviFigura(figuraAggiunta);
            System.out.println("Rimossa figura: " + figuraAggiunta);
        }
    }
    @Override
    public boolean isUndoable() {
        return true;
    }


}

