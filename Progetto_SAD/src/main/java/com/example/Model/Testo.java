/**
 * Classe che rappresenta una figura di tipo Testo come entità del modello.
 * Estende Figura per uniformare il comportamento del testo con le altre figure geometriche,
 * consentendo così che il testo venga trattato come una figura standard durante le varie operazioni
 * di disegno, selezione, spostamento, ridimensionamento.
 *
 * Contiene il contenuto testuale, le informazioni geometriche (coordinate), stilistiche (colori, font size, rotazione),
 * e genera il nodo grafico JavaFX associato alla figura testo corrispondente.
 *
 * La creazione concreta del nodo grafico avviene nella View tramite il metodo creaNodoJavaFX().
 *
 * Fornisce inoltre la Strategy per la generazione delle visualizzazioni temporanee durante le interazioni utente
 * (creazione, spostamento, ridimensionamento) tramite il pattern Strategy.
 *
 * Autori:
 *  - Maria Silvana (modellazione dati, gestione contenuto testuale, generazione nodo JavaFX, integrazione Strategy)
 *  - Michele (metodi getClone() e toString())
 *  - Kevin (gestione rotazione)
 */

package com.example.Model;

import com.example.State.FiguraSelezionataManager;
import com.example.Strategy.FiguraTemporaneaStrategy;
import com.example.Strategy.TextAreaTemporaneoStrategy;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Testo extends Figura{

    private String contenuto;
    private Group gruppo;
    double padding = 10;
    private TextArea textArea= new TextArea();


    public Testo(double x1, double y1, double x2, double y2, Color strokeColor, Color fillColor, int fontSize, String content) {
        super(x1, y1, x2, y2, strokeColor, fillColor, fontSize);
        this.contenuto = content;
    }

    public void setContenuto(String contenuto) { this.contenuto = contenuto;}


    public Node creaNodoJavaFX(){

        gruppo = new Group();

        // Registra un listener sulla proprietà textProperty() del TextArea.
        // Ogni volta che il contenuto del TextArea viene modificato dall'utente,
        // aggiorna automaticamente l'attributo contenuto nel Model (classe Testo).
        // In questo modo il Model resta sempre sincronizzato con il testo visualizzato nella View.
        textArea.textProperty().addListener((obs, oldVal, newVal) -> setContenuto(newVal));

        textArea.setLayoutX(Math.min(x1,x2)+padding);
        textArea.setLayoutY(Math.min(y1,y2)+padding);
        textArea.setPrefWidth(Math.abs(x2 - x1)-2*padding);
        textArea.setPrefHeight(Math.abs(y2 - y1)-2*padding);
        //textArea.setMinWidth(10);
        //textArea.setMinHeight(10);
        textArea.setFont(Font.font(this.fontSize));
        textArea.setText(contenuto);
        textArea.setRotate(rotazione);
        textArea.setStyle("-fx-control-inner-background:" + String.format("#%02X%02X%02X",
                (int) (fillColor.getRed() * 255),
                (int) (fillColor.getGreen() * 255),
                (int) (fillColor.getBlue() * 255)) + "; -fx-border-color:white;"+ "-fx-text-fill:" + String.format("#%02X%02X%02X",
                (int) (strokeColor.getRed() * 255),
                (int) (strokeColor.getGreen() * 255),
                (int) (strokeColor.getBlue() * 255)) + ";");



        Rectangle box = new Rectangle();
        box.setX(Math.min(x1,x2));
        box.setY(Math.min(y1,y2));
        box.setWidth(Math.abs(x2 - x1));
        box.setHeight(Math.abs(y2 - y1));
        box.setFill(fillColor);
        box.setStroke(strokeColor);
        // aggiunto
        box.setRotate(rotazione);

        if (FiguraSelezionataManager.getInstance().get() == this) {
            box.setEffect(new DropShadow(20, Color.GREY));
        }

        // collegamento tra Figura Testo e corrispondente nodo JavaFX Rectangle
        this.setNodo(box);

        gruppo.getChildren().add(box);
        gruppo.getChildren().add(textArea);
        gruppo.setUserData(this);

        return gruppo;


    }
    public String getContenuto(){
        return this.contenuto;
    }

    public FiguraTemporaneaStrategy getTemporaryRenderStrategy(){
        return new TextAreaTemporaneoStrategy();
    }


    @Override
    public Figura getClone() {
        int dx = 20;
        Testo t = new Testo(x1+dx, y1+dx, x2+dx, y2+dx, strokeColor, fillColor, fontSize,contenuto);
        t.setContenuto(contenuto);
        t.setRotazione(rotazione);
        return t;
    }

    public String toString() {
        return ("Testo");
    }
}
