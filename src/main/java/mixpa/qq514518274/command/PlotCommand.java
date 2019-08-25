package mixpa.qq514518274.command;

import com.google.common.collect.Maps;
import mixpa.qq514518274.Util;
import mixpa.qq514518274.chunkdate.Mine;
import mixpa.qq514518274.chunkdate.MineArea;
import mixpa.qq514518274.config.Config;
import mixpa.qq514518274.config.Message;
import mixpa.qq514518274.config.MineConfig;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlotCommand implements CommandExecutor {
    private volatile HashMap<Player, Long> time = Maps.newHashMap();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            plotHelp(sender);
            return true;
        }
        if (sender instanceof Player) {
            switch (args[0]) {
                case "help": {
                    plotHelp(sender);
                    break;
                }
                case "reset": {
                    if (args.length == 2) {
                        plotReset((Player) sender, args[1]);
                    } else {
                        sender.sendMessage(Message.getResetUsage());
                    }
                    break;
                }
                case "mines": {
                    sender.sendMessage("---------------");
                    for (String name : MineConfig.getMineNameMap().keySet()) {
                        sender.sendMessage(name);
                    }
                    sender.sendMessage("---------------");
                    break;
                }
                default:
                    plotHelp(sender);
                    break;
            }
        } else sender.sendMessage(Message.getMustBeAPlayer());
        return true;
    }

    private void plotHelp(CommandSender sender) {
        sender.sendMessage(Message.getHelpMessage().toArray(new String[0]));
    }

    private void plotReset(Player player, String mineName) {
        Chunk chunk = player.getLocation().getChunk();
        if (Util.isRoad(chunk)) {
            player.sendMessage(Message.getCantResetInRoad());
        } else if (MineConfig.getMineNameMap().containsKey(mineName)) {
            Mine mine = MineConfig.getMineNameMap().get(mineName);
            if (player.isOp()) {
                new MineArea(chunk.getX(),chunk.getZ(),player.getWorld()).resetMineArea(mine);
            } else if (time.containsKey(player)) {
                long coolDowns = System.currentTimeMillis() - time.get(player) - Config.getCoolDowns() * 1000;
                if (coolDowns >= 0) {
                    new MineArea(chunk).resetMineArea(mine);
                    time.put(player, System.currentTimeMillis());
                } else player.sendMessage(Message.getWaitCoolDowns(-coolDowns / 1000));
            } else {
                new MineArea(chunk.getX(), chunk.getZ(),player.getWorld()).resetMineArea(mine);
                time.put(player, System.currentTimeMillis());
            }
        } else player.sendMessage(Message.getNoMine(mineName));
    }
}
