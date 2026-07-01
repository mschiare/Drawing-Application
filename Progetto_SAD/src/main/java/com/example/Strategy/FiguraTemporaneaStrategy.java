/**
 * Interfaccia che definisce la strategia per la generazione e l'aggiornamento
 * delle figure temporanee durante le interazioni utente, secondo il pattern Strategy.
 *
 * Le figure temporanee vengono utilizzate per visualizzare il feedback grafico dinamico
 * durante operazioni di disegno, spostamento, ridimensionamento o rotazione, senza
 * modificare immediatamente lo stato del modello.
 *
 * Ogni tipo concreto di figura (rettangolo, ellisse, poligono, testo) implementa
 * questa interfaccia per specificare il proprio comportamento temporaneo durante le interazioni.
 *
 * Metodi:
 *  - crea(): genera la figura temporanea a partire dalle coordinate iniziali e dall'angolo di rotazione.
 *  - aggiorna(): aggiorna dinamicamente la figura temporanea in funzione delle nuove coordinate e della rotazione corrente.
 *
 * Autori:
 *  - Maria Silvana
 */


package com.example.Strategy;

import javafx.scene.Node;

public interface FiguraTemporaneaStrategy {

    Node crea(double x1, double y1,double rotazione);
    void aggiorna(Node node, double x1, double y1, double x2, double y2, double rotazione);

}
