/**
 * Classe astratta che rappresenta una figura generica come entità del modello.
 * Contiene le informazioni geometriche (coordinate), stilistiche (colori, font size, rotazione) e il riferimento al nodo JavaFX associato.
 * I dati sono utilizzati dalla View per generare la rappresentazione grafica sulla lavagna.
 * Fornisce inoltre l'interfaccia per la creazione di visualizzazioni temporanee durante le interazioni utente
 * (creazione, spostamento, ridimensionamento) tramite il pattern Strategy.
 *
 * Autori:
 * - Maria Silvana (gestione proprietà geometriche e di stile, generazione nodo JavaFX, integrazione Strategy)
 * - Michele (implementazione getClone() e toString)
 * - Kevin (gestione rotazione)
 */


package com.example.Model;
import com.example.Strategy.FiguraTemporaneaStrategy;
import javafx.scene.Node;
import javafx.scene.paint.Color;


public abstract class Figura {

    protected double x1, y1;    //Coord. OnMousePressed
    protected double x2, y2;    //Coord. OnMouseReleased
    protected int fontSize;
    protected double rotazione;

    protected Color strokeColor;
    protected Color fillColor;
    protected Node nodo;

    public Figura(double x1, double y1, double x2, double y2, Color strokeColor, Color fillColor, int fontSize) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.fontSize = fontSize;
        this.rotazione = 0;

    }


    // collegamento tra un'istanza di Figura nel Model e la corrispondente figura in JavaFX
    public void setNodo(Node nodo) {
        this.nodo = nodo;
    }

    public Node getNodo() {
        return nodo;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setFillColor( Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setRotazione(double rotazione) {this.rotazione = rotazione;}

    public double getRotazione() { return rotazione; }

    public void setFontSize(int size) {this.fontSize = size;}

    public int getFontSize() { return fontSize; }


    public double getX1() {
        return x1;
    }
    public double getY1() {
        return y1;
    }
    public double getX2() {
        return x2;
    }
    public double getY2() {
        return y2;
    }


    public void setX1(double x1) {
        this.x1 = x1;
    }
    public void setX2(double x2) {
        this.x2 = x2;
    }
    public void setY1(double y1) {
        this.y1 = y1;
    }
    public void setY2(double y2) {
        this.y2 = y2;
    }

    /** public abstract Node creaNodoJavaFX();
     *
     * Metodo astratto che consente la creazione del nodo JavaFX associato alla figura.
     * La logica di creazione concreta del nodo viene implementata nelle sottoclassi e invocata dalla View.
     * Il nodo JavaFX non viene creato nel model, ma viene generato nella View a partire dai dati contenuti nel modello.
     * Serve per associare il nodo grafico alla figura, mantenendo il collegamento tra Model e View.
     *
     * Metodo utilizzato dalla classe LavagnaView.
     *
     */

    public abstract Node creaNodoJavaFX();

    /** public abstract FiguraTemporaneaStrategy getTemporaryRenderStrategy();
     *
     * Metodo astratto che restituisce la Strategy associata alla figura per la generazione temporanea
     * del nodo JavaFX durante le interazioni utente (creazione, spostamento, modifica).
     *
     * Questo metodo non implementa logica operativa: restituisce semplicemente l'istanza di Strategy
     * specifica per il tipo di figura. La logica vera e propria di generazione temporanea è delegata
     * all'oggetto FiguraTemporaneaStrategy restituito.
     *
     * Metodo utilizzato dalle classi: RidimensionaFiguraStato, DisegnaFiguraStato, SpostamentoFiguraStato.
     *
     */

    public abstract FiguraTemporaneaStrategy getTemporaryRenderStrategy();

    /*
       getClone() è utilizzata dalla classe PasteCommand.

     */

    public abstract Figura getClone();

    public abstract String toString();

}