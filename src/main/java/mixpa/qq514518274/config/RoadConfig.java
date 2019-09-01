package mixpa.qq514518274.config;

import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RoadConfig {
    @Getter
    private static String biome;
    @Getter
    private static String lowest;
    @Getter
    private static String body;
    @Getter
    private static String floor;
    @Getter
    private static String frame;

    public RoadConfig(Reader reader) throws IllegalAccessException {
        Map<String, Object> map = new Yaml().load(reader);
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            field.set(null, map.get(field.getName()));
        }
    }

    public RoadConfig(File file) throws FileNotFoundException, IllegalAccessException {
        this(new FileReader(file));
    }

    public RoadConfig(InputStream is) throws IllegalAccessException {
        this(new InputStreamReader(is, StandardCharsets.UTF_8));
    }
}
