/*
* Comando che gestisce l'operazione di taglia (cut) nel modello della lavagna.
*
* Rimuove la figura selezionata dalla lavagna e la imposta come figura copiata.
* Non supporta l'operazione di annullamento ('undo').
*
* Autori:
* - Michele
*/
package com.example.Command;

import com.example.Model.Figura;
import com.example.Model.LavagnaModel;
import com.example.State.FiguraSelezionataManager;

public class CutCommand implements Command{

    private LavagnaModel lavagnaModel;
    private Figura figura;

    public CutCommand(LavagnaModel lavagnaModel, Figura figura) {
        this.lavagnaModel = lavagnaModel;
        this.figura = figura;
    }

    public void execute(){
        FiguraSelezionataManager.getInstance().clear();
        lavagnaModel.setFiguraCopiata(figura);
        lavagnaModel.rimuoviFigura(figura);
        System.out.println("Cut " + figura.toString() + "\n");
    }
    public void undo(){}

    public boolean isUndoable(){
        return false;
    }
}
