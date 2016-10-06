package io.sonicdeadlock.projectc.util;

import org.json.JSONObject;

import java.io.*;

/**
 * Created by Alex on 10/5/2016.
 */
public class JSONLoader {
    public static JSONObject loadJSONObject(String fileLocation) throws IOException {

        return loadJSONObject(new File(fileLocation));

    }

    public static JSONObject loadJSONObject(File file) throws IOException {
        StringBuilder source = new StringBuilder();
        BufferedReader br=new BufferedReader(new FileReader(file));
        String line;
        while ((line=br.readLine())!=null){
            source.append(line);
        }
        return new JSONObject(source.toString());

    }
}
