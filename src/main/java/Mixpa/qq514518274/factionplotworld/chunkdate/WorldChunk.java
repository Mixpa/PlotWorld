package Mixpa.qq514518274.factionplotworld.chunkdate;

import org.bukkit.generator.ChunkGenerator;

public interface WorldChunk {
    ChunkGenerator.ChunkData getChunkDate(ChunkGenerator.ChunkData chunkData, ChunkGenerator.BiomeGrid biome);
}
