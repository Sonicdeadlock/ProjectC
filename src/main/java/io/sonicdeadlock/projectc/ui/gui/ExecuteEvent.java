package io.sonicdeadlock.projectc.ui.gui;

import javafx.beans.NamedArg;
import javafx.event.EventType;
import javafx.scene.input.InputEvent;

/**
 * Created by Alex on 10/8/2016.
 */
public class ExecuteEvent extends InputEvent {
    private String line;
    private static EventType EXECUTE_EVENT =new EventType(InputEvent.ANY,"ExecuteEvent");
    public ExecuteEvent(String line) {
        super(EXECUTE_EVENT);
        this.line= line;
    }

    public String getLine() {
        return line;
    }
}
