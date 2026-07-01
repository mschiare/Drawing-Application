/**
 * Classe che implementa il comando per lo spostamento di una figura sulla lavagna,
 * secondo il pattern Command, consentendo l'annullamento (undo) dell'operazione.
 *
 * Il comando riceve la figura da spostare e memorizza sia le nuove coordinate target (x1, y1),
 * sia le coordinate iniziali (handle_x, handle_y), necessarie per ripristinare la posizione originale
 in caso di annullamento.
 *
 * L'operazione di spostamento viene eseguita tramite il LavagnaModel, che aggiorna la posizione della figura.
 * La logica di selezione della figura corrente viene gestita separatamente tramite il FiguraSelezionataManager.
 *
 * Questa classe viene istanziata in SpostamentoFiguraStato
 *
 * Autore:
 * - Maria Silvana
*/

package com.example.Command;

import com.example.Model.Figura;
import com.example.Model.LavagnaModel;
import com.example.State.FiguraSelezionataManager;


public class SpostamentoFiguraCommand implements Command {

    private Figura figura;
    private double x1; //coordinata x dopo lo spostamento
    private double y1; //coordinata y dopo lo spostamento
    private double handle_x; //coordinata x prima dello spostamento
    private double handle_y; //coordinata y prima dello spostamento

    public SpostamentoFiguraCommand(Figura figura, double x1, double y1, double handle_x, double handle_y) {
        this.figura = figura;
        this.x1 = x1;
        this.y1 = y1;
        this.handle_x = handle_x;
        this.handle_y = handle_y;
    }

    Figura figura_selezionata = FiguraSelezionataManager.getInstance().get();

    @Override
    public void execute() {

        LavagnaModel.getInstance().spostaFigura(figura, x1, y1);
        System.out.println("Figura " + figura.toString() + " spostata\n");

    }

    @Override
    public void undo() {

        FiguraSelezionataManager.getInstance().clear();

        LavagnaModel.getInstance().spostaFigura(figura_selezionata, handle_x, handle_y);
        System.out.println("Undo: Figura " + figura.toString() + " spostata\n");

    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}
