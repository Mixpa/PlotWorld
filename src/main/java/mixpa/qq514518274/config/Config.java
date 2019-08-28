package mixpa.qq514518274.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
//todo: 抽象所有Config
class Config {
    static void load(final File file, Class cls, ConfigInit ci) throws FileNotFoundException, IllegalAccessException {
        if (!file.exists())
            throw new FileNotFoundException("file is not find!");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (Field field : cls.getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            if (config.contains(name)) {
                ci.definitionField(field,name,config);
            } else throw new IllegalArgumentException("config.yml文件中的" + name + "配置不存在，请添加并配置它！");
        }
    }

    public interface ConfigInit {
        //定义变量的函数
        void definitionField(Field field, String name, ConfigurationSection cs) throws IllegalAccessException;
    }
}
