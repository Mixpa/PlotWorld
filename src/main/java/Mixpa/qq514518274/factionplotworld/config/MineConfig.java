package Mixpa.qq514518274.factionplotworld.config;

import Mixpa.qq514518274.factionplotworld.chunkdate.Mine;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class MineConfig {
    private static LinkedHashMap<Mine, Integer> mineComposition;

    public static void loadMineConfig(Plugin plugin) {
        File minesDes = new File(plugin.getDataFolder(), "mines");
        if (!minesDes.exists()) {
            if (!minesDes.mkdirs()) {
                throw new RuntimeException("无法生成Mine文件夹！");
            }
            plugin.saveResource("mines/default.yml", false);
            plugin.saveResource("mines/Material.txt", true);
        } else if (!minesDes.isDirectory()) {
            if (!minesDes.delete()) {
                throw new RuntimeException("插件的文件夹中存在一个叫做Mines的文件，且无法删除！");
            } else if (minesDes.mkdirs()) {
                throw new RuntimeException("无法生成Mine文件夹！");
            }
        }
        File[] fileArr = minesDes.listFiles();
        if (fileArr == null) return;
        if (Config.getMineComposition() == null) return;
        mineComposition = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : Config.getMineComposition().entrySet()) {
            boolean hasFile = false;
            for (File file : fileArr) {
                if (file.getName().equals(entry.getKey() + ".yml")) {
                    hasFile = true;
                    if (file.isFile()) {
                        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(new File(minesDes, entry.getKey()+".yml"));
                        mineComposition.put(((Mine) yaml.get("Mine")), entry.getValue());
                    }
                }
            }
            if (!hasFile) throw new IllegalArgumentException("mines文件夹里面必须含有和conifg配置中对应的mine配置文件！");
        }
    }

    public static LinkedHashMap<Mine, Integer> getMineComposition() {
        return mineComposition;
    }
}
