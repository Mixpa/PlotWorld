package mixpa.qq514518274.config;

import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class PlotConfig implements ConfigLoader {
    @Getter
    private static int roadLength = 1;
    @Getter
    private static int plotLength = 2;
    @Getter
    private static int worldHeight = 64;
    @Getter
    private static int coolDowns = 0;
    @Getter
    private static Double resetMoney = 100d;
    @Getter
    private static LinkedHashMap<String, Integer> plotConfig;

    public static int getAddLength() {
        return roadLength + plotLength;
    }

    @Override
    public void load(File file) throws FileNotFoundException, IllegalAccessException {
        load(new FileReader(file));
    }

    @Override
    public void load(Reader reader) throws IllegalAccessException {
        Map<String, Object> map = new Yaml().load(reader);
        for (Field field : PlotConfig.class.getDeclaredFields()) {
            field.setAccessible(true);
            field.set(null, map.get(field.getName()));
        }
    }
}