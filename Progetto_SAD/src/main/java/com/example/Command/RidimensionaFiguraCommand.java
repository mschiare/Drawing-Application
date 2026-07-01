/**
 * Classe che implementa il comando per il ridimensionamento di una figura selezionata,
 * secondo il pattern Command, consentendo anche l'annullamento (undo) dell'operazione.
 *
 * Il comando opera sulla figura selezionata mantenuta dal FiguraSelezionataManager e
 * applica il ridimensionamento tramite il LavagnaModel. Vengono memorizzate sia le nuove
 * coordinate finali che le coordinate iniziali (handle_x, handle_y) per poter ripristinare
 * lo stato precedente durante l'operazione di undo.
 *
 * Questa classe viene istanziata nella classe RidimensionaFiguraStato.
 *
 * Autori: Maria Silvana
 */

 package com.example.Command;

import com.example.Model.Figura;
import com.example.Model.LavagnaModel;
import com.example.State.FiguraSelezionataManager;

public class RidimensionaFiguraCommand implements Command {

    double x2, y2;
    double handle_x, handle_y; // coordinate da cui inizia il ridimensionamento
    Figura figura_selezionata = FiguraSelezionataManager.getInstance().get();

    public RidimensionaFiguraCommand(double x2, double y2, double handle_x, double handle_y) {
        this.y2 = y2;
        this.x2 = x2;
        this.handle_x = handle_x;
        this.handle_y = handle_y;
    }




    public void execute() {

        LavagnaModel.getInstance().ridimensionaFigura(figura_selezionata, x2, y2);
        System.out.println("Figura " + figura_selezionata + " ridimensionata");
    }


    @Override
    public void undo() {

        FiguraSelezionataManager.getInstance().clear();
        LavagnaModel.getInstance().ridimensionaFigura(figura_selezionata, handle_x, handle_y);
        System.out.println("Figura " + figura_selezionata + " ridimensionata");

    }

    @Override
    public boolean isUndoable() {
        return true;
    }

}
