/*
* Interfaccia per il pattern Command.
*
* Definisce i metodi base per eseguire un comando ('execute'), annullarlo ('undo'),
* verificare se supporta l'annullamento ('isUndoable') e controllare se può essere eseguito ('canExecute').
*
* Autori:
*  - Michele: execute(), undo()
*  - Kevin: canExecute()
*  - Mirko: isUndoable()
*/
package com.example.Command;


public interface Command {
    public void execute();
    public void undo();
    public boolean isUndoable();
    default boolean canExecute() {
        return true; // di default tutti i comandi sono eseguibili
    }
}


