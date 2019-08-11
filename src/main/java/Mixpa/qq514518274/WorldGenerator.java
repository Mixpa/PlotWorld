package Mixpa.qq514518274;

import Mixpa.qq514518274.chunkdate.Mine;
import Mixpa.qq514518274.chunkdate.Road;
import Mixpa.qq514518274.config.MineConfig;
import Mixpa.qq514518274.config.Config;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WorldGenerator extends ChunkGenerator {
    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        ArrayList<BlockPopulator> list = new ArrayList<>();
        list.add(new RoadPopulator());
        return list;
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        ChunkData chunkData = createChunkData(world);
        //如果是道路
        if (Util.isRoad(x, z))
            return new Road().getChunkDate(chunkData, biome);
        if (Config.getMineComposition().size() == 0)
            throw new IllegalArgumentException("插件的mine占比出现问题！请检查config中的plotConfig！");
        int randomInt = random.nextInt(99);
        for (Map.Entry<Mine, Integer> entry : MineConfig.getMineComposition().entrySet()) {
            if (entry.getValue() > randomInt) {
                return entry.getKey().getChunkDate(chunkData, biome);
            }
        }
        throw new IllegalArgumentException("插件的mine占比出现问题！请检查config中的plotConfig！");
    }
}