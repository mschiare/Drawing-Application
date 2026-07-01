/** Stato del sistema che gestisce l'interazione per effettuare uno zoom-out.**
* Al click del mouse sulla lavagna, viene creato e eseguito un comando
* di ZoomOut centrato sul punto cliccato.
* Gli altri eventi non sono gestiti in questo stato.
* Autori:
* - Michele*/

package com.example.State;

import com.example.Command.Command;
import com.example.Command.Invoker;
import com.example.Command.ZoomOutCommand;
import com.example.View.LavagnaView;
import javafx.scene.input.MouseEvent;

public class ZoomOutStato implements Stato{


    @Override
    public void onMousePressed(MouseEvent event){
        double x = event.getX();
        double y = event.getY();

        Command cmd = new ZoomOutCommand(x, y);
        Invoker.getInstance().executeCommand(cmd);
    }

    @Override
    public void onMouseDragged(MouseEvent event){}

    @Override
    public void onMouseReleased(MouseEvent event){}

    @Override
    public void onSliderChanged(double sliderValue) {return;}
    @Override
    public void onSliderReleased(double sliderValue){return;}
    @Override
    public void onMouseClicked(MouseEvent event) {
        return;
    }
}
