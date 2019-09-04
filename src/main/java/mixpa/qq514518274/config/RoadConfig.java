package mixpa.qq514518274.config;

import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.Map;

public class RoadConfig implements ConfigLoader {
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

    @Override
    public void load(File file) throws FileNotFoundException, IllegalAccessException {
        load(new FileReader(file));
    }

    @Override
    public void load(Reader reader) throws IllegalAccessException {
        Map<String, Object> map = new Yaml().load(reader);
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            field.set(null, map.get(field.getName()));
        }
    }
}
