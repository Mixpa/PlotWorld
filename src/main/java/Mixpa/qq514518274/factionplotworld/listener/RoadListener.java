package Mixpa.qq514518274.factionplotworld.listener;

import Mixpa.qq514518274.factionplotworld.config.Config;
import Mixpa.qq514518274.factionplotworld.Util;
import Mixpa.qq514518274.factionplotworld.WorldGenerator;
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
    private static final String CANT_BUILD_IN_ROAD = Config.getCantBuildInRoad();

    @EventHandler
    public void whenBlockPlace(BlockPlaceEvent event) {
        if (!(event.getBlockPlaced().getWorld().getGenerator() instanceof WorldGenerator))
            return;
        if (Util.isRoad(event.getBlockPlaced().getChunk())) {
            event.getPlayer().sendMessage(CANT_BUILD_IN_ROAD);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void whenBlockBurn(BlockBurnEvent event) {
        if (!(event.getBlock().getWorld().getGenerator() instanceof WorldGenerator))
            return;
        if (Util.isRoad(event.getBlock()))
            event.setCancelled(true);
    }

    @EventHandler
    public void whenBlockIgnite(BlockIgniteEvent event) {
        if (!(event.getBlock().getWorld().getGenerator() instanceof WorldGenerator))
            return;
        if (Util.isRoad(event.getBlock()))
            event.setCancelled(true);
    }

    @EventHandler
    public void whenBlockBreak(BlockBreakEvent event) {
        if (!(event.getBlock().getWorld().getGenerator() instanceof WorldGenerator))
            return;
        if (Util.isRoad(event.getBlock())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(CANT_BUILD_IN_ROAD);
        }
    }

    @EventHandler
    public void whenBlockFromTo(BlockFromToEvent event) {
        if (!(event.getToBlock().getWorld().getGenerator() instanceof WorldGenerator))
            return;
        if (Util.isRoad(event.getToBlock()))
            event.setCancelled(true);
    }

    @EventHandler
    public void whenPistonExtend(BlockPistonExtendEvent event) {
        if (!(event.getBlock().getWorld().getGenerator() instanceof WorldGenerator))
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
        if (!(event.getBlock().getWorld().getGenerator() instanceof WorldGenerator))
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
        if (!(event.getBlockClicked().getWorld().getGenerator() instanceof WorldGenerator))
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

    @SuppressWarnings("unchecked")
    private static void whenExplode(Event event) {
        List<Block> blockList;
        if (event instanceof BlockExplodeEvent) {
            BlockExplodeEvent e = (BlockExplodeEvent) event;
            if (!(e.getBlock().getWorld().getGenerator() instanceof WorldGenerator)) {
                return;
            } else blockList = e.blockList();
        } else if (event instanceof EntityExplodeEvent) {
            EntityExplodeEvent e = (EntityExplodeEvent) event;
            if (!(((EntityExplodeEvent) event).getEntity().getWorld().getGenerator() instanceof WorldGenerator)) {
                return;
            } else blockList = e.blockList();
        } else return;
        List<Block> explodeBlock = new ArrayList();
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
}
