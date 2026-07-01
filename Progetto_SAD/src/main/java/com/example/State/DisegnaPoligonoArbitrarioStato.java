/**
 * Stato dell'applicazione che gestisce l'interazione per la creazione di un poligono arbitrario
 * tramite click successivi dell'utente sulla lavagna.
 *
 * Funzionamento:
 * - Al primo click viene creato un nuovo poligono vuoto e viene memorizzato il primo punto,
 *   evidenziato con un piccolo cerchio.
 * - Ogni click successivo aggiunge un vertice al poligono.
 * - Se l'utente fa doppio click (con almeno 3 vertici), il poligono si chiude: l'ultimo punto
 *   inserito (il doppio click) viene rimosso per evitare duplicati e il poligono viene finalizzato.
 *
 * Dopo la chiusura:
 * - Viene eseguito il comando AggiungiPoligonoCommand per aggiungere definitivamente la figura al modello.
 * - Il poligono temporaneo e il cerchietto del primo punto vengono rimossi dalla vista.
 * - Si prepara la lavagna per iniziare la creazione di un nuovo poligono.
 *
 * Note importanti:
 * - La classe utilizza un Polygon di JavaFX solo per fornire il feedback visivo
 *   mentre l'utente crea la figura.
 * - La validazione dei punti (coordinate >= 0) evita poligoni non validi.
 * - La gestione del doppio click per chiudere il poligono rende l'interazione naturale e intuitiva.
 */

package com.example.State;

import com.example.Command.AggiungiPoligonoCommand;
import com.example.Command.Command;
import com.example.Command.Invoker;
import com.example.Model.LavagnaModel;
import com.example.View.LavagnaView;
import com.example.Factory.PoligonoArbitrarioFactory;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;


public class DisegnaPoligonoArbitrarioStato implements Stato{

    private Polygon poligono = null;
    private boolean figuraChiusa = false;

    private final AnchorPane lavagna;
    private final LavagnaModel lavagnaModel;
    private final ColorPicker strokeColor;
    private final ColorPicker fillColor;
    private final Group figureInserite;
    private Point2D primoPunto = null;
    private Circle cerchiettoPrimoPunto = null;

    public DisegnaPoligonoArbitrarioStato(AnchorPane lavagna, LavagnaModel lavagnaModel, ColorPicker strokeColor, ColorPicker fillColor) {
        this.lavagna = lavagna;
        this.lavagnaModel = lavagnaModel;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
        this.figureInserite = LavagnaView.getInstance().getFigureZoomabili();
    }


    public void onMouseClicked(MouseEvent event) {
        if (figuraChiusa) return;

        if (poligono == null || !figureInserite.getChildren().contains(poligono)) {
            poligono = new Polygon();
            poligono.setStroke(strokeColor.getValue());
            poligono.setFill(fillColor.getValue());
            figureInserite.getChildren().add(poligono);

            // Reset del primo punto
            primoPunto = null;
            if (cerchiettoPrimoPunto != null) {
                figureInserite.getChildren().remove(cerchiettoPrimoPunto);
                cerchiettoPrimoPunto = null;
            }
        }

        Point2D punto = figureInserite.sceneToLocal(event.getSceneX(), event.getSceneY());

        if (punto.getX() < 0 || punto.getY() < 0) {
            // Punto non valido, lo ignoriamo
            return;
        }
        poligono.getPoints().addAll(punto.getX(), punto.getY());

        if (primoPunto == null) {
            primoPunto = punto;
            cerchiettoPrimoPunto = new Circle(punto.getX(), punto.getY(), 5);
            cerchiettoPrimoPunto.setFill(strokeColor.getValue());
            figureInserite.getChildren().add(cerchiettoPrimoPunto);
        }

        poligono.setStroke(strokeColor.getValue());
        poligono.setFill(fillColor.getValue());

        // Se l’utente fa doppio click, chiudi il poligono
        if (event.getClickCount() == 2 && poligono.getPoints().size() >= 6) {
            poligono.getPoints().removeLast();
            poligono.getPoints().removeLast();// Rimuovi l'ultimo punto per chiudere il poligono
            chiudiPoligono();
        }
    }

    @Override
    public void onMousePressed(MouseEvent event) {}
    @Override
    public void onMouseDragged(MouseEvent event) {}
    @Override
    public void onMouseReleased(MouseEvent event) {}
    @Override
    public void onSliderChanged(double sliderValue) {return;}
    @Override
    public void onSliderReleased(double sliderValue){return;}

    private void chiudiPoligono() {
        figuraChiusa = true;

        // Verifica che tutti i punti siano validi
        for (Double coord : poligono.getPoints()) {
            if (coord < 0) {
                figureInserite.getChildren().remove(poligono);
                poligono = null;
                return;
            }
        }
        figureInserite.getChildren().remove(poligono);
        // Esegui il comando per aggiungere la figura
        Command cmd = new AggiungiPoligonoCommand(
                lavagnaModel,
                new PoligonoArbitrarioFactory(poligono.getPoints()),
                poligono.getPoints(),
                lavagna,
                strokeColor.getValue(),
                fillColor.getValue()
        );
        Invoker.getInstance().executeCommand(cmd);
        if (cerchiettoPrimoPunto != null) {
            figureInserite.getChildren().remove(cerchiettoPrimoPunto);
            cerchiettoPrimoPunto = null;
        }
        primoPunto = null;
        poligono = new Polygon();
        poligono.setFill(fillColor.getValue());
        poligono.setStroke(strokeColor.getValue());
        figureInserite.getChildren().add(poligono);
        figuraChiusa = false;
    }
}
