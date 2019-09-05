package test.mixpa.qq514518274.config;

import lombok.Getter;
import org.junit.Test;

import java.io.File;

public class TestConfigLoader {
    @Getter
    final File CONFIG = new File("build/resources/test/test/config.yml");
    @Getter
    final File ROAD_CONFIG = new File("build/resources/test/test/road.yml");
    @Test
    public void testConfigLoader(){
    }
}
