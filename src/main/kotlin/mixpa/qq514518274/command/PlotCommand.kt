package mixpa.qq514518274.command

import mixpa.qq514518274.Util
import mixpa.qq514518274.chunkdate.MineArea
import mixpa.qq514518274.config.Config
import mixpa.qq514518274.config.MineConfig
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class PlotCommand : CommandExecutor {
    companion object {
        var time = WeakHashMap<Player, Long>()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            when (args.size) {
                0 -> help(sender)
                1 -> {
                    when (args[0]) {
                        "mines" -> showMines(sender)
                    }
                }
                2 -> {
                    when (args[0]) {
                        "reset" -> reset(sender, args[1])
                    }
                }
            }
        } else sender.sendMessage("you must be a player!")
        return true
    }

    private fun help(sender: CommandSender) {
        sender.sendMessage("")
    }

    private fun reset(sender: Player, name: String) {
        val chunk = sender.location.chunk
        if (Util.isRoad(chunk)) {
            sender.sendMessage("can't reset in plot!")
            return
        }
        //如果有匹配的Mine
        if (MineConfig.getMineNameMap().containsKey(name)) {
            if (sender.isOp) {
                MineArea(chunk.x, chunk.z, sender.world).resetMineArea(MineConfig.getMineNameMap()[name])
                return
            }
            //如果玩家使用过这个指令
            if (time.containsKey(sender)) {
                val coolDowns = System.currentTimeMillis() - time[sender]!!
                if (coolDowns >= Config.getCoolDowns() * 1000) {
                    resetMine(name, sender)
                } else {
                    sender.sendMessage("you should wait ${Config.getCoolDowns() * 1000 - coolDowns} s to reset!")
                }
            } else resetMine(name, sender)
        } else sender.sendMessage("$name not exists!")
    }

    private fun showMines(sender: Player) {
        for ((name, chance) in Config.getMineComposition()) sender.sendMessage("$name: $chance")
    }

    private fun resetMine(mineName: String, sender: Player) {
        val chunk = sender.location.chunk
        MineArea(chunk.x, chunk.z, sender.world).resetMineArea(MineConfig.getMineNameMap()[mineName])
        time[sender] = System.currentTimeMillis()
    }
}
