package test.mixpa.qq514518274.config;

import mixpa.qq514518274.config.Message;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;

public class TestMessage {
    final static File FILE = new File("out/production/resources/message.yml");
    @Test
    public void testMessage() throws IllegalAccessException, FileNotFoundException {
        new Message(FILE);
        for (String var:Message.getHelpMessage()){
            System.out.println(var);
        }
        System.out.println(Message.getPrefix());
        System.out.println(Message.getCantBuildInRoad());
        System.out.println(Message.getCantClaimInRoad());
    }
    @Test
    public void test() throws IllegalAccessException, FileNotFoundException {
        new Message(new File("D:\\Users\\Mixpa\\IdeaProjects\\FactionPlotWorld\\src\\main\\resources\\message.yml"));

        for (Field field:Message.class.getDeclaredFields()){
            field.setAccessible(true);
            System.out.println(field);
            System.out.println(field.get(null));
        }
    }
    @Test
    public void testCoolDowns() throws FileNotFoundException, IllegalAccessException {
        new Message(FILE);
        System.out.println(Message.getWaitCoolDowns(60*60*61));
    }
    @Test
    public void testNoMine() throws IllegalAccessException, FileNotFoundException {
        new Message(new File("D:\\Users\\Mixpa\\IdeaProjects\\FactionPlotWorld\\src\\main\\resources\\message.yml"));
        System.out.println(Message.getNoMine().replaceAll("\\{mine}", "hahahah"));
    }
}
