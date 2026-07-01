/**
 * Stato dell'applicazione dedicato allo spostamento di un PoligonoArbitrario.
 *
 * Questo stato si attiva quando l'utente seleziona e trascina un poligono arbitrario
 * sulla lavagna. Diversamente dalle figure geometriche classiche rettangoli, ellissi, segmenti,
 * che vengono manipolate usando due coordinate principali (x1, y1, x2, y2), il poligono arbitrario
 * viene gestito tramite una lista di vertici List<Double> che definisce la sua forma.
 *
 * Funzionalità principali:
 * - Durante onMousePressed, salva i vertici iniziali del poligono e crea una versione
 *   temporanea della figura per fornire feedback visivo durante lo spostamento.
 * - Durante onMouseDragged, aggiorna dinamicamente la posizione della figura temporanea,
 *   traslando ciascun punto della lista.
 * - Durante onMouseReleased, rimuove la figura temporanea e registra un comando
 *   SpostamentoFiguraCommand per aggiornare la posizione reale della figura,
 *   permettendo anche il supporto all'undo.
 *
 * Questo stato rappresenta un'estensione del comportamento base di spostamento,
 * adattato alla gestione più complessa dei poligoni arbitrari che non possono essere
 * descritti o spostati semplicemente con due coordinate.
 */

package com.example.State;

import com.example.Command.Command;
import com.example.Command.Invoker;
import com.example.Command.SpostamentoFiguraCommand;
import com.example.Command.SpostamentoPoligonoCommand;
import com.example.Model.Figura;
import com.example.Model.LavagnaModel;
import com.example.Model.PoligonoArbitrario;
import com.example.Strategy.FiguraTemporaneaStrategy;
import com.example.Strategy.PoligonoArbitrarioStrategy;
import com.example.View.LavagnaView;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class SpostamentoPoligonoStato implements Stato {
    double handle_x_iniziale, handle_y_iniziale;
    List<Double> punti_iniziali = new ArrayList<>();

    Figura figura = FiguraSelezionataManager.getInstance().get();
    FiguraSelezionataManager figuraSelezionataManager = FiguraSelezionataManager.getInstance();
    FiguraTemporaneaStrategy figuraTemporaneaStrategy = null;
    Node figuraTemporanea = null;


    @Override
    public void onMousePressed(MouseEvent event){
        punti_iniziali=((PoligonoArbitrario)figura).getPunti();

        // Memorizzo dove ho cliccato
        Point2D punto = LavagnaView.getInstance().getFigureZoomabili().sceneToLocal(event.getSceneX(), event.getSceneY());
        handle_x_iniziale = punto.getX();
        handle_y_iniziale = punto.getY();

        figuraTemporaneaStrategy = figuraSelezionataManager.get().getTemporaryRenderStrategy();


        figuraTemporanea = ((PoligonoArbitrarioStrategy)figuraTemporaneaStrategy).creaPoligono(punti_iniziali, figura.getRotazione());


        //Aggiungo la figura temporanea alla lavagna
        LavagnaView.getInstance().getFigureZoomabili().getChildren().add(figuraTemporanea);

    }

    @Override
    public void onMouseDragged(MouseEvent event){

        // Calcolo dello spostamento rispetto al punto iniziale
        Point2D punto = LavagnaView.getInstance().getFigureZoomabili().sceneToLocal(event.getSceneX(), event.getSceneY());
        double deltaX = punto.getX() - handle_x_iniziale;
        double deltaY = punto.getY() - handle_y_iniziale;


        List<Double> nuoviPunti = new ArrayList<>(punti_iniziali);
        for (int i = 0; i < punti_iniziali.size(); i++) {
            // Aggiorno le coordinate dei punti del poligono
            nuoviPunti.set(i, punti_iniziali.get(i) + (i % 2 == 0 ? deltaX : deltaY));
        }
        ((PoligonoArbitrarioStrategy)figuraTemporaneaStrategy).aggiornaPoligono(figuraTemporanea, nuoviPunti, figura.getRotazione());
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

        Command cmd = new SpostamentoPoligonoCommand(figuraSelezionataManager.get(),x2,y2,handle_x_iniziale,handle_y_iniziale);
        Invoker.getInstance().executeCommand(cmd);
        StatoManager.getInstance().setStato(new SelezionaFiguraStato());


    }
    @Override
    public void onSliderChanged(double sliderValue) {return;}
    @Override
    public void onSliderReleased(double sliderValue){return;}
    @Override
    public void onMouseClicked(MouseEvent event) {
        // Non gestiamo il click in questo stato
    }
}
