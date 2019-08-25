package test.mixpa.qq514518274;

import org.junit.Test;


public class Main {
    public static void main(String[] args) {

    }

    @Test
    public void test() {
        System.out.println(this.getClass().getClassLoader().getResource("test.yml"));
    }
}
