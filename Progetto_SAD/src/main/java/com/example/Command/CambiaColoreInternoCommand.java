/*
Classe che si occupa di cambiare il colore dell'interno della figura
*Autori: Kevin
*/

package com.example.Command;

import com.example.Model.Figura;
import com.example.Model.LavagnaModel;
import com.example.State.FiguraSelezionataManager;
import javafx.scene.paint.Color;

public class CambiaColoreInternoCommand implements Command {

    // Modello della lavagna che gestisce le figure
    final private LavagnaModel lavagnaModel;
    // Figura su cui cambiare il colore interno (fill)
    private Figura element;
    // Nuovo colore da applicare all'interno della figura
    private final Color nuovoColore;
    // Colore interno precedente per poter fare undo
    private Color vecchioColore;

    // Costruttore
    public CambiaColoreInternoCommand(LavagnaModel lavagnaModel, Figura element, Color colore) {
        this.lavagnaModel = lavagnaModel;
        this.element = element;
        this.nuovoColore = colore;
        this.vecchioColore = element.getFillColor();
    }

    // Esegue il cambio di colore interno
    @Override
    public void execute() {
        lavagnaModel.cambiaColoreInterno(element, nuovoColore);
    }

    // Undo: ripristina il colore interno precedente
    @Override
    public void undo() {
        // Pulisce la selezione corrente per evitare effetti indesiderati
        FiguraSelezionataManager.getInstance().clear();
        lavagnaModel.cambiaColoreInterno(element, vecchioColore);
        System.out.println("Coloro l'interno di " + element.toString() + " con vecchio colore: " + vecchioColore + "\n");
    }
    // Supporta l'undo
    @Override
    public boolean isUndoable() {
        return true;
    }
}
