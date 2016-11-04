package io.sonicdeadlock.projectc.ui.web.rs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alex on 10/15/2016.
 */
@ApplicationPath("api")
public class APIApplication extends Application {
    private static final Logger LOGGER = LogManager.getLogger(APIApplication.class);

//    @PostConstruct
//    public void init(){
//        LOGGER.debug("init");
//    }

//    @Override
//    public Set<Class<?>> getClasses() {
//        return new HashSet<Class<?>>(Arrays.asList(Move.class));
//    }
}
