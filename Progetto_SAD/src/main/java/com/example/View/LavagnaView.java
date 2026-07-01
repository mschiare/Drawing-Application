/**
 * Classe che rappresenta la View principale della lavagna grafica nel pattern MVC.
 *
 * Implementa il pattern Singleton per garantire un'unica istanza globale della View,
 * e funge da osservatore del LavagnaModel, aggiornando dinamicamente la rappresentazione
 * grafica delle figure sulla lavagna tramite il metodo aggiornaLavana().
 *
 * Autori: tutti
 *
 */


package com.example.View;

import com.example.Model.Figura;
import com.example.Model.Griglia;
import com.example.Model.LavagnaModel;
import com.example.Model.PoligonoArbitrario;
import com.example.State.*;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class LavagnaView implements Runnable{


    private static LavagnaView instance;
    private AnchorPane lavagna;
    private Group figureZoomabili = new Group();
    private Circle handle;
    private Circle handle_1;
    Node griglia = new Group();


    public static LavagnaView getInstance(){
        return instance;
    }

    public static LavagnaView getInstance(AnchorPane lavagna) {
        if (instance == null) {
            instance = new LavagnaView(lavagna);
        }
        return instance;
    }

    //vedere se serve
    public AnchorPane getLavagna() {
        return lavagna;
    }

    public LavagnaView(AnchorPane lavagna){
        this.lavagna = lavagna;
        lavagna.getChildren().add(figureZoomabili); // Aggiungilo una volta sola
        LavagnaModel.getInstance().aggiungiOsservatore(this);
    }

    public void aggiungiGriglia(Griglia griglia) {
        this.griglia = griglia.creaNodoJavaFX();
        aggiornaLavagna();
    }

    public void rimuoviGriglia() {
        this.griglia = new Griglia(5,5,lavagna.getWidth(),lavagna.getHeight(),Color.TRANSPARENT).creaNodoJavaFX();
        aggiornaLavagna();
    }

    public Group getFigureZoomabili() {
        return figureZoomabili;
    }


    private void aggiungiFiguraZoomabile(Node nodo){
        figureZoomabili.getChildren().add(nodo);
    }


    public void aggiornaLavagna(){

        handle = null;
        handle_1 = null;

        figureZoomabili.getChildren().clear();

        if (this.griglia != null)
            aggiungiFiguraZoomabile(griglia);


        for (Figura f : LavagnaModel.getInstance().getFigure()) {


            Node nodo = f.creaNodoJavaFX();
            aggiungiFiguraZoomabile(nodo);

            f.getNodo().setOnMouseClicked(event -> {
                /*
                 * boolean isInZoomStato viene inserita per evitare che, una volta selezionato zoom_in button
                 * o zoom_out button, cliccando in corrispondenza di una figura, questa non viene selezionata
                 *  perchè ci si trova nello stato di zoom
                 * */
                boolean isInZoomStato = (StatoManager.getInstance().getStato() instanceof ZoomInStato) || (StatoManager.getInstance().getStato() instanceof ZoomOutStato);
                if (!isInZoomStato && !(StatoManager.getInstance().getStato() instanceof DisegnaPoligonoArbitrarioStato)) {
                    FiguraSelezionataManager.getInstance().set(f);
                    System.out.println("Figura selezionata: " + f.toString() + "\n");
                    StatoManager.getInstance().setStato(new SelezionaFiguraStato());
                }
            });

        }

        // gestione figura selezionata, handle e toFront()
        Figura f = FiguraSelezionataManager.getInstance().get();
        if (f != null) {

                double hx = f.getX2();
                double hy = f.getY2();

                handle = new Circle(hx, hy, 5, Color.BROWN);
                handle.setCursor(Cursor.SE_RESIZE);
                //handle.toFront();

                aggiungiFiguraZoomabile(handle);

                handle.setOnMousePressed(event -> {
                    if(FiguraSelezionataManager.getInstance().get().getClass() == PoligonoArbitrario.class)
                        StatoManager.getInstance().setStato(new RidimensionaPoligonoStato());
                    else
                        StatoManager.getInstance().setStato(new RidimensionaFiguraStato());
                    System.out.println("Inizio a ridimensionare");
                });

                // gestione handle spostamento
                double hx_1 = f.getX1();
                double hy_1 = f.getY1();


                handle_1 = new Circle(hx_1, hy_1, 5, Color.GRAY);
                handle_1.setCursor(Cursor.MOVE);
                //handle_1.toFront();

                aggiungiFiguraZoomabile(handle_1);

                handle_1.setOnMousePressed(event -> {
                    if(FiguraSelezionataManager.getInstance().get().getClass() == PoligonoArbitrario.class)
                        StatoManager.getInstance().setStato(new SpostamentoPoligonoStato());
                    else
                        StatoManager.getInstance().setStato(new SpostamentoFiguraStato());
                    System.out.println("Inizio a spostare");
                });


        }
    }

    @Override
    public void run() {
        aggiornaLavagna();
    }


}
