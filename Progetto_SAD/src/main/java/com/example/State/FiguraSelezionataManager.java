/**
 * Classe che gestisce la figura attualmente selezionata sulla lavagna.
 *
 * Implementa il pattern Singleton per garantire la presenza di un'unica istanza che mantiene il riferimento alla figura selezionata corrente.
 * In questo modo tutti i componenti del sistema (State, Command, Controller) possono accedere e modificare
 * lo stato di selezione.
 *
 * Metodi principali:
 *  - get(): restituisce la figura attualmente selezionata.
 *  - set(): imposta la nuova figura selezionata.
 *  - clear(): annulla la selezione corrente.
 *
 * Autori:
 *  - Maria Silvana (implementazione del manager di selezione con pattern Singleton)
*/



package com.example.State;

import com.example.Model.Figura;

public class FiguraSelezionataManager {
    private static FiguraSelezionataManager instance;

    private Figura figuraSelezionata;

    private FiguraSelezionataManager() {
        // costruttore privato per Singleton
    }

    public static FiguraSelezionataManager getInstance() {
        if (instance == null) {
            instance = new FiguraSelezionataManager();
        }
        return instance;
    }

    public Figura get() {
        return figuraSelezionata;
    }

    public void set(Figura figura) {
        this.figuraSelezionata = figura;
    }

    public void clear() {
        this.figuraSelezionata = null;
    }



}
