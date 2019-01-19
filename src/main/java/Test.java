import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        for(int i = 0; i < 100; i ++) {
            Random random = new Random();
            int r = random.nextInt(38 - 26 + 1) + 26;
            System.out.println(r);
        }
    }
}
