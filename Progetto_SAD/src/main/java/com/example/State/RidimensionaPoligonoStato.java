/**
 * Stato dell'applicazione responsabile del ridimensionamento di un PoligonoArbitrario.
 *
 * A differenza delle figure classiche: rettangoli, ellissi e segmenti, il PoligonoArbitrario
 * è definito da una lista di vertici che non segue una struttura fissa. Questo comporta che
 * il ridimensionamento non possa essere applicato semplicemente modificando larghezza e altezza,
 * ma debba avvenire tramite una trasformazione geometrica dei vertici.
 *
 * Funzionalità principali:
 * - Quando l'utente preme su un vertice del poligono, viene creata una figura temporanea
 *   per fornire feedback visivo durante l'interazione.
 * - Durante il trascinamento del mouse, viene calcolata una trasformazione omotetica
 *   e rotazionale dei vertici originali, rispetto al baricentro della figura.
 * - Al rilascio del mouse, viene eseguito il comando RidimensionaPoligonoCommand
 *   per rendere permanente la modifica e supportare undo.
 *
 * Il metodo calcolaNuoviVertici implementa la logica di ridimensionamento tramite:
 * - Calcolo del centroide del poligono.
 * - Calcolo del rapporto di scala e della rotazione necessari per trasformare un vertice originale
 *   nella nuova posizione.
 * - Applicazione di questa trasformazione a tutti i vertici.
 *
 * Questo stato è necessario per garantire un ridimensionamento coerente e intuitivo
 * anche in presenza di forme non regolari, rispettando la natura arbitraria della figura.
 */

package com.example.State;

import com.example.Command.Command;
import com.example.Command.Invoker;
import com.example.Command.RidimensionaPoligonoCommand;
import com.example.Model.*;
import com.example.Strategy.*;
import com.example.View.LavagnaView;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;


public class RidimensionaPoligonoStato implements Stato {

    private double x1, y1;

    Figura figura = FiguraSelezionataManager.getInstance().get();
    Node figuraTemporaneaFX = null;
    FiguraTemporaneaStrategy strategy = figura.getTemporaryRenderStrategy();

    private List<Double> vecchiPunti = new ArrayList<>();



    @Override
    public void onMousePressed(MouseEvent event) {

        Point2D punto = LavagnaView.getInstance().getFigureZoomabili().sceneToLocal(event.getSceneX(), event.getSceneY());
        x1 = punto.getX();
        y1 = punto.getY();

        PoligonoArbitrario p = (PoligonoArbitrario) figura;
        vecchiPunti = new ArrayList<>(p.getPunti());
        figuraTemporaneaFX = ((PoligonoArbitrarioStrategy)strategy).creaPoligono(p.getPunti(), p.getRotazione());

        LavagnaView.getInstance().getFigureZoomabili().getChildren().add(figuraTemporaneaFX);
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        Point2D punto = LavagnaView.getInstance().getFigureZoomabili().sceneToLocal(e.getSceneX(), e.getSceneY());
        double x2 = punto.getX();
        double y2 = punto.getY();


        PoligonoArbitrario p = (PoligonoArbitrario) figura;
        int indiceCentrale = p.getPunti().size() / 4;
        List<Double> nuoviVertici = calcolaNuoviVertici(p.getPunti(), indiceCentrale , x2, y2);

        // Aggiorna la lista dei vertici originali con la nuova lista
        p.getPunti().clear();
        p.getPunti().addAll(nuoviVertici);

        ((PoligonoArbitrarioStrategy)strategy).aggiornaPoligono(figuraTemporaneaFX, p.getPunti(), p.getRotazione());
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

        Command cmd = new RidimensionaPoligonoCommand((PoligonoArbitrario) figura, vecchiPunti);
        Invoker.getInstance().executeCommand(cmd);
        StatoManager.getInstance().setStato(new SelezionaFiguraStato());
    }


    public static List<Double> calcolaNuoviVertici(List<Double> vertici, int indiceVertice, double newX, double newY) {
        int n = vertici.size() / 2;

        // Punto originale (handle)
        double x0 = vertici.get(indiceVertice * 2);
        double y0 = vertici.get(indiceVertice * 2 + 1);

        // Punto di destinazione (dove è stato trascinato)
        double x1 = newX;
        double y1 = newY;

        // Calcola centroide
        double cx = 0, cy = 0;
        for (int i = 0; i < vertici.size(); i += 2) {
            cx += vertici.get(i);
            cy += vertici.get(i + 1);
        }
        cx /= n;
        cy /= n;

        // Vettori dal centroide al punto spostato (prima e dopo)
        double dx0 = x0 - cx;
        double dy0 = y0 - cy;
        double dx1 = x1 - cx;
        double dy1 = y1 - cy;

        // Modulo dei vettori (per calcolare scala)
        double lung0 = Math.hypot(dx0, dy0);
        double lung1 = Math.hypot(dx1, dy1);
        if (lung0 == 0) lung0 = 1e-10; // evita divisione per zero

        double scala = lung1 / lung0;

        // Calcola angolo di rotazione
        double angolo0 = Math.atan2(dy0, dx0);
        double angolo1 = Math.atan2(dy1, dx1);
        double rotazione = angolo1 - angolo0;

        // Applica trasformazione a tutti i vertici
        List<Double> nuovi = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double x = vertici.get(i * 2);
            double y = vertici.get(i * 2 + 1);

            // Coordinata relativa al centro
            double dx = x - cx;
            double dy = y - cy;

            // Scala
            dx *= scala;
            dy *= scala;

            // Ruota
            double xRot = dx * Math.cos(rotazione) - dy * Math.sin(rotazione);
            double yRot = dx * Math.sin(rotazione) + dy * Math.cos(rotazione);

            // Riporta al sistema globale
            nuovi.add(cx + xRot);
            nuovi.add(cy + yRot);
        }

        return nuovi;
    }

    @Override
    public void onSliderChanged(double sliderValue) {return;}
    @Override
    public void onSliderReleased(double sliderValue){return;}
    @Override
    public void onMouseClicked(MouseEvent event) {
        // Non implementato, ma necessario per l'interfaccia
    }
}
