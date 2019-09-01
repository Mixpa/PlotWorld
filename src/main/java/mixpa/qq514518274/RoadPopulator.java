package mixpa.qq514518274;

import mixpa.qq514518274.config.PlotConfig;
import mixpa.qq514518274.config.RoadConfig;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

class RoadPopulator extends BlockPopulator {
    private final static int addLength = PlotConfig.getPlotLength() + PlotConfig.getRoadLength();

    private static void setZBrickX15(Chunk chunk) {
        for (int z = 15; z >= 0; z--) {
            chunk.getBlock(15, PlotConfig.getWorldHeight() + 1, z).setType(Material.valueOf(RoadConfig.getFrame()));
        }
    }

    private static void setZBrickX0(Chunk chunk) {
        for (int z = 15; z >= 0; z--) {
            chunk.getBlock(0, PlotConfig.getWorldHeight() + 1, z).setType(Material.valueOf(RoadConfig.getFrame()));
        }
    }

    private static void setXBrickZ15(Chunk chunk) {
        for (int x = 15; x >= 0; x--) {
            chunk.getBlock(x, PlotConfig.getWorldHeight() + 1, 15).setType(Material.valueOf(RoadConfig.getFrame()));
        }
    }

    private static void setXBrickZ0(Chunk chunk) {
        for (int x = 15; x >= 0; x--) {
            chunk.getBlock(x, PlotConfig.getWorldHeight() + 1, 0).setType(Material.valueOf(RoadConfig.getFrame()));
        }
    }

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int x = chunk.getX();
        int z = chunk.getZ();
        if (Util.isRoad(chunk)) {
            if (Math.abs(x) % addLength > PlotConfig.getRoadLength() - 1) {
                if (!Util.isRoad(x, z + 1))
                    setXBrickZ15(chunk);
                if (!Util.isRoad(x, z - 1))
                    setXBrickZ0(chunk);
            }
            if (Math.abs(z) % addLength > PlotConfig.getRoadLength() - 1) {
                if (!Util.isRoad(x + 1, z))
                    setZBrickX15(chunk);
                if (!Util.isRoad(x - 1, z))
                    setZBrickX0(chunk);
            }
            if (!Util.isRoad(x + 1, z + 1))
                chunk.getBlock(15, PlotConfig.getWorldHeight() + 1, 15).setType(Material.valueOf(RoadConfig.getFrame()));
            if (!Util.isRoad(x + 1, z - 1))
                chunk.getBlock(15, PlotConfig.getWorldHeight() + 1, 0).setType(Material.valueOf(RoadConfig.getFrame()));
            if (!Util.isRoad(x - 1, z - 1))
                chunk.getBlock(0, PlotConfig.getWorldHeight() + 1, 0).setType(Material.valueOf(RoadConfig.getFrame()));
            if (!Util.isRoad(x - 1, z + 1))
                chunk.getBlock(0, PlotConfig.getWorldHeight() + 1, 15).setType(Material.valueOf(RoadConfig.getFrame()));
        }
    }
}
