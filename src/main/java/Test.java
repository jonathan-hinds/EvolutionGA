import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        Population population = new Population();
        List<Double> chromosome = new ArrayList<>();
        chromosome.add(19.666666666666668);
        chromosome.add(3.0);
        chromosome.add(32.666666666666664);
        chromosome.add(1.0);
        chromosome.add(1.0);
        population.adjustChromosome(chromosome);
    }
}
