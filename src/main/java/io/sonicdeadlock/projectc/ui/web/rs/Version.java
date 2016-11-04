package io.sonicdeadlock.projectc.ui.web.rs;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 
@Path("version")
public class Version{
    private static final Logger LOGGER = LogManager.getLogger(Version.class);

@GET
public String getVersion(){
	try{
		BufferedReader br = new BufferedReader(new InputStreamReader(Version.class.getClassLoader().getResourceAsStream("version")));
		String line = br.readLine();
		br.close();
	return line;
	}catch(IOException ex){
		LOGGER.error("error reading version file",ex);
		}
	return null;
	}

}
