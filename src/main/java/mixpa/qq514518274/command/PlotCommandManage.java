package mixpa.qq514518274.command;

import mixpa.qq514518274.config.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlotCommandManage implements TabExecutor {
    private static final CommandExecutor HELP = new PlotHelpCommand();
    private static final CommandExecutor RESET = new PlotResetCommand();
    private static final CommandExecutor MINES = new PlotMinesCommand();
    private static List<String> commandList;
    private static Map<String, List<String>> aloneCommand;
    public PlotCommandManage(){
        commandList = new ArrayList<>();
        aloneCommand = new HashMap<>();
        for (Field field : PlotCommandManage.class.getDeclaredFields()) {
            if (field.getName().equals("commandList"))
                continue;
            if (field.getName().equals("aloneCommand"))
                continue;
            String name = field.getName().toLowerCase();
            commandList.add(name);
            List<String> alone = new ArrayList<>();
            alone.add(name);
            aloneCommand.put(name, alone);
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
        if (args.length ==1){
            if (args[0].equals("")){
                return commandList;
            }
            for (Map.Entry<String, List<String>> entry : aloneCommand.entrySet()) {
                if (entry.getKey().startsWith(args[0])) return entry.getValue();
            }
        }
        return null;
    }
}
