package test.mixpa.qq514518274.config;

import mixpa.qq514518274.config.PlotConfig;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

public class TestPlotConfig {
    final File FILE = new File("out/production/resources/config.yml");

    @Test
    public void testConfig() throws IllegalAccessException, FileNotFoundException {
        long start = System.currentTimeMillis();
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testImp() throws FileNotFoundException, IllegalAccessException {
        new PlotConfig().load(FILE);
        System.out.println(PlotConfig.getResetMoney());
    }

    @Test
    public void testDemo() throws FileNotFoundException, IllegalAccessException {
        System.out.println(PlotConfig.getPlotConfig());
    }
}
