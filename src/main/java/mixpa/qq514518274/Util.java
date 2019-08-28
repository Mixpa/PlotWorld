package mixpa.qq514518274;

import mixpa.qq514518274.config.PlotConfig;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Util {
    private static int roadLength;
    private static int addLength;

    static void init(){
        roadLength = PlotConfig.getRoadLength();
        addLength = PlotConfig.getPlotLength() + PlotConfig.getRoadLength();
    }

    public static boolean isRoad(int chunkX, int chunkZ) {
        if (chunkX == 0 || chunkZ == 0)
            return true;
        if (Math.abs(chunkX) % addLength <= roadLength - 1)
            return true;
        else return Math.abs(chunkZ) % addLength <= roadLength - 1;
    }

    public static boolean isRoad(Chunk chunk) {
        return isRoad(chunk.getX(), chunk.getZ());
    }

    public static boolean isRoad(Block block) {
        return isRoad(block.getChunk());
    }

    public static boolean isWorld(World world) {
        return world.getGenerator() instanceof WorldGenerator;
    }

    public static boolean isWorld(Player player) {
        return isWorld(player.getWorld());
    }
}
