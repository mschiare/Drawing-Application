/*
*Questa classe si occupa dello spostamento della figura in primo piano
* Autori: Kevin
*/
package com.example.Command;

import com.example.Model.LavagnaModel;
import com.example.Model.Figura;
import com.example.State.FiguraSelezionataManager;

public class SpostaSopraCommand implements Command{

    // Modello che contiene le figure (la "lavagna")
    final private LavagnaModel lavagnaModel;
    // Figura da spostare in primo piano
    final private Figura element;


    // Costruttore che riceve la lavagna e la figura da spostare
    public SpostaSopraCommand(LavagnaModel lavagnaModel, Figura element) {
        this.lavagnaModel = lavagnaModel;
        this.element = element;
    }

    // Esegue lo spostamento della figura verso l'alto
    @Override
    public void execute() {
        lavagnaModel.spostaSopra(element);
        System.out.println(element.toString() + " messa in primo piano\n");
    }

    // Annulla l'operazione spostando la figura in basso (undo)
    @Override
    public void undo() {
        FiguraSelezionataManager.getInstance().clear();
        lavagnaModel.spostaSotto(element);
    }
    // Controlla se il comando può essere eseguito
    // Non si può eseguire se la figura è già in cima alla lista
    @Override
    public boolean canExecute() {
        int index = lavagnaModel.getFigure().indexOf(element);

        if (index == lavagnaModel.getFigure().size() - 1) {
            System.out.println("Elemento già in cima, non può essere spostato più in alto.");
        }

        return index < lavagnaModel.getFigure().size() - 1;
    }

    //Supporta l'undo
    @Override
    public boolean isUndoable() {
        return true;
    }
}