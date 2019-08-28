package mixpa.qq514518274.command;

import mixpa.qq514518274.config.MineConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PlotMinesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("----mines list----");
        for (String name : MineConfig.getMineNameMap().keySet()) {
            sender.sendMessage("----"+name+"----");
        }
        sender.sendMessage("---------------");
        return true;
    }
}
