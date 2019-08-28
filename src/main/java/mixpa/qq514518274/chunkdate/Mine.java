package mixpa.qq514518274.chunkdate;

import lombok.Getter;
import mixpa.qq514518274.config.PlotConfig;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.generator.ChunkGenerator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class Mine implements ConfigurationSerializable{
    @Getter
    private String name;
    private LinkedHashMap<Material, Integer> blockComposition;

    public Mine(Map<String, Object> map) {
        name = (String) map.get("name");
        blockComposition = new LinkedHashMap<>();
        @SuppressWarnings("unchecked") Map<String, Object> blockList = (Map) map.get("blockComposition");
        int chance = 0;
        for (Map.Entry<String, Object> entry : blockList.entrySet()) {
            int value = (int) entry.getValue();
            chance += value;
            blockComposition.put(Material.valueOf(entry.getKey()), chance);
        }
    }

    public ChunkGenerator.ChunkData getChunkDate(ChunkGenerator.ChunkData chunkData, ChunkGenerator.BiomeGrid biome) {
        //设置整个区块为平原
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                biome.setBiome(i, j, Biome.PLAINS);
            }
        }
        //基岩
        chunkData.setRegion(0, 0, 0, 16, 1, 16, Material.BEDROCK);
        resetMine(chunkData);

        return chunkData;
    }

    void reset(Chunk chunk) {
        resetMine(chunk);
    }

    private <A> void resetMine(A chunkDef) {
        if (!(chunkDef instanceof Chunk || chunkDef instanceof ChunkGenerator.ChunkData))
            throw new IllegalArgumentException("type must be Chunk or ChunkDate!");
        Random random = new Random();
        int randomInt;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 1; y < PlotConfig.getWorldHeight() + 1; y++) {
                    randomInt = random.nextInt(99);
                    for (Map.Entry<Material, Integer> entry : blockComposition.entrySet()) {
                        if (entry.getValue() > randomInt) {
                            if (chunkDef instanceof Chunk)
                                ((Chunk) chunkDef).getBlock(x, y, z).setType(entry.getKey());
                            if (chunkDef instanceof ChunkGenerator.ChunkData)
                                ((ChunkGenerator.ChunkData) chunkDef).setBlock(x, y, z, entry.getKey());
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", name);
        LinkedHashMap<String, Integer> blockMap = new LinkedHashMap<>();
        int chance = 0;
        for (Map.Entry<Material, Integer> entry : blockComposition.entrySet()) {
            blockMap.put(entry.getKey().toString(), entry.getValue() - chance);
            chance = chance + entry.getValue();
        }
        map.put("blockComposition", blockMap);
        return map;
    }
}
