package mixpa.qq514518274;

import mixpa.qq514518274.command.PlotCommand;
import mixpa.qq514518274.chunkdate.Mine;
import mixpa.qq514518274.config.Config;
import mixpa.qq514518274.config.MineConfig;
import mixpa.qq514518274.listener.PluginListener;
import mixpa.qq514518274.listener.RoadListener;
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
        //load config.yml
        //config.yml的加载
        File config = new File(getDataFolder(), "config.yml");
        if (!config.exists())
            saveResource("config.yml", false);
        try {
            new Config(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //load Mine's config
        //对Mine的设置进行加载
        ConfigurationSerialization.registerClass(Mine.class);
        MineConfig.loadMineConfig(this);
        //init Util
        //初始化Util工具类
        new Util();
        //register Event's
        //监听类加载
        getServer().getPluginManager().registerEvents(new RoadListener(), this);
        whenLegacyFactionsEnable();
        //register Commands
        //注册命令
        getCommand("plot").setExecutor(new PlotCommand());
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new WorldGenerator();
    }

    private void whenLegacyFactionsEnable() {
        getServer().getPluginManager().registerEvents(new PluginListener(this), this);
    }
}
