/*
* Comando che gestisce l'operazione di incolla (paste) nel modello della lavagna.
*
* Clona la figura precedentemente copiata e la aggiunge al modello. Supporta l'operazione
* di annullamento ('undo'), rimuovendo la figura incollata dalla lavagna.
*
* Autori:
*  - Michele
*
*/
package com.example.Command;

import com.example.Model.Figura;
import com.example.Model.LavagnaModel;

public class PasteCommand implements Command {

    private LavagnaModel lavagnaModel;
    private Figura figuraIncollata;

    public PasteCommand(LavagnaModel lavagnaModel) {
        this.lavagnaModel = lavagnaModel;
    }

    public void execute(){
        Figura figuraCopiata = lavagnaModel.getFiguraCopiata();
        Figura figuraClonata = figuraCopiata.getClone(); // per offset
        lavagnaModel.aggiungiFigura(figuraClonata);
        lavagnaModel.setFiguraCopiata(figuraClonata);
        figuraIncollata = figuraClonata;
        System.out.println("Paste " + figuraIncollata.toString() + "\n");
    }

    public void undo(){
        lavagnaModel.rimuoviFigura(figuraIncollata);
        System.out.println("Rimossa figura: " + figuraIncollata.toString() + " \n");
    }
    public boolean isUndoable(){
        return true;
    }


}
