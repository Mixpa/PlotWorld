package mixpa.qq514518274;

import mixpa.qq514518274.chunkdate.Mine;
import mixpa.qq514518274.command.PlotCommand;
import mixpa.qq514518274.command.TabPlotCommand;
import mixpa.qq514518274.config.Config;
import mixpa.qq514518274.config.Message;
import mixpa.qq514518274.config.MineConfig;
import mixpa.qq514518274.listener.PluginListener;
import mixpa.qq514518274.listener.RoadListener;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LoadOrder;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.bukkit.plugin.java.annotation.plugin.author.Authors;

import java.io.File;

/**
 * {@code FactionPlotWorld} 插件的主类
 *
 * @author Mixpa
 */
@Plugin(name = "PlotWorld", version = "1.2.0")
@Authors(@Author("Mixpa"))
@LoadOrder(PluginLoadOrder.STARTUP)
@Description("A Plot Faction world generator.")
@SoftDependency("LegacyFactions")
@Commands(@Command(name = "plot", aliases = "p", desc = "插件的主命令"))
public class PlotWorld extends JavaPlugin {
    @Override
    public void onEnable() {
        //load config.yml message.yml
        //config.yml message.yml 的加载
        File config = new File(getDataFolder(), "config.yml");
        File message = new File(getDataFolder(), "message.yml");
        if (!config.exists())
            saveResource("config.yml", false);
        if (!message.exists())
            saveResource("message.yml", false);
        try {
            new Config(config);
            new Message(message);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //load Mine's config
        //对Mine的设置进行加载
        ConfigurationSerialization.registerClass(Mine.class);
        MineConfig.loadMineConfig(this);
        //init Util
        //初始化Util工具类
        Util.init();
        //register Event's
        //监听类加载
        getServer().getPluginManager().registerEvents(new RoadListener(), this);
        getServer().getPluginManager().registerEvents(new PluginListener(this), this);
        //register Commands
        //注册命令
        getCommand("plot").setExecutor(new PlotCommand());
        getCommand("plot").setTabCompleter(new TabPlotCommand());
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new WorldGenerator();
    }
}
