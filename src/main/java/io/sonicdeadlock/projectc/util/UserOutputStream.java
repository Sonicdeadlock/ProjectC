package io.sonicdeadlock.projectc.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by Alex on 10/5/2016.
 */
public class UserOutputStream extends OutputStream {
    private static UserOutputStream ourInstance = new UserOutputStream();
    private StringBuilder text = new StringBuilder(),flash= new StringBuilder();


    public static UserOutputStream getInstance() {
        return ourInstance;
    }

    @Override
    public void flush() {
        flash.append(text);


        text = new StringBuilder();
    }

    public String getFlash(){
        String f = flash.toString();
        flash = new StringBuilder();
        return f;
    }

    @Override
    public void write(int b) {
        text.append((char)b);
    }
}
