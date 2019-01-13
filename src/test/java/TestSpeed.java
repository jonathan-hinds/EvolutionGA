import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestSpeed {

    @Test
    public void testSpeed(){
        Enemy enemy = new Enemy(ChromosomeUtil.randomChromosome(12.0));
        enemy.setName("1");
        Enemy enemy2 = new Enemy(ChromosomeUtil.randomChromosome(12.0));
        enemy2.setName("2");

    }

    @Test
    public void testcrossover(){
        Population population = new Population();
        ChromosomeUtil.setTotalAttributePoints(12.0);
        for(int i = 0; i < 100; i ++) {
            Enemy enemy = new Enemy(ChromosomeUtil.randomChromosome(12.0));
            //System.out.println(enemy.getStats().getChromosome().toString());
            Enemy enemy2 = new Enemy(ChromosomeUtil.randomChromosome(12.0));
            //System.out.println(enemy2.getStats().getChromosome().toString() + "\n");
            population.crossOver(enemy, enemy2);
        }
    }

    @Test
    public void testAdjustment(){
        Population population = new Population();
        Double[] schema = {1.0, 1.0, 1.0, 9.0, 2.0};
        List<Double> chromosome = new ArrayList<>(Arrays.asList(schema));
        //Enemy enemy = new Enemy(chromosome);

        List<Double> adjustChromsome = population.adjustChromsomeLarge(chromosome);
        Double sum = ChromosomeUtil.sum(adjustChromsome);
        System.out.println(adjustChromsome.toString());
        System.out.println(sum);
    }

    @Test
    public void testAdjustmentSmall(){
        Population population = new Population();
        Double[] schema = {1.0, 1.0, 1.0, 5.0, 2.0};
        List<Double> chromosome = new ArrayList<>(Arrays.asList(schema));
        //Enemy enemy = new Enemy(chromosome);

        List<Double> adjustChromsome = population.adjustChromsomeSmall(chromosome);
        Double sum = ChromosomeUtil.sum(adjustChromsome);
        System.out.println(adjustChromsome.toString());
        System.out.println(sum);
    }



}
