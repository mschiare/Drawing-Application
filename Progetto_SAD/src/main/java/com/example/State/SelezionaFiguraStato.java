/**
 * Stato che gestisce la selezione e deselezione delle figure presenti sulla lavagna,
 * secondo il pattern State.
 *
 * Durante l'interazione:
 *  - onMousePressed(): verifica quale figura è stata cliccata.
 *    Se la figura è già selezionata, viene deselezionata; altrimenti viene impostata come figura selezionata corrente.
 *    Se il click avviene su un'area vuota della lavagna (non su una figura o sugli handle), viene deselezionata l'eventuale figura selezionata.
 *
 * La figura selezionata viene gestita centralmente tramite il FiguraSelezionataManager (pattern Singleton),
 * mentre il LavagnaModel viene notificato per aggiornare la view in seguito alle modifiche di selezione.
 *
 * Lo Stato SelezionaFiguraStato non gestisce operazioni di trascinamento o rilascio del mouse,
 * e lascia vuoti i metodi onMouseDragged() e onMouseReleased().
 *
 * Da questo stato  l'utente può accedere agli altri stati operativi come spostamento o ridimensionamento.
 *
 * Autori:
 *  - Maria Silvana
 *
 */


package com.example.State;

import com.example.Model.Figura;
import com.example.Model.LavagnaModel;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;


public class SelezionaFiguraStato implements Stato {

    FiguraSelezionataManager figuraSelezionataManager = FiguraSelezionataManager.getInstance();

    public SelezionaFiguraStato() {
        LavagnaModel.getInstance().notificaOsservatori();
    }

    @Override
    public void onMousePressed(MouseEvent event){
        Object source = event.getTarget();

        if (source instanceof Node node && node.getUserData() instanceof Figura figura) {

            // se la figura è già selezionata viene deselezionata
            if (figuraSelezionataManager.get() == figura) {
                figuraSelezionataManager.clear();
                LavagnaModel.getInstance().deselezionaFigura(figura);
                System.out.println("Figura deselezionata: " + figura.toString() + "\n");
            } else {

            // seleziono la figura su cui ho cliccato
                figuraSelezionataManager.set(figura);
                LavagnaModel.getInstance().selezionaFigura(figura);
                System.out.println("Figura selezionata: " + figura.toString() + "\n");
            }

        } else if(!(source instanceof Circle circle))  {
            // click sulla lavagna → deseleziona
            Figura figuraSelezionata = figuraSelezionataManager.get();
            if (figuraSelezionata != null) {
                System.out.println("Figura deselezionata: " + figuraSelezionata.toString() + "\n");
                figuraSelezionataManager.clear();
                LavagnaModel.getInstance().deselezionaFigura(figuraSelezionata);

            }
        }
    }

    @Override
    public void onMouseDragged(MouseEvent event){
        return;
    }

    @Override
    public void onMouseReleased(MouseEvent event){
        return;
    }

    @Override
    public void onSliderChanged(double sliderValue) {return;}
    @Override
    public void onSliderReleased(double sliderValue){return;}

    @Override
    public void onMouseClicked(MouseEvent event){return;}


}