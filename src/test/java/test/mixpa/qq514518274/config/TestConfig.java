package test.mixpa.qq514518274.config;

import mixpa.qq514518274.config.Config;
import org.junit.Test;

import java.io.File;

public class TestConfig {
    @Test
    public void testConfig() throws IllegalAccessException {
        Config config = new Config(new File("D:\\Users\\Mixpa\\IdeaProjects\\FactionPlotWorld\\src\\main\\resources\\config.yml"));
        System.out.println(Config.getPlotConfig().toString());
    }
}
