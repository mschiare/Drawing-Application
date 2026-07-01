/** Stato del sistema che gestisce l'interazione per effettuare uno zoom-in.
** Al click del mouse sulla lavagna, viene creato e eseguito un comando
* di ZoomIn centrato sul punto cliccato.* Gli altri eventi non sono gestiti in questo stato.
Autori:
- Michele*/

package com.example.State;

import com.example.Command.Command;
import com.example.Command.Invoker;
import com.example.Command.ZoomInCommand;
import com.example.View.LavagnaView;
import javafx.scene.input.MouseEvent;

public class ZoomInStato implements Stato{


    @Override
    public void onMousePressed(MouseEvent event){
        double x = event.getX();
        double y = event.getY();
        Command cmd = new ZoomInCommand(x, y);
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
