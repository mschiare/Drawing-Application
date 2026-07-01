/**
 * Interfaccia che definisce i metodi per gli stati operativi della lavagna, secondo il pattern State.
 *
 * Gli stati rappresentano i diversi comportamenti che l'applicazione assume durante le interazioni utente
 * (ad esempio: disegno, selezione, spostamento, ridimensionamento, rotazione, ecc.).
 *
 * I primi tre metodi (onMousePressed, onMouseDragged, onMouseReleased) gestiscono le interazioni tramite mouse.
 * I metodi onSliderChanged e onSliderReleased gestiscono le variazioni di angolo (es. per la rotazione)
 * durante l'interazione con uno slider.
 *
 * Autori:
 *  - Maria Silvana (definizione interfaccia, gestione interazioni mouse)
 * -  Kevin (estensione per gestione slider)
*/


 package com.example.State;

import javafx.scene.input.MouseEvent;

public interface Stato {

    // Maria Silvana
    void onMousePressed(MouseEvent event);
    void onMouseDragged(MouseEvent event);
    void onMouseReleased(MouseEvent event);
    void onMouseClicked(MouseEvent event);

    // Kevin
    void onSliderChanged(double nuovoAngolo);
    void onSliderReleased(double angoloFinale);

}
