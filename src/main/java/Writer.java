import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    public Writer() {
    }

    public void write(String text){
        try {
            FileWriter writer = new FileWriter("/Users/elliottstansbury/Desktop/Projects/EvolutionGA/src/main/resources/Results.txt", true);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
