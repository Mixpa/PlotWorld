package mixpa.qq514518274.command;

import mixpa.qq514518274.config.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PlotHelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(Message.getHelpMessage().toArray(new String[0]));
        return true;
    }
}
