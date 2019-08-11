package Mixpa.qq514518274.factionplotworld.chunkdate;

import Mixpa.qq514518274.factionplotworld.config.Config;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.generator.ChunkGenerator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Mine implements ConfigurationSerializable, WorldChunk {
    private String name;
    private LinkedHashMap<Material, Integer> blockComposition;

    public Mine(Map<String, Object> map) {
        name = (String) map.get("name");
        blockComposition = new LinkedHashMap<>();
        @SuppressWarnings("unchecked") Map<String, Object> blockList = (Map<String, Object>) map.get("blockComposition");
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
        Random random = new Random();
        int randomInt;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 1; y < Config.getWorldHeight() + 1; y++) {
                    randomInt = random.nextInt(99);
                    for (Map.Entry<Material, Integer> entry : blockComposition.entrySet()) {
                        if (entry.getValue() > randomInt) {
                            chunkData.setBlock(x, y, z, entry.getKey());
                            break;
                        }
                    }
                }
            }
        }

        return chunkData;
    }

    //todo
    @SuppressWarnings("unused")
    public void setChunk(Chunk chunk) {
        ConcurrentHashMap<Material, Integer> map = new ConcurrentHashMap<>(blockComposition);
        Random random = new Random();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 1; y < Config.getWorldHeight() + 1; y++) {
                    for (Map.Entry<Material, Integer> entry : map.entrySet()) {
                        if (entry.getValue() > random.nextInt(99)) {
                            if (chunk.getBlock(x, y, z).isEmpty())
                                chunk.getBlock(x, y, z).setType(entry.getKey());
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
        LinkedHashMap<String, Double> blockMap = new LinkedHashMap<>();
        Double chance = 0D;
        for (Map.Entry<Material, Integer> entry : blockComposition.entrySet()) {
            blockMap.put(entry.getKey().toString(), entry.getValue() - chance);
            chance = chance + entry.getValue();
        }
        map.put("blockComposition", blockMap);
        return map;
    }
}
