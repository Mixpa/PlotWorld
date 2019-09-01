package mixpa.qq514518274.config;

import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;

public class PlotConfig extends Config {
    @Getter
    private static int roadLength = 1;
    @Getter
    private static int plotLength = 2;
    @Getter
    private static int worldHeight = 64;
    @Getter
    private static int coolDowns = 0;
    @Getter
    private static LinkedHashMap<String, Integer> plotConfig;

    public PlotConfig(final File file) throws IllegalAccessException, FileNotFoundException {
        load(file, PlotConfig.class, (field, name, cs) -> {
            if (name.equals("plotConfig")) {
                plotConfig = new LinkedHashMap<>();
                for (Map.Entry<String, Object> entry : cs.getConfigurationSection("plotConfig").getValues(false).entrySet()) {
                    plotConfig.put(entry.getKey(), (Integer) entry.getValue());
                }
            } else field.set(null, cs.get(name));
        });
    }
    public void load(Reader reader){
        Yaml yaml = new Yaml().load(reader);

    }

    public static int getAddLength() {
        return roadLength + plotLength;
    }
}