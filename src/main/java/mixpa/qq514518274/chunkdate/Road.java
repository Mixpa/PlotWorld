package mixpa.qq514518274.chunkdate;

import mixpa.qq514518274.config.PlotConfig;
import mixpa.qq514518274.config.RoadConfig;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

public class Road{
    public static ChunkGenerator.ChunkData getChunkDate(ChunkGenerator.ChunkData chunkData, ChunkGenerator.BiomeGrid biome) {
        //基岩
        chunkData.setRegion(0, 0, 0, 16, 1, 16, Material.valueOf(RoadConfig.getLowest()));
        //石头
        chunkData.setRegion(0, 1, 0, 16, PlotConfig.getWorldHeight(), 16, Material.valueOf(RoadConfig.getBody()));
        //砖头
        chunkData.setRegion(0, PlotConfig.getWorldHeight(), 0, 16, PlotConfig.getWorldHeight() + 1, 16, Material.valueOf(RoadConfig.getFloor()));
        //设置整个区块为虚空
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                biome.setBiome(i, j, Biome.valueOf(RoadConfig.getBiome()));
            }
        }
        return chunkData;
    }
}
