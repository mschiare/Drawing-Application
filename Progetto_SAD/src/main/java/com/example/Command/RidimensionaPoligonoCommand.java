/**
 * Comando per ridimensionare un poligono arbitrario all'interno della lavagna.
 *
 * Questo comando implementa il pattern Command e permette di applicare una trasformazione
 * sui vertici del poligono, memorizzando lo stato precedente e quello successivo per
 * supportare l'operazione di undo.
 *
 * Quando viene eseguito (execute), aggiorna i punti del poligono con la nuova configurazione.
 * Quando viene annullato (undo), ripristina i punti originali precedenti al ridimensionamento.
 *
 * Il comando mantiene una copia profonda delle liste di punti per garantire l'integrità dello stato.
 *
 */

package com.example.Command;

import com.example.Model.LavagnaModel;
import com.example.Model.PoligonoArbitrario;

import java.util.ArrayList;
import java.util.List;

public class RidimensionaPoligonoCommand implements Command {

    private PoligonoArbitrario poligono;
    private List<Double> puntiPrima; // stato precedente
    private List<Double> puntiDopo;  // stato nuovo

    public RidimensionaPoligonoCommand(PoligonoArbitrario poligono, List<Double> vecchiPunti) {
        this.poligono = poligono;
        this.puntiPrima = new ArrayList<>(vecchiPunti); // copia profonda
        this.puntiDopo = new ArrayList<>(poligono.getPunti());           // stato da applicare
    }

    @Override
    public void execute() {
        LavagnaModel.getInstance().ridimensionaPoligono(poligono, puntiDopo);
    }

    @Override
    public void undo() {
        LavagnaModel.getInstance().ridimensionaPoligono(poligono, puntiPrima);
    }
    @Override
    public boolean isUndoable() {
        return true; // il comando è undoable
    }
}

