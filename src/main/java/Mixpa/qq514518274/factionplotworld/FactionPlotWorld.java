package Mixpa.qq514518274.factionplotworld;

import Mixpa.qq514518274.factionplotworld.chunkdate.Mine;
import Mixpa.qq514518274.factionplotworld.config.Config;
import Mixpa.qq514518274.factionplotworld.config.MineConfig;
import Mixpa.qq514518274.factionplotworld.listener.FactionListener;
import Mixpa.qq514518274.factionplotworld.listener.RoadListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * {@code FactionPlotWorld} 插件的主类
 *
 * @author Mixpa
 */
@SuppressWarnings("unused")
public class FactionPlotWorld extends JavaPlugin {
    @Override
    public void onEnable() {
        //config.yml的加载
        File config = new File(getDataFolder(), "config.yml");
        if (!config.exists())
            saveResource("config.yml", false);
        try {
            new Config(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对Mine的设置进行加载
        ConfigurationSerialization.registerClass(Mine.class);
        MineConfig.loadMineConfig(this);
        //初始化Util工具类
        new Util();
        //监听类加载
        getServer().getPluginManager().registerEvents(new RoadListener(), this);
        whenLegacyFactionsEnable();
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new WorldGenerator();
    }

    private void whenLegacyFactionsEnable() {
        if (Bukkit.getPluginManager().isPluginEnabled("LegacyFactions")) {
            getServer().getPluginManager().registerEvents(new FactionListener(), this);
        }
    }
}
