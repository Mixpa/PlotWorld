package mixpa.qq514518274.command;

import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class TabPlotCommand implements TabCompleter {
    private static List<String> commands = Lists.newArrayList("help", "reset", "mines");
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length==0)
            return commands;
        return null;
    }
}
