package com.example.progetto_sad_gruppo14_ah;

import com.example.Command.*;
import com.example.Model.Figura;
import com.example.Factory.*;
import com.example.Model.LavagnaModel;
import com.example.Model.PoligonoArbitrario;
import com.example.View.LavagnaView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import com.example.State.*;
import javafx.scene.paint.Color;


public class HelloController{

    @FXML
    private AnchorPane lavagna;
    @FXML
    private TextField nRighe;
    @FXML
    private TextField nColonne;
    @FXML
    private ToggleButton zoom_in;
    @FXML
    private ToggleButton zoom_out;
    @FXML
    private Button resetZoomButton;
    @FXML
    private ToggleButton segmentoButton;
    @FXML
    private ToggleButton ellisseButton;
    @FXML
    private ToggleButton rettangoloButton;
    @FXML
    private ToggleButton testoButton;
    @FXML
    private ToggleButton grigliaButton;
    @FXML
    private ToggleButton poligonoButton;
    @FXML
    private MenuItem salvaConNome;
    @FXML
    private MenuItem caricaFile;
    @FXML
    private Button spostaSopraButton;
    @FXML
    private Button spostaSottoButton;
    @FXML
    private Button undoButton;
    @FXML
    private MenuItem Elimina;
    @FXML
    private MenuItem cut;
    @FXML
    private MenuItem copy;
    @FXML
    private MenuItem paste;

    @FXML
    private ComboBox<Integer> fontSizeComboBox;

    @FXML
    private Slider sliderRotazione;

    @FXML
    private ColorPicker strokeColorPicker;
    @FXML
    private ColorPicker fillColorPicker;


    private LavagnaModel lavagnaModel;
    private LavagnaView lavagnaView;

    private FiguraSelezionataManager figuraSelezionataManager = FiguraSelezionataManager.getInstance();
    private StatoManager statoManager = StatoManager.getInstance();




    @FXML
    private void initialize() {

        lavagnaModel = LavagnaModel.getInstance();
        lavagnaView = LavagnaView.getInstance(lavagna);

        // Aggiunto da: Kevin
        sliderRotazione.setMin(0);
        sliderRotazione.setMax(360);


        // inizializzazione fontSizeComboBox
        ObservableList<Integer> fontSizes = FXCollections.observableArrayList(
                8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 40, 48, 56, 64, 72
        );

        // Aggiunto da: Maria Silvana
        fontSizeComboBox.setItems(fontSizes);
        fontSizeComboBox.setValue(12);

        // Aggiunto da: Maria Silvana
        fontSizeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            lavagnaModel.setFont(newValue);
        });


        // (cut, copy, paste).setAccelerator(...)
        // Aggiunto da: Michele
        cut.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        copy.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        paste.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));

        Elimina.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));

        // Aggiunto da: Michele
        lavagna.heightProperty().addListener((observable, oldValue, newValue) -> {
            Command cmd = new AggiungiGrigliaCommand(Integer.parseInt(nRighe.getText()), Integer.parseInt(nColonne.getText()), lavagna.getWidth(), (double) newValue, Color.LIGHTGRAY);
            if(grigliaButton.isSelected()){
                Invoker.getInstance().executeCommand(cmd);
            }
            else {
                cmd = new RimuoviGrigliaCommand();
                Invoker.getInstance().executeCommand(cmd);
            }

        });

        // Aggiunto da: Michele
        lavagna.widthProperty().addListener((observable, oldValue, newValue) -> {
            Command cmd = new AggiungiGrigliaCommand(Integer.parseInt(nRighe.getText()), Integer.parseInt(nColonne.getText()), (double) newValue, lavagna.getHeight(), Color.LIGHTGRAY);
            if(grigliaButton.isSelected()){
                Invoker.getInstance().executeCommand(cmd);
            }
            else {
                cmd = new RimuoviGrigliaCommand();
                Invoker.getInstance().executeCommand(cmd);
            }
        });

        // Aggiunto da: Michele
        nRighe.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int nRighe = Integer.parseInt(newValue);
                Command cmd = new AggiungiGrigliaCommand( nRighe, Integer.parseInt(nColonne.getText()), lavagna.getWidth(), lavagna.getHeight(), Color.LIGHTGRAY);
                if(grigliaButton.isSelected()){
                    Invoker.getInstance().executeCommand(cmd);}
            } catch (NumberFormatException e) {
                System.out.println("Valore non numerico");
            }
        });

        // Aggiunto da: Michele
        nColonne.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int nColonne = Integer.parseInt(newValue);
                Command cmd = new AggiungiGrigliaCommand(Integer.parseInt(nRighe.getText()), nColonne, lavagna.getWidth(), lavagna.getHeight(), Color.LIGHTGRAY);
                if(grigliaButton.isSelected()){
                    Invoker.getInstance().executeCommand(cmd);}
            } catch (NumberFormatException e) {
                System.out.println("Valore non numerico");
            }
        });

        // Aggiunto da: Michele
        zoom_in.setOnAction((e) -> {
            if (zoom_in.isSelected())
                statoManager.setStato(new ZoomInStato());
            else
                statoManager.setStato(new IdleStato());
        });

        // Aggiunto da: Michele
        zoom_out.setOnAction((e) -> {
            if (zoom_out.isSelected())
                statoManager.setStato(new ZoomOutStato());
            else
                statoManager.setStato(new IdleStato());
        });

        // Aggiunto da: Michele
        resetZoomButton.setOnAction((event) -> {
            Invoker.getInstance().executeCommand(new ResetZoomCommand(lavagnaView));
        });

        // Aggiunto da: Maria Silvana
        rettangoloButton.setOnAction(e -> {
            if (rettangoloButton.isSelected()) {
                statoManager.setStato(new DisegnaFiguraStato(new RettangoloFactory(), lavagna, lavagnaModel, strokeColorPicker, fillColorPicker,fontSizeComboBox));
                System.out.println("Selezionato rettangolo button\n");
            } else {
                statoManager.setStato(new IdleStato());
                System.out.println("Deselezionato rettangolo button\n");
            }
            figuraSelezionataManager.clear();
            LavagnaModel.getInstance().deselezionaFigura(figuraSelezionataManager.get());
        });

        // Aggiunto da: Maria Silvana
        segmentoButton.setOnAction(e -> {
            if (segmentoButton.isSelected()) {
                statoManager.setStato(new DisegnaFiguraStato(new SegmentoFactory(),lavagna, lavagnaModel, strokeColorPicker, fillColorPicker,fontSizeComboBox));
                System.out.println("Selezionato segmento button\n");
            } else {
                System.out.println("Deselezionato segmento button\n");
                statoManager.setStato(new IdleStato());
            }
            figuraSelezionataManager.clear();
            LavagnaModel.getInstance().deselezionaFigura(figuraSelezionataManager.get());
        });

        // Aggiunto da: Maria Silvana
        ellisseButton.setOnAction(e -> {
            if (ellisseButton.isSelected()) {
                statoManager.setStato(new DisegnaFiguraStato(new EllisseFactory(), lavagna, lavagnaModel, strokeColorPicker, fillColorPicker,fontSizeComboBox));
                System.out.println("Selezionato ellisse button\n");
            } else {
                System.out.println("Deselezionato ellisse button");
                statoManager.setStato(new IdleStato());;
            }
            figuraSelezionataManager.clear();
            LavagnaModel.getInstance().deselezionaFigura(figuraSelezionataManager.get());
        });

        // Aggiunto da: Michele
        grigliaButton.setOnAction(event -> {
            try{
                if(grigliaButton.isSelected())
                    Invoker.getInstance().executeCommand(new AggiungiGrigliaCommand(Integer.parseInt(nRighe.getText()), Integer.parseInt(nColonne.getText()), lavagna.getWidth(), lavagna.getHeight(), Color.LIGHTGRAY));
                else
                    Invoker.getInstance().executeCommand(new RimuoviGrigliaCommand());
            }
            catch(Exception e){
                System.out.println("Numero righe (colonne) non valido\n");
            }
        });

        // Aggiunto da: Maria Silvana
        testoButton.setOnAction(e -> {

            if (testoButton.isSelected()) {
                statoManager.setStato(new DisegnaFiguraStato(new TestoFactory(),lavagna, lavagnaModel, strokeColorPicker, fillColorPicker,fontSizeComboBox));
            } else {
                statoManager.setStato(new IdleStato());
            }
            figuraSelezionataManager.clear();
            LavagnaModel.getInstance().deselezionaFigura(figuraSelezionataManager.get());
        });

        // Aggiunto da: Mirko
        poligonoButton.setOnAction(e -> {
            if (poligonoButton.isSelected()) {
                statoManager.setStato(new DisegnaPoligonoArbitrarioStato(lavagna, lavagnaModel, strokeColorPicker, fillColorPicker));
                System.out.println("Selezionato poligono button\n");
            } else {
                System.out.println("Deselezionato poligono button\n");
                statoManager.setStato(new IdleStato());
            }
            figuraSelezionataManager.clear();
            LavagnaModel.getInstance().deselezionaFigura(figuraSelezionataManager.get());
        });


        // Aggiunto da: Maria Silvana
        lavagna.setOnMousePressed(event ->{
            statoManager.getStato().onMousePressed(event);
        });

        // Aggiunto da: Maria Silvana
        lavagna.setOnMouseDragged(event ->{
            statoManager.getStato().onMouseDragged(event);

        });

        // Aggiunto da: Maria Silvana
        lavagna.setOnMouseReleased(event ->{
            statoManager.getStato().onMouseReleased(event);
        });

        // Aggiunto da Mirko
        lavagna.setOnMouseClicked(event -> {
            statoManager.getStato().onMouseClicked(event);
        });

        //Aggiunto da Kevin
        sliderRotazione.setOnMousePressed(event ->{
            if (FiguraSelezionataManager.getInstance().get().getClass() == PoligonoArbitrario.class)
                statoManager.setStato(new RuotaPoligonoStato());
            else
                statoManager.setStato(new RuotaFiguraStato());
        });

        //Aggiunto da Kevin
        sliderRotazione.valueProperty().addListener((obs, oldVal, newVal) -> {
            statoManager.getStato().onSliderChanged(newVal.doubleValue());
        });

        //Aggiunto da Kevin
        sliderRotazione.setOnMouseReleased(e -> {
            statoManager.getStato().onSliderReleased(sliderRotazione.getValue());
        });

        //Aggiunto da Kevin
        salvaConNome.setOnAction(e ->{

            Command cmd = new SalvaFiguraCommand(salvaConNome, lavagnaModel);

            Invoker.getInstance().executeCommand(cmd);


        });

        // Aggiunto da: Mirko
        caricaFile.setOnAction(e->{
            Command cmd = new CaricaCommand(lavagnaModel, caricaFile);

            Invoker.getInstance().executeCommand(cmd);
            Invoker.getInstance().svuotaStack();

        });

        // Aggiunto da: Kevin
        spostaSopraButton.setOnAction(e -> {
            if(figuraSelezionataManager.get() != null){
                Command cmd = new SpostaSopraCommand(lavagnaModel, figuraSelezionataManager.get());

                Invoker.getInstance().executeCommand(cmd);


            }
        });

        // Aggiunto da: Kevin
        spostaSottoButton.setOnAction(e -> {
            if(figuraSelezionataManager.get() != null){
                Command cmd = new SpostaSottoCommand(lavagnaModel, figuraSelezionataManager.get());

                Invoker.getInstance().executeCommand(cmd);


            }
        });

        // Aggiunto da: Kevin
        fillColorPicker.setOnAction(e -> {
            if(figuraSelezionataManager.get() != null){
                Command cmd = new CambiaColoreInternoCommand(lavagnaModel, figuraSelezionataManager.get(), fillColorPicker.getValue());

                Invoker.getInstance().executeCommand(cmd);



            }
        });

        // Aggiunto da: Kevin
        strokeColorPicker.setOnAction(e -> {
            if(figuraSelezionataManager.get() != null){
                Command cmd = new CambiaColoreBordoCommand(lavagnaModel, figuraSelezionataManager.get(), strokeColorPicker.getValue());

                Invoker.getInstance().executeCommand(cmd);

            }
        });

        // Aggiunto da: Mirko
        undoButton.setOnAction(e -> {
            Invoker.getInstance().undo();
            figuraSelezionataManager.clear();
        });


        // Aggiunto da: Mirko
        Elimina.setOnAction(e->{
            Figura figura = figuraSelezionataManager.get();
            if(figura != null) {
                Command cmd = new EliminaCommand(lavagnaModel, figura);
                Invoker.getInstance().executeCommand(cmd);
                System.out.println("FIGURA ELIMINATA");
            }
            else
                System.out.println("Nessuna figura selezionata");
        });

        // Aggiunto da: Michele
        cut.setOnAction(e->{
            Figura figura = figuraSelezionataManager.get();
            if(figura != null) {
                Command cmd = new CutCommand(lavagnaModel, figura);
                Invoker.getInstance().executeCommand(cmd);

            }
            else
                System.out.println("Nessuna figura selezionata");
        });


        // Aggiunto da: Michele
        copy.setOnAction(event->{
            Figura figura = figuraSelezionataManager.get();
            if(figura != null) {
                Command cmd = new CopyCommand(lavagnaModel, figura);
                Invoker.getInstance().executeCommand(cmd);

            }
            else
                System.out.println("Nessuna figura selezionata");

        });

        // Aggiunto da: Michele
        paste.setOnAction(event->{
            try{
                Command cmd = new PasteCommand(lavagnaModel);
                Invoker.getInstance().executeCommand(cmd);
            }
            catch(Exception e){
                System.out.println("Nessuna figura copiata");
            }
        });

}
}