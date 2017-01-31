package io.sonicdeadlock.projectc.runtime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Alex on 10/5/2016.
 */
public class PreInit {
    private static final Logger LOGGER = LogManager.getLogger(PreInit.class);
    private static PreInit ourInstance = new PreInit();

    private PreInit() {

    }

    public static PreInit getInstance() {
        return ourInstance;
    }

}
