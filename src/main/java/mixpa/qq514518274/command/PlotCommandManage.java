package mixpa.qq514518274.command;

import mixpa.qq514518274.config.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PlotCommandManage implements TabExecutor {
    private static final CommandExecutor HELP = new PlotHelpCommand();
    private static final CommandExecutor RESET = new PlotResetCommand();
    private static final CommandExecutor MINES = new PlotMinesCommand();
    private static List<String> commandList;
    public PlotCommandManage(){
        commandList = new ArrayList<>();
        for (Field field : PlotCommandManage.class.getDeclaredFields()) {
            if (field.getName().equals("commandList"))
                continue;
            commandList.add(field.getName().toLowerCase());
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return HELP.onCommand(sender, command, label, args);
        }
        if (sender instanceof Player) {
            switch (args[0]) {
                case "help": {
                    return HELP.onCommand(sender, command, label, args);
                }
                case "reset": {
                    return RESET.onCommand(sender, command, label, args);
                }
                case "mines": {
                    return MINES.onCommand(sender, command, label, args);
                }
                default:
                    HELP.onCommand(sender, command, label, args);
                    break;
            }
        } else sender.sendMessage(Message.getMustBeAPlayer());
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length ==1)
            return commandList;
        return null;
    }
}
