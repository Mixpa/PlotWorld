package mixpa.qq514518274.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

public class PluginListener implements Listener {
    private static Plugin plugin;
    public PluginListener(Plugin plugin){
        PluginListener.plugin = plugin;
    }
    @SuppressWarnings("unused")
    @EventHandler
    public void
    whenFactionEnable(PluginEnableEvent event){
        if (!event.getPlugin().getName().equals("LegacyFactions"))
            return;
        if (!event.getPlugin().isEnabled())
            return;
        plugin.getServer().getPluginManager().registerEvents(new FactionListener(), plugin);
    }
}
