package test.mixpa.qq514518274.config;

import mixpa.qq514518274.config.PlotConfig;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

public class TestPlotConfig {
    @Test
    public void testConfig() throws IllegalAccessException, FileNotFoundException {
        long start = System.currentTimeMillis();
        new PlotConfig(new File("out/production/resources/config.yml"));
        System.out.println(System.currentTimeMillis()-start);
    }
    @Test
    public void testImp() {
    }
}
