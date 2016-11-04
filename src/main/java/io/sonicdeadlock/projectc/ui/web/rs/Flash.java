package io.sonicdeadlock.projectc.ui.web.rs;

import io.sonicdeadlock.projectc.util.UserOutputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Alex on 10/30/2016.
 */

@Path("flash")
public class Flash {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getFlash(){
        UserOutputStream.getInstance().flush();
        return UserOutputStream.getInstance().getFlash();
    }
}
