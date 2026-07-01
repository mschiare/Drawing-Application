/**
 * La classe RotazioneFiguraCommand implementa il pattern Command per gestire
 * l'operazione di rotazione di una figura all'interno del modello della lavagna.
 *
 * Questa classe consente di applicare una rotazione a una figura selezionata
 * e supporta l'operazione di undo, permettendo di ripristinare l'angolo di
 * rotazione precedente.
 * Autori: Kevin
 * */

package com.example.Command;

import com.example.Model.Figura;
import com.example.Model.LavagnaModel;
import com.example.State.FiguraSelezionataManager;

public class RotazioneFiguraCommand implements Command{
    // Figura da ruotare
    private Figura element;
    // Angolo di rotazione desiderato (nuovo)
    private double rotazione;
    // Angolo di rotazione precedente, utile per l'undo
    private double vecchiaRotazione;


    // Costruttore che inizializza la figura e gli angoli
    public RotazioneFiguraCommand(Figura element, double rotazione) {

        this.element = element;
        this.rotazione = rotazione;
        vecchiaRotazione = element.getRotazione();
    }

    // Esegue la rotazione applicando il nuovo angolo alla figura
    @Override
    public void execute() {
        System.out.println("Ruoto");
        LavagnaModel.getInstance().cambiaRotazione(element, rotazione);
    }

    // Annulla la rotazione riportando la figura alla rotazione precedente
    @Override
    public void undo() {
        FiguraSelezionataManager.getInstance().clear();
        LavagnaModel.getInstance().cambiaRotazione(element, vecchiaRotazione);
        System.out.println("Ruoto alla vecchia posizione");
    }
    // Supporta undo
    @Override
    public boolean isUndoable() {
        return true;
    }
}
