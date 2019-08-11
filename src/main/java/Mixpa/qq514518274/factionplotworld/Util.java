package Mixpa.qq514518274.factionplotworld;

import Mixpa.qq514518274.factionplotworld.config.Config;
import org.bukkit.Chunk;
import org.bukkit.block.Block;

public class Util {
    private static int roadLength = Config.getRoadLength();
    private static int addLength = Config.getRoadLength()+Config.getPlotLength();

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
}
