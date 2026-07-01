/**
 * Comando che gestisce lo spostamento di un poligono arbitrario all'interno della lavagna,
 * secondo il pattern Command.
 *
 * Il comando lavora sulla figura selezionata, ottenuta tramite FiguraSelezionataManager,
 * e permette di spostare il poligono aggiornandone le coordinate tramite il metodo spostaPoligono()
 * del LavagnaModel.
 *
 * Vengono memorizzate sia le coordinate finali dello spostamento (x1, y1), sia quelle iniziali
 * (handle_x, handle_y) per consentire l'annullamento dell'operazione tramite undo().
 *
 *
 * Autori:
 *  - Mirko
 */



package com.example.Command;

import com.example.Model.Figura;
import com.example.Model.LavagnaModel;
import com.example.Model.PoligonoArbitrario;
import com.example.State.FiguraSelezionataManager;

public class SpostamentoPoligonoCommand implements Command {

    private Figura figura;
    private double x1; //coordinata x dopo lo spostamento
    private double y1; //coordinata y dopo lo spostamento
    private double handle_x; //coordinata x prima dello spostamento
    private double handle_y; //coordinata y prima dello spostamento

    public SpostamentoPoligonoCommand(Figura figura, double x1, double y1, double handle_x, double handle_y) {
        this.figura = figura;
        this.x1 = x1;
        this.y1 = y1;
        this.handle_x = handle_x;
        this.handle_y = handle_y;
    }

    Figura figura_selezionata = FiguraSelezionataManager.getInstance().get(); // conservare per undo()

    @Override
    public void execute() {

        LavagnaModel.getInstance().spostaPoligono((PoligonoArbitrario) figura_selezionata, x1, y1);
        System.out.println("Figura " + figura.toString() + " spostata\n");
    }

    @Override
    public void undo() {

        FiguraSelezionataManager.getInstance().clear();
        LavagnaModel.getInstance().spostaPoligono((PoligonoArbitrario) figura_selezionata, handle_x, handle_y);
        System.out.println("Undo: Figura " + figura.toString() + " spostata\n");

    }
    @Override
    public boolean isUndoable() {
        return true;
    }
}
