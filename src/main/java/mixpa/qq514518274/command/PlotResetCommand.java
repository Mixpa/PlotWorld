package mixpa.qq514518274.command;

import com.google.common.collect.Maps;
import mixpa.qq514518274.PlotWorld;
import mixpa.qq514518274.Util;
import mixpa.qq514518274.chunkdate.Mine;
import mixpa.qq514518274.chunkdate.MineArea;
import mixpa.qq514518274.config.Message;
import mixpa.qq514518274.config.MineConfig;
import mixpa.qq514518274.config.PlotConfig;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlotResetCommand implements CommandExecutor {
    private static Economy econ = PlotWorld.getEcon();
    private static Permission perms = PlotWorld.getPerms();
    private volatile HashMap<Player, Long> time = Maps.newHashMap();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            Player player = (Player) sender;
            String mineName = args[1];
            Chunk chunk = player.getLocation().getChunk();
            if (Util.isRoad(chunk)) {
                player.sendMessage(Message.getCantResetInRoad());
            } else if (MineConfig.getMineNameMap().containsKey(mineName)) {
                Mine mine = MineConfig.getMineNameMap().get(mineName);
                if (player.isOp()) {
                    new MineArea(chunk.getX(), chunk.getZ(), player.getWorld()).resetMineArea(mine);
                } else if (!econ.has(player, PlotConfig.getResetMoney())) {
                    sender.sendMessage(Message.getNoMoney(PlotConfig.getResetMoney()));
                    return true;
                } else if (!perms.has(player, "PlotWorld.reset") && !perms.has(player, "PlotWorld.reset." + mineName)) {//检测是否有权限
                    sender.sendMessage("you dont have permission to reset!");
                } else if (time.containsKey(player)) {//如果不是OP
                    long coolDowns = System.currentTimeMillis() - time.get(player) - PlotConfig.getCoolDowns() * 1000;
                    if (coolDowns >= 0) {
                        econ.withdrawPlayer(player, PlotConfig.getResetMoney());
                        new MineArea(chunk).resetMineArea(mine);
                        time.put(player, System.currentTimeMillis());
                    } else player.sendMessage(Message.getWaitCoolDowns(-coolDowns / 1000));
                } else {
                    econ.withdrawPlayer(player, PlotConfig.getResetMoney());
                    new MineArea(chunk.getX(), chunk.getZ(), player.getWorld()).resetMineArea(mine);
                    time.put(player, System.currentTimeMillis());
                }
            } else player.sendMessage(Message.getNoMine(mineName));
        } else {
            sender.sendMessage(Message.getResetUsage());
        }
        return true;
    }
}
