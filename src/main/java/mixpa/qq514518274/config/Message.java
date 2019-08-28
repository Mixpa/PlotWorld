package mixpa.qq514518274.config;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Message {
    @Getter
    private static String prefix;
    @Getter
    private static String cantBuildInRoad;
    @Getter
    private static String cantClaimInRoad;
    @Getter
    private static String resetUsage;
    @Getter
    private static String cantResetInRoad;
    @Getter
    private static String noMine;
    @Getter
    private static String mustBeAPlayer;
    @Getter
    private static String waitCoolDowns;
    @Getter
    private static List<String> helpMessage;

    public Message(final File file) throws IllegalAccessException, FileNotFoundException {
        if (!file.exists())
            throw new FileNotFoundException("file is not find!");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (Field field : Message.class.getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            if (config.contains(name)) {
                field.set(null, config.get(name));
            } else throw new IllegalAccessException("配置文件message.yml中的" + name + "配置不存在！");
        }
        for (Field field : Message.class.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals("prefix"))
                continue;
            if (field.getName().equals("helpMessage"))
                continue;
            field.set(null, transString((String) field.get(null)));
        }
        helpMessage = transString(helpMessage);
    }

    public static String getNoMine(String mineName) {
        return noMine.replaceAll("\\{mine}", mineName);
    }

    public static String getWaitCoolDowns(Long coolDowns) {
        return waitCoolDowns.replaceAll("\\{cool_downs}", coolDowns.toString());
    }

    private List<String> transString(List<String> list) {
        ArrayList<String> help = Lists.newArrayList();
        for (String var : list) {
            help.add(ChatColor.translateAlternateColorCodes('&', var));
        }
        return help;
    }

    private String transString(String var) {
        return ChatColor.translateAlternateColorCodes('&', prefix + var);
    }
}
