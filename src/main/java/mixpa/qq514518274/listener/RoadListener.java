package mixpa.qq514518274.listener;

import mixpa.qq514518274.Util;
import mixpa.qq514518274.config.Message;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class RoadListener implements Listener {

    private static void whenExplode(Event event) {
        //所有被爆炸破坏的方块存放在blockList
        List<Block> blockList;
        if (event instanceof BlockExplodeEvent) {
            BlockExplodeEvent e = (BlockExplodeEvent) event;
            if (!Util.isWorld(e.getBlock().getWorld())) {
                return;
            } else blockList = e.blockList();
        } else {
            EntityExplodeEvent e = (EntityExplodeEvent) event;
            if (!Util.isWorld(e.getLocation().getWorld())) {
                return;
            } else blockList = e.blockList();
        }
        ArrayList<Block> explodeBlock = new ArrayList<>();
        boolean isRoad = false;
        for (Block block : blockList) {
            if (!Util.isRoad(block)) {
                explodeBlock.add(block);
            } else isRoad = true;
        }
        if (isRoad) {
            for (Block block : explodeBlock) {
                block.setType(Material.AIR);
            }
            ((Cancellable) event).setCancelled(true);
        }
    }

    @EventHandler
    public void whenBlockPlace(BlockPlaceEvent event) {
        if (!Util.isWorld(event.getBlock().getWorld()))
            return;
        if (Util.isRoad(event.getBlockPlaced().getChunk())) {
            event.getPlayer().sendMessage(Message.getCantBuildInRoad());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void whenBlockBurn(BlockBurnEvent event) {
        if (!Util.isWorld(event.getBlock().getWorld()))
            return;
        if (Util.isRoad(event.getBlock()))
            event.setCancelled(true);
    }

    @EventHandler
    public void whenBlockIgnite(BlockIgniteEvent event) {
        if (!Util.isWorld(event.getBlock().getWorld()))
            return;
        if (Util.isRoad(event.getBlock()))
            event.setCancelled(true);
    }

    @EventHandler
    public void whenBlockBreak(BlockBreakEvent event) {
        if (!Util.isWorld(event.getBlock().getWorld()))
            return;
        if (Util.isRoad(event.getBlock())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Message.getCantBuildInRoad());
        }
    }

    @EventHandler
    public void whenBlockFromTo(BlockFromToEvent event) {
        if (!Util.isWorld(event.getBlock().getWorld()))
            return;
        if (Util.isRoad(event.getToBlock()))
            event.setCancelled(true);
    }

    @EventHandler
    public void whenPistonExtend(BlockPistonExtendEvent event) {
        if (!Util.isWorld(event.getBlock().getWorld()))
            return;
        for (Block block : event.getBlocks()) {
            if (Util.isRoad(block)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void whenPistonRetract(BlockPistonRetractEvent event) {
        if (!Util.isWorld(event.getBlock().getWorld()))
            return;
        for (Block block : event.getBlocks()) {
            if (Util.isRoad(block)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void whenPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        if (!Util.isWorld(event.getBlockClicked().getWorld()))
            return;
        if (Util.isRoad(event.getBlockClicked()))
            event.setCancelled(true);
    }

    @EventHandler
    public void whenBlockExplode(BlockExplodeEvent event) {
        whenExplode(event);
    }

    @EventHandler
    public void whenEntityExplode(EntityExplodeEvent event) {
        whenExplode(event);
    }
}
