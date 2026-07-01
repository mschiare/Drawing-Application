/*
Classe che si occupa di cambiare il colore del bordo alla figura
*Autori: Kevin
*/

package com.example.Command;

import com.example.Model.Figura;
import com.example.Model.LavagnaModel;
import com.example.State.FiguraSelezionataManager;
import javafx.scene.paint.Color;

public class CambiaColoreBordoCommand implements Command {
    // Modello della lavagna su cui si opera
    final private LavagnaModel lavagnaModel;
    // Figura su cui cambiare il colore del bordo
    private Figura element;
    // Nuovo colore da applicare al bordo
    private final Color nuovoColore;
    // Colore precedente del bordo, per poter fare undo
    private Color vecchioColore;

    // Costruttore: inizializza modello, figura, nuovo colore e salva il colore attuale del bordo
    public CambiaColoreBordoCommand(LavagnaModel lavagnaModel, Figura element, Color colore) {
        this.lavagnaModel = lavagnaModel;
        this.element = element;
        this.nuovoColore = colore;
        this.vecchioColore = element.getStrokeColor();
    }

    // Cambia il colore del bordo della figura
    @Override
    public void execute() {
        lavagnaModel.cambiaColoreBordo(element, nuovoColore);
    }

    // Undo: ripristina il colore del bordo precedente
    @Override
    public void undo() {
        FiguraSelezionataManager.getInstance().clear();
        lavagnaModel.cambiaColoreBordo(element, vecchioColore);
        System.out.println("Coloro il bordo di " + element.toString() + " con vecchio colore: " + vecchioColore + "\n");
    }

    // Supporta undo
    @Override
    public boolean isUndoable() {
        return true;
    }
}
