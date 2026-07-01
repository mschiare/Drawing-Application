/**
 * Stato inattivo (Idle) per il pattern State.
 *
 * Rappresenta la condizione in cui il sistema non sta effettuando alcuna operazione attiva
 * (disegno, spostamento, ridimensionamento, rotazione, ecc.).
 *
 * Tutti i metodi di gestione degli eventi sono implementati come vuoti, in quanto in questa modalità
 * non viene gestita alcuna interazione utente.
 *
 * Autori:
 *  - Maria Silvana (implementazione stato Idle)
 *
 */



package com.example.State;

import javafx.scene.input.MouseEvent;

public class IdleStato implements Stato {

    @Override
    public void onMousePressed(MouseEvent event){

    }

    @Override
    public void onMouseDragged(MouseEvent event){

    }

    @Override
    public void onMouseReleased(MouseEvent event){

    }
    @Override
    public void onSliderChanged(double sliderValue) {return;}
    @Override
    public void onSliderReleased(double sliderValue){return;}
    @Override
    public void onMouseClicked(MouseEvent event){
        return;
    }

}
