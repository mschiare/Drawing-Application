/*
* Comando che inserisce una griglia sulla lavagna.
*
* Crea una griglia con le dimensioni e colori specificati e la aggiunge alla lavagna.
* Non supporta l'operazione di annullamento ('undo').
*
* Autori:
*  - Michele
*/

package com.example.Command;

import com.example.Factory.FiguraFactory;
import com.example.Model.Figura;
import com.example.Model.Griglia;
import com.example.Model.LavagnaModel;
import com.example.View.LavagnaView;
import javafx.scene.paint.Color;

import java.util.List;

public class AggiungiGrigliaCommand implements Command {

    private double x2, y2;
    private int nRighe, nColonne;
    private Color strokeColor;
    private Figura griglia;

    public AggiungiGrigliaCommand(int nRighe, int nColonne, double x2, double y2, Color strokeColor) {
        this.nRighe = nRighe;
        this.nColonne = nColonne;
        this.x2 = x2; // larghezza lavagna
        this.y2 = y2; // altezza lavagna
        this.strokeColor= strokeColor;

    }

    public void execute() {
        Griglia griglia = new Griglia(nRighe, nColonne, x2 , y2, strokeColor);
        LavagnaView.getInstance().aggiungiGriglia(griglia);
        System.out.println("Aggiunta griglia\n");
    }
    @Override
    public void undo() {
        return;
    }
    @Override
    public boolean isUndoable() {
        return false;
    }
}
