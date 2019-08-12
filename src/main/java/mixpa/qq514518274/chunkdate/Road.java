package mixpa.qq514518274.chunkdate;

import mixpa.qq514518274.config.Config;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

public class Road implements WorldChunk {
    public ChunkGenerator.ChunkData getChunkDate(ChunkGenerator.ChunkData chunkData, ChunkGenerator.BiomeGrid biome) {
        //基岩
        chunkData.setRegion(0, 0, 0, 16, 1, 16, Material.BEDROCK);
        //石头
        chunkData.setRegion(0, 1, 0, 16, Config.getWorldHeight(), 16, Material.STONE);
        //砖头
        chunkData.setRegion(0, Config.getWorldHeight(), 0, 16, Config.getWorldHeight() + 1, 16, Material.SMOOTH_BRICK);
        //设置整个区块为虚空
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                biome.setBiome(i, j, Biome.VOID);
            }
        }
        return chunkData;
    }
}
