package test.mixpa.qq514518274.config;

import mixpa.qq514518274.config.RoadConfig;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class TestRoadConfig {
    @Test
    public void TestConfig() throws FileNotFoundException, IllegalAccessException {
        File testFile = new File("build/resources/test/test/road.yml");
        new RoadConfig(testFile);
        new RoadConfig(new FileReader(testFile));
        new RoadConfig(new FileInputStream(testFile));
        System.out.println(RoadConfig.getFrame());
    }
}
