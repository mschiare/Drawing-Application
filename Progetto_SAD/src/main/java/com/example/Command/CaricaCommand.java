/**
 * Comando per caricare una lista di figure geometriche da un file di testo formattato.
 *
 * Il comando apre una finestra di dialogo per la selezione di un file .txt, quindi legge
 * il file riga per riga, interpretando ogni riga come una descrizione di una figura:
 * - Supporta i tipi: ellisse, rettangolo, segmento, testo e poligono arbitrario.
 * - Per ogni figura legge le proprietà geometriche, colori e rotazione.
 *
 * In caso di errore di formato o I/O viene mostrato un alert all'utente.
 * Se il file è vuoto, viene mostrato un avviso e nessuna figura viene caricata.
 *
 * Il caricamento comporta la pulizia della lavagna (modello) e l'inserimento delle nuove figure.
 *
 * Il comando non supporta l'undo.
 *
 * Il file di input deve rispettare il formato previsto, con campi separati da ';',
 * ed etichette per proprietà come x1=valore, stroke=#hexcolor, ecc.
 */

package com.example.Command;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.Model.*;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;


public class CaricaCommand implements Command {

    private LavagnaModel lavagnaModel;
    private MenuItem apriFile;


    public CaricaCommand(LavagnaModel lavagnaModel, MenuItem apriFile) {
        this.lavagnaModel = lavagnaModel;
        this.apriFile = apriFile;
    }

    @Override
    public void execute() {
        Figura figura;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona un file .txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File file = fileChooser.showOpenDialog(apriFile.getParentPopup().getOwnerWindow());
        if (file != null) {
            double x1 = 0;
            double y1 = 0;
            double x2 = 0;
            double y2 = 0;
            Color stroke;
            Color fill;
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                lavagnaModel.svuotaLavagna();
                List<Figura> figureTemp = new ArrayList<>();
                boolean fileVuoto = true;
                String line;
                while ((line = reader.readLine()) != null) {
                    fileVuoto = false;

                    try {
                        String[] parts = line.split(";");
                        String tipo = parts[0];
                        String content = "";
                        int fontSize = 0;
                        double rotazione = 0;
                        List<Double> punti = new ArrayList<>();
                        if (tipo.equals("poligonoarbitrario")) {
                            for (int i = 1; i < parts.length - 3; i++) {
                            punti.add(Double.parseDouble(parts[i]));
                            }
                            stroke = Color.web(parts[parts.length-3].split("=")[1]);
                            fill = Color.web(parts[parts.length-2].split("=")[1]);
                            rotazione = Double.parseDouble(parts[parts.length-1].split("=")[1]);
                        } else {
                            x1 = Double.parseDouble(parts[1].split("=")[1]);
                            y1 = Double.parseDouble(parts[2].split("=")[1]);
                            x2 = Double.parseDouble(parts[3].split("=")[1]);
                            y2 = Double.parseDouble(parts[4].split("=")[1]);
                            stroke = Color.web(parts[5].split("=")[1]);
                            fill = Color.web(parts[6].split("=")[1]);
                            rotazione = Double.parseDouble(parts[7].split("=")[1]);
                            if(parts.length == 10){
                                fontSize = Integer.parseInt(parts[8].split("=")[1]);
                                content = parts[9].split("=")[1];
                            }
                        }

                        figura = switch (tipo) {
                            case "ellisse" -> new Ellisse(x1, y1, x2, y2, stroke, fill);
                            case "rettangolo" -> new Rettangolo(x1, y1, x2, y2, stroke, fill);
                            case "segmento" -> new Segmento(x1, y1, x2, y2, stroke, fill);
                            case "testo" -> new Testo(x1, y1, x2, y2, stroke, fill, fontSize, content);
                            case "poligonoarbitrario" -> new PoligonoArbitrario(punti, stroke, fill);
                            default -> throw new IllegalArgumentException("Tipo di tipo non valido"+tipo);
                        };

                        figura.setRotazione(rotazione);
                        figureTemp.add(figura);

                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Errore di caricamento");
                        alert.setHeaderText("Formattazione del file non valida!");
                        alert.setContentText("Errore nella riga: \"" + line + "\"");

                        alert.showAndWait();
                        return;
                    }

                }
                if (fileVuoto) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("File vuoto");
                    alert.setHeaderText("Il file selezionato è vuoto");
                    alert.setContentText("Nessuna figura è stata caricata.");
                    alert.showAndWait();
                } else {
                    lavagnaModel.caricaFigure(figureTemp);
                }

            } catch (IOException e) {
                e.printStackTrace();
                lavagnaModel.svuotaLavagna();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore di lettura");
                alert.setHeaderText("Impossibile leggere il file");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }
    @Override
    public void undo() {
        return;
    }
    @Override
    public boolean isUndoable() {
        return false;
    }
}

