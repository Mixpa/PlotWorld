package test.mixpa.qq514518274.config;

import mixpa.qq514518274.config.ConfigLoader;
import mixpa.qq514518274.config.PlotConfig;
import mixpa.qq514518274.config.RoadConfig;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestConfigLoader {
    final File CONFIG = new File("build/resources/test/test/config.yml");
    final File ROAD_CONFIG = new File("build/resources/test/test/road.yml");
    @Test
    public void testConfigLoader(){
        List<ConfigLoader> list = new ArrayList<>();
        list.add(new PlotConfig());
        list.add(new RoadConfig());
    }
}
