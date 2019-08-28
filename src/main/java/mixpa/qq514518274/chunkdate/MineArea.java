package mixpa.qq514518274.chunkdate;

import lombok.Getter;
import mixpa.qq514518274.Util;
import mixpa.qq514518274.config.PlotConfig;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class MineArea {
    @Getter
    private int X;
    @Getter
    private int Z;
    @Getter
    private World world;

    public MineArea(int chunkX, int chunkZ, World world) {
        if (Util.isRoad(chunkX, chunkZ))
            throw new IllegalArgumentException("can not in Road!");
        this.X = chunkX / PlotConfig.getAddLength();
        this.Z = chunkZ / PlotConfig.getAddLength();
        this.world = world;
    }

    public MineArea(Chunk chunk) {
        new MineArea(chunk.getX(), chunk.getZ(), chunk.getWorld());
    }

    private List<Chunk> getMineChunks() {
        ArrayList<Chunk> chunks = new ArrayList<>();
        for (int chunkX = 1; chunkX <= PlotConfig.getPlotLength(); chunkX++) {
            for (int chunkZ = 1; chunkZ <= PlotConfig.getPlotLength(); chunkZ++) {
                chunks.add(world.getChunkAt(getChunkXorZ(this.X, chunkX), getChunkXorZ(this.Z, chunkZ)));
            }
        }
        return chunks;
    }

    private int getChunkXorZ(int xOrZ, int chunkXorZ) {
        if (xOrZ < 0) return xOrZ * PlotConfig.getAddLength() - PlotConfig.getRoadLength() + 1 - chunkXorZ;
        else return xOrZ * PlotConfig.getAddLength() + PlotConfig.getRoadLength() - 1 + chunkXorZ;
    }

    public void resetMineArea(Mine mine) {
        for (Chunk chunk : getMineChunks()) {
            mine.reset(chunk);
        }
    }

    @Override
    public String toString() {
        return "MineArea{" +
                "chunkX=" + X +
                ", chunkZ=" + Z +
                '}';
    }
}
