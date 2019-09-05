package mixpa.qq514518274;

import lombok.Getter;
import mixpa.qq514518274.chunkdate.Mine;
import mixpa.qq514518274.command.PlotCommandManage;
import mixpa.qq514518274.config.Message;
import mixpa.qq514518274.config.MineConfig;
import mixpa.qq514518274.config.PlotConfig;
import mixpa.qq514518274.config.RoadConfig;
import mixpa.qq514518274.listener.PluginListener;
import mixpa.qq514518274.listener.RoadListener;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.dependency.SoftDependency;
import org.bukkit.plugin.java.annotation.dependency.SoftDependsOn;
import org.bukkit.plugin.java.annotation.permission.Permissions;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LoadOrder;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.bukkit.plugin.java.annotation.plugin.author.Authors;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * {@code FactionPlotWorld} 插件的主类
 *
 * @author Mixpa
 */
@Plugin(name = "PlotWorld", version = "1.4.0")
@Authors(@Author("Mixpa"))
@LoadOrder(PluginLoadOrder.STARTUP)
@Description("A Plot world generator.")
@SoftDependsOn(value = {@SoftDependency("LegacyFactions"), @SoftDependency("Vault")})
@Commands(@Command(name = "plot", aliases = "p", desc = "插件的主命令"))
@Permissions(value = @org.bukkit.plugin.java.annotation.permission.Permission(name = "PlotWorld.reset", defaultValue = PermissionDefault.FALSE))
public class PlotWorld extends JavaPlugin {
    @Getter
    private static Economy econ = null;
    @Getter
    private static Permission perms = null;

    @Override
    public void onEnable() {
        loadConfig();
        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
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
        TabExecutor te = new PlotCommandManage();
        getCommand("plot").setExecutor(te);
        getCommand("plot").setTabCompleter(te);

    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new WorldGenerator();
    }

    private void loadConfig() {
        File config = new File(getDataFolder(), "config.yml");
        File message = new File(getDataFolder(), "message.yml");
        File road = new File(getDataFolder(), "road.yml");
        if (!config.exists())
            saveResource("config.yml", false);
        if (!message.exists())
            saveResource("message.yml", false);
        if (!road.exists())
            saveResource("road.yml", false);
        try {
            new PlotConfig().load(config);
            new Message(message);
            new RoadConfig().load(road);
        } catch (IllegalAccessException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private void setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
    }
}
