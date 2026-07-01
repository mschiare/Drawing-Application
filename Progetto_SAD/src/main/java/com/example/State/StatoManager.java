/**
 * Classe che gestisce il cambio e il mantenimento dello stato corrente dell'applicazione,
 * secondo il pattern State.
 *
 * Implementa il pattern Singleton per garantire la presenza di un'unica istanza globale
 * che coordina lo stato operativo dell'applicazione (Idle, Disegna, Sposta, Ridimensiona, Rotazione).
 *
 * Lo stato corrente viene mantenuto all'interno dell'attributo statoCorrente, inizializzato di default su IdleStato.
 *
 * Metodi principali:
 *  - setStato(): aggiorna lo stato corrente con un nuovo stato operativo.
 *  - getStato(): restituisce lo stato corrente attivo.
 *
 * Autori:
 *  - Maria Silvana (implementazione del manager degli stati con pattern Singleton e State)
*/

package com.example.State;

public class StatoManager {

    private static StatoManager instance;

    private Stato statoCorrente = new IdleStato();

    private StatoManager() {}

    public static StatoManager getInstance() {
        if (instance == null) {
            instance = new StatoManager();
        }
        return instance;
    }

    public void setStato(Stato nuovoStato) {
        this.statoCorrente = nuovoStato;
    }

    public Stato getStato() {
        return statoCorrente;
    }

}
