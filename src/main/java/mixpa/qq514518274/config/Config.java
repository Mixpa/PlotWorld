package mixpa.qq514518274.config;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class Config {
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

    public Config(final File file) throws IllegalAccessException {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (Field field : Config.class.getDeclaredFields()) {
            String name = field.getName();
            field.setAccessible(true);
            if (config.contains(name)) {
                if (name.equals("plotConfig")) {
                    plotConfig = new LinkedHashMap<>();
                    for (Map.Entry<String, Object> entry : config.getConfigurationSection("plotConfig").getValues(false).entrySet()) {
                        plotConfig.put(entry.getKey(), (Integer) entry.getValue());
                    }
                    continue;
                }
                if (field.getType() == String.class)
                    field.set(null, ChatColor.translateAlternateColorCodes('&', config.getString(name)));
                field.set(null, config.get(name));
            } else throw new IllegalArgumentException("config.yml文件中的" + name + "配置不存在，请添加并配置它！");
        }
    }
    public static int getAddLength() {
        return roadLength + plotLength;
    }
}