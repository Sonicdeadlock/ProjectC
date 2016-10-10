package io.sonicdeadlock.projectc.util;

import javafx.scene.text.Text;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by Alex on 10/5/2016.
 */
public class UserOutputStream extends OutputStream {
    private static UserOutputStream ourInstance = new UserOutputStream();
    private Text guiText;
    private StringBuilder text = new StringBuilder();


    public static UserOutputStream getInstance() {
        return ourInstance;
    }

    @Override
    public void flush() {
        if (guiText != null) {
            guiText.setText(text.toString());
        }


        text = new StringBuilder();
    }

    @Override
    public void write(int b) {
        text.append((char)b);
    }

    public void setGuiText(Text text){
        this.guiText=text;
    }

}
