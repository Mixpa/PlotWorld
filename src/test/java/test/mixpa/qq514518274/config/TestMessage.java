package test.mixpa.qq514518274.config;

import mixpa.qq514518274.config.Message;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;

public class TestMessage {
    @Test
    public void testMessage() throws IllegalAccessException {
        new Message(new File("D:\\Users\\Mixpa\\IdeaProjects\\FactionPlotWorld\\src\\main\\resources\\message.yml"));
        for (String var:Message.getHelpMessage()){
            System.out.println(var);
        }
        System.out.println(Message.getPrefix());
        System.out.println(Message.getCantBuildInRoad());
        System.out.println(Message.getCantClaimInRoad());
    }
    @Test
    public void test() throws IllegalAccessException {
        new Message(new File("D:\\Users\\Mixpa\\IdeaProjects\\FactionPlotWorld\\src\\main\\resources\\message.yml"));

        for (Field field:Message.class.getDeclaredFields()){
            field.setAccessible(true);
            System.out.println(field);
            System.out.println(field.get(null));
        }
    }
    @Test
    public void testNoMine() throws IllegalAccessException {
        new Message(new File("D:\\Users\\Mixpa\\IdeaProjects\\FactionPlotWorld\\src\\main\\resources\\message.yml"));
        System.out.println(Message.getNoMine().replaceAll("\\{mine}", "hahahah"));
    }
}
