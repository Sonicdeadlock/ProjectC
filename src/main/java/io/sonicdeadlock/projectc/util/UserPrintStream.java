package io.sonicdeadlock.projectc.util;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by Alex on 10/10/2016.
 */
public class UserPrintStream extends PrintStream {
    private static UserPrintStream ourInstance = null;

    public UserPrintStream(OutputStream out) {
        super(out);
    }

    public static UserPrintStream getInstance() {
        if (ourInstance == null) {
            ourInstance = new UserPrintStream(UserOutputStream.getInstance());
        }
        return ourInstance;
    }


}
