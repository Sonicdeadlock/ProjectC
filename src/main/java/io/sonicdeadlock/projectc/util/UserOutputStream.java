package io.sonicdeadlock.projectc.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by Alex on 10/5/2016.
 */
public class UserOutputStream extends PrintStream {
    private static UserOutputStream ourInstance = new UserOutputStream(System.out);

    public UserOutputStream(OutputStream out) {
        super(out);
    }

    public static UserOutputStream getInstance() {
        return ourInstance;
    }






}
