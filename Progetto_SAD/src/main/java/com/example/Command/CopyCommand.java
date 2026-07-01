/*
* Comando che gestisce l'operazione di copia di una figura nel modello della lavagna.
*
* Imposta la figura selezionata come figura copiata. Non supporta l'annullamento ('undo').
*
* Autori:
* - Michele
*/
package com.example.Command;

import com.example.Model.Figura;
import com.example.Model.LavagnaModel;

public class CopyCommand implements Command {

    private LavagnaModel lavagnaModel;
    private Figura figura;

    public CopyCommand(LavagnaModel lavagnaModel, Figura figura) {
        this.lavagnaModel = lavagnaModel;
        this.figura = figura;
    }

    public void execute(){
        lavagnaModel.setFiguraCopiata(figura);
        System.out.println("Copy " + figura.toString() + "\n");
    }
    public void undo(){}

    public boolean isUndoable(){
        return false;
    }


}
