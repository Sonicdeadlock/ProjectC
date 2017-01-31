package io.sonicdeadlock.projectc.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sonicdeadlock.projectc.world.Loadable;

import java.io.File;
import java.io.IOException;

/**
 * Created by Alex on 10/5/2016.
 */
public class SaveUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
    }

    public static <T extends Loadable> T load(String fileLocation, Class<T> clazz) throws IOException {
        return load(new File(fileLocation), clazz);
    }

    public static <T extends Loadable> T load(File file, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(file, clazz);
    }

    public static void save(File file, Loadable loadable) throws IOException {
        OBJECT_MAPPER.writeValue(file, loadable);
    }

    public static void save(String fileLocation, Loadable loadable) throws IOException {
        save(new File(fileLocation), loadable);
    }

}
