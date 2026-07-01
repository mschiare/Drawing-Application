/*
*Questa classe si occupa dello spostamento della figura in secondo piano
*Autori: Kevin
*/

package com.example.Command;

import com.example.Model.LavagnaModel;
import com.example.Model.Figura;
import com.example.State.FiguraSelezionataManager;

public class SpostaSottoCommand implements Command {

    // Modello della lavagna che contiene tutte le figure
    final private LavagnaModel lavagnaModel;
    // Figura da spostare in fondo
    final private Figura element;

    // Costruttore che inizializza lavagna e figura da spostare
    public SpostaSottoCommand(LavagnaModel lavagnaModel, Figura element) {
        this.lavagnaModel = lavagnaModel;
        this.element = element;
    }

    @Override
    // Esegue lo spostamento della figura in fondo (dietro a tutte le altre)
    public void execute() {
        lavagnaModel.spostaSotto(element);
        System.out.println(element.toString() + " messa in fondo\n");
    }

    // Undo che riporta la figura in cima (inverte l'esecuzione)
    @Override
    public void undo() {

        FiguraSelezionataManager.getInstance().clear();
        lavagnaModel.spostaSopra(element);
    }

    // Controlla se il comando può essere eseguito:
    // non si può eseguire se la figura è già in fondo
    @Override
    public boolean canExecute() {
        int index = lavagnaModel.getFigure().indexOf(element);

        if (index == 0) {
            System.out.println("Elemento già in fondo, non può essere spostato più in basso.");
        }

        return index != 0;
    }

    //Supporta l'undo
    @Override
    public boolean isUndoable() {
        return true;
    }
}