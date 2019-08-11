package Mixpa.qq514518274.listener;

import Mixpa.qq514518274.Util;
import Mixpa.qq514518274.config.Config;
import net.redstoneore.legacyfactions.event.EventFactionsLandChange;
import net.redstoneore.legacyfactions.locality.Locality;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@SuppressWarnings("unused")
public class FactionListener implements Listener {
    @EventHandler
    public void whenFactionClaim(EventFactionsLandChange event) {
        if (event.getCause().equals(EventFactionsLandChange.LandChangeCause.Unclaim)) {
            return;
        }
        if (!Util.isWorld(event.getFPlayer().getPlayer()))
            return;

        for (Locality locality : event.transactions().keySet()) {
            if (Util.isRoad(locality.getChunk())) {
                event.getFPlayer().sendMessage(Config.getCantClaimInRoad());
                event.setCancelled(true);
                return;
            }
        }
    }
}
