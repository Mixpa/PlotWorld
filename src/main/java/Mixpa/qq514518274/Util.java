package Mixpa.qq514518274;

import Mixpa.qq514518274.config.Config;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Util {
    private static int roadLength;
    private static int addLength;

    Util() {
        roadLength = Config.getRoadLength();
        addLength = Config.getPlotLength() + Config.getRoadLength();
    }

    static boolean isRoad(int x, int z) {
        if (x == 0 || z == 0)
            return true;
        if (Math.abs(x) % addLength <= roadLength - 1)
            return true;
        else return Math.abs(z) % addLength <= roadLength - 1;
    }

    public static boolean isRoad(Chunk chunk) {
        return isRoad(chunk.getX(), chunk.getZ());
    }

    public static boolean isRoad(Block block) {
        return isRoad(block.getChunk());
    }

    public static boolean isWorld(World world){
        return world.getGenerator() instanceof WorldGenerator;
    }
    public static boolean isWorld(Player player){
        return isWorld(player.getWorld());
    }
}
