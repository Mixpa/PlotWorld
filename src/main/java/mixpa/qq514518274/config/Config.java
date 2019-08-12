package mixpa.qq514518274.config;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class Config {
    @Getter
    private static int roadLength = 1;
    @Getter
    private static int plotLength = 2;
    @Getter
    private static int addLength = 3;
    @Getter
    private static int worldHeight = 64;
    private static int coolDowns = 0;
    @Getter
    private static String cantBuildInRoad = ChatColor.translateAlternateColorCodes('&', "&a[服务器]&7这里是道路哦！");
    @Getter
    private static String cantClaimInRoad = ChatColor.translateAlternateColorCodes('&', "&a[服务器]&7禁止占领含有道路的区块哦！");

    private static LinkedHashMap<String, Integer> mineComposition;

    public Config(Plugin plugin) throws IOException {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile, true), StandardCharsets.UTF_8));
        if (config.contains("roadLength"))
            roadLength = config.getInt("roadLength");
        else writeRoadLength(writer);
        if (config.contains("plotLength"))
            plotLength = config.getInt("plotLength");
        else writePlotLength(writer);
        addLength = roadLength + plotLength;
        if (config.contains("worldHeight"))
            worldHeight = config.getInt("worldHeight");
        else writeWorldHeight(writer);
        if (config.contains("cantBuildInRoad"))
            cantBuildInRoad = ChatColor.translateAlternateColorCodes('&', config.getString("cantBuildInRoad"));
        else writeCantBuildInRoad(writer);
        if (config.contains("cantClaimInRoad"))
            cantClaimInRoad = ChatColor.translateAlternateColorCodes('&', config.getString("cantClaimInRoad"));
        else writeCantClaimInRoad(writer);
        if (config.contains("plotConfig")) {
            mineComposition = new LinkedHashMap<>();
            for (Map.Entry<String, Object> entry : config.getConfigurationSection("plotConfig").getValues(false).entrySet()) {
                mineComposition.put(entry.getKey(), (Integer) entry.getValue());
            }
        } else {
            writePlotConfig(writer);
        }
        if (config.contains("coolDowns"))
            coolDowns = config.getInt("coolDowns", 0);
        writer.close();
    }

    private static void writeRoadLength(BufferedWriter writer) throws IOException {
        writer.newLine();
        writer.write("#roadLength、lotLength代表的是世界中道路（road）和非道路（plot）区块的宽度");
        writer.newLine();
        writer.write("# 它们的宽度必须为整数 单位是区块");
        writer.newLine();
        writer.write("roadLength: 1");
    }

    private static void writePlotLength(BufferedWriter writer) throws IOException {
        writer.newLine();
        writer.write("plotLength: 2");
    }


    private static void writeWorldHeight(BufferedWriter writer) throws IOException {
        writer.newLine();
        writer.write("worldHeight: 64");
    }

    private static void writeCantBuildInRoad(BufferedWriter writer) throws IOException {
        writer.newLine();
        writer.write("cantBuildInRoad: \'&a[服务器]&7这里是道路哦！\'");
    }

    private static void writeCantClaimInRoad(BufferedWriter writer) throws IOException {
        writer.newLine();
        writer.write("cantClaimInRoad: \'&a[服务器]&7禁止占领含有道路的区块哦!\'");
    }

    private static void writePlotConfig(BufferedWriter writer) throws IOException {
        writer.newLine();
        writer.write("#下面这项是关于世界中非道路区块（称为plot）的设置");
        writer.newLine();
        writer.write("#格式为  [plot的名字]: [总占比 ]");
        writer.newLine();
        writer.write("#所有plot生成的概率加起来必须等于100 否则会引起报错哦！");
        writer.newLine();
        writer.write("plotConfig:");
        writer.newLine();
        writer.write("  default: 100");
    }

    public static LinkedHashMap<String, Integer> getMineComposition() {
        return mineComposition;
    }

    public static int getCoolDowns() {
        return coolDowns;
    }
}
