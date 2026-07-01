/**
 * Classe che rappresenta il modello centrale della lavagna grafica secondo il pattern MVC.
 *
 * LavagnaModel mantiene lo stato completo della lavagna, gestendo l'elenco di tutte le figure
 * presenti (rettangoli, ellissi, testi, poligoni e segmenti) e coordinando tutte le operazioni di
 * modifica delle figure: aggiunta, rimozione, spostamento, ridimensionamento, rotazione.
 *
 * Viene implementato come Singleton per garantire un'unica istanza condivisa in tutto il sistema.
 *
 * Il modello è osservabile: mantiene una lista di osservatori (Runnable) che vengono notificati
 * tramite il metodo notificaOsservatori() ogni volta che lo stato della lavagna viene modificato.
 * In questo modo la View (LavagnaView) può aggiornare automaticamente la rappresentazione grafica
 * in seguito a modifiche del modello.
 *
 * Autori: tutti
 *
 */



package com.example.Model;


import com.example.State.FiguraSelezionataManager;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.ArrayList;

public class LavagnaModel {

    private static LavagnaModel instance;
    private List<Figura> figure = new ArrayList<>();
    private List<Runnable> osservatori = new ArrayList<>();
    private Figura figuraCopiata; // Aggiunto da: Michele


    public static LavagnaModel getInstance(){
        if(instance == null){
            instance = new LavagnaModel();
        }
        return instance;
    }

    // Maria Silvana
    public void selezionaFigura(Figura figura){
        notificaOsservatori();
    }

    // Maria Silvana
    public void deselezionaFigura(Figura figura){
        notificaOsservatori();
    }

    //Aggiunto da: Mirko
    public void rimuoviFigura(Figura figura){
        figure.remove(figura);
        notificaOsservatori();
    }

    // Michele
    public void setFiguraCopiata(Figura figura){
        figuraCopiata = figura;
    }

    // Michele
    public Figura getFiguraCopiata(){
        return figuraCopiata;
    }

    // Maria Silvana
    public void ridimensionaFigura(Figura figura, double x2, double y2){

        figure.get(figure.indexOf(figura)).setX2(x2);
        figure.get(figure.indexOf(figura)).setY2(y2);

        notificaOsservatori();
    }

    //Aggiunto da: Mirko
    public void ridimensionaPoligono(PoligonoArbitrario figura, List<Double> nuoviPunti) {
        figura.getPunti().clear();
        figura.getPunti().addAll(nuoviPunti);
        notificaOsservatori();
    }

    //Aggiunto da: Mirko
    public void spostaPoligono(PoligonoArbitrario poligono, double x1, double y1){
        double x2_diff = figure.get(figure.indexOf(poligono)).getX2() - figure.get(figure.indexOf(poligono)).getX1();
        double y2_diff = figure.get(figure.indexOf(poligono)).getY2() - figure.get(figure.indexOf(poligono)).getY1();

        List<Double> punti = poligono.getPunti();
        double x_diff = x1 - punti.get(0);
        double y_diff = y1 - punti.get(1);
        for (int i = 0; i < punti.size(); i += 2) {
            punti.set(i, punti.get(i) + x_diff);
            punti.set(i + 1, punti.get(i + 1) + y_diff);
        }
        notificaOsservatori();
    }

    // Maria Silvana
    public void spostaFigura(Figura figura, double x1, double y1){
        double x2_diff = figure.get(figure.indexOf(figura)).getX2() - figure.get(figure.indexOf(figura)).getX1();
        double y2_diff = figure.get(figure.indexOf(figura)).getY2() - figure.get(figure.indexOf(figura)).getY1();

        figure.get(figure.indexOf(figura)).setX1(x1);
        figure.get(figure.indexOf(figura)).setY1(y1);
        figure.get(figure.indexOf(figura)).setX2(x1+x2_diff);
        figure.get(figure.indexOf(figura)).setY2(y1+y2_diff);

        notificaOsservatori();


    }

    //Cambia Colore bordo della figura. Aggiunto da Kevin
    public void cambiaColoreBordo(Figura figura, Color colore){
        int index = figure.indexOf(figura);
        figure.get(index).setStrokeColor(colore);
        notificaOsservatori();

    }
    //Cambia colore interno della figura. Aggiunto da Kevin
    public void cambiaColoreInterno(Figura figura, Color colore){
        int index = figure.indexOf(figura);
        figure.get(index).setFillColor(colore);
        notificaOsservatori();
    }

    // Maria Silvana
    public void aggiungiFigura(Figura figura){
        figure.add(figura);
        notificaOsservatori();
    }


    public List<Figura> getFigure() {
        return figure;
    }

    public void aggiungiOsservatore(Runnable osservatore){
        osservatori.add(osservatore);
    }

    public void notificaOsservatori(){
        for (Runnable r : osservatori){
            r.run();
        }
    }

    // Mirko
    public void svuotaLavagna() {
        figure.clear();
        FiguraSelezionataManager.getInstance().clear();
        notificaOsservatori();
    }

    // Mirko
    public void caricaFigure(List <Figura> tempList){
        figure.addAll(tempList);
        notificaOsservatori();
    }

    //Aggiunto da Kevin
    public void spostaSopra(Figura figura){
        // Rimuove la figura dalla sua posizione attuale
        figure.remove(figura);
        // la aggiunge in cima
        figure.add(figura);
        notificaOsservatori();
    }

    //Aggiunto da Kevin
    public void spostaSotto(Figura figura){
        // Rimuove la figura dalla sua posizione attuale
        figure.remove(figura);
        // Inserisce la figura in fondo
        figure.add(0, figura);
        // Notifica gli osservatori che il modello è cambiato
        notificaOsservatori();
    }

    //Aggiunto da Kevin
    public void cambiaRotazione(Figura figura, double rotazione){
        // Imposta una nuova rotazione sulla figura indicata
        figure.get(figure.indexOf(figura)).setRotazione(rotazione);
        // Notifica gli osservatori della modifica
        notificaOsservatori();

    }

    public void setFont(int font){
       Figura figura = FiguraSelezionataManager.getInstance().get();
       figura.fontSize = font;
       notificaOsservatori();
    }

}
