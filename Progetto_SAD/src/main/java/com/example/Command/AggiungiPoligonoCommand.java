/**
 * Comando per aggiungere un poligono personalizzato alla lavagna.
 *
 * A differenza delle figure classiche (come segmenti, rettangoli, ellissi), che vengono
 * definite tramite coordinate di partenza e arrivo (x1, y1, x2, y2), il poligono viene
 * costruito a partire da una lista arbitraria di punti (List<Double>) che rappresentano
 * i vertici della figura. Questo consente di rappresentare forme complesse e non predefinite.
  * Il comando si occupa di:
 * - Creare una nuova figura tramite la factory (ignorando le coordinate standard);
 * - Aggiungerla al modello;
 * - Adattare le dimensioni dell'AnchorPane (lavagna) se necessario, per includere il poligono;
 * - Permettere l'annullamento (undo) della figura aggiunta.
 **/
package com.example.Command;

import com.example.Factory.FiguraFactory;
import com.example.Model.Figura;
import com.example.Model.LavagnaModel;
import com.example.State.FiguraSelezionataManager;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.List;

public class AggiungiPoligonoCommand implements Command{
    private LavagnaModel lavagnaModel;
    private FiguraFactory figuraFactory;
    private AnchorPane lavagna;
    private Color strokeColor;
    private Color fillColor;
    private double rotazione;
    private Figura figuraAggiunta;
    private List<Double> punti;



    public AggiungiPoligonoCommand(LavagnaModel lavagnaModel, FiguraFactory factory, List<Double> punti, AnchorPane lavagna, Color strokeColor, Color fillColor) {
        this.lavagnaModel = lavagnaModel;
        this.figuraFactory = factory;
        this.punti = punti;
        this.lavagna = lavagna;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
        this.rotazione=0;
    }


    @Override
    public void execute() {
        figuraAggiunta = figuraFactory.creaFigura(0, 0, 0, 0, strokeColor, fillColor,0);
        lavagnaModel.aggiungiFigura(figuraAggiunta);
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < punti.size(); i += 2) {
            double x = punti.get(i);
            double y = punti.get(i + 1);
            if (x > maxX) maxX = x;
            if (y > maxY) maxY = y;
        }
        if (maxX > lavagna.getPrefWidth()) lavagna.setPrefWidth(maxX + 100);
        if (maxY > lavagna.getPrefHeight()) lavagna.setPrefHeight(maxY + 100);
        System.out.println("Poligono Aggiunto\n" );
    }

    @Override
    public void undo() {
        FiguraSelezionataManager.getInstance().clear();
        lavagnaModel.rimuoviFigura(figuraAggiunta);
        System.out.println("Poligono Rimosso\n");
    }
    @Override
    public boolean isUndoable() {
        return true;
    }
}


