package test.mixpa.qq514518274.config;

import mixpa.qq514518274.config.RoadConfig;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class TestRoadConfig {
    @Test
    public void TestConfig() throws FileNotFoundException, IllegalAccessException {
        File testFile = new File("build/resources/test/test/road.yml");
        new RoadConfig().load(testFile);
        new RoadConfig().load(new FileReader(testFile));
        System.out.println(RoadConfig.getFrame());
    }
}
