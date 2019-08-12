package test.mixpa.qq514518274;

import mixpa.qq514518274.Util;
import mixpa.qq514518274.chunkdate.MineArea;
import mixpa.qq514518274.config.Config;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UtilTest {
    private static List<String> worlds;
    final int plot = 2;
    final int road = 1;
    final int add = 3;

    private chunk getMineArea(int x, int z) {
        if (Util.isRoad(x, z)) return null;
        x = x/3;
        z = z/3;
        return new chunk(x,z);
    }
    public List<chunk> getMineChunks(int x, int z){
        ArrayList<chunk> chunks = new ArrayList<>();
        for (int chunkX=1;chunkX<=plot;chunkX++){
            for (int chunkZ=1;chunkZ<=plot;chunkZ++)
                chunks.add(new chunk(getChunkXorZ(x,chunkX), getChunkXorZ(z,chunkZ)));
        }
        return chunks;
    }
    private int getChunkXorZ(int xOrZ, int chunkXorZ){
        if (xOrZ<0) return xOrZ*add-road+1-chunkXorZ;
        else return xOrZ*add+road-1+chunkXorZ;
    }

    @Test
    public void test() {
        System.out.println(getMineChunks(-5,0));
    }
    class chunk{
        int x;
        int z;

        chunk(int x, int z){
            this.x =x;
            this.z =z;
        }

        @Override
        public String toString() {
            return "chunk{" +
                    "x=" + x +
                    ", z=" + z +
                    '}';
        }
    }
}
