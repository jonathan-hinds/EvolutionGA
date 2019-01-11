import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ChromosomeTest {

    @Test
    public void testCreateChromo(){
        ChromosomeUtil chromosomeUtil = new ChromosomeUtil();
        Double expect = 12.0;

        List<Double> chromosome = ChromosomeUtil.randomChromosome(expect);

        Assert.assertEquals(expect, ChromosomeUtil.sum(chromosome));
    }

    @Test
    public void testCreate100Chromo(){
        ChromosomeUtil chromosomeUtil = new ChromosomeUtil();
        Double expect = 12.0;

        for(int i = 0; i < 100; i ++){
            List<Double> chromosome = ChromosomeUtil.randomChromosome(expect);
            Assert.assertEquals(expect, ChromosomeUtil.sum(chromosome));
        }
    }

    @Test
    public void testShuffleSchema(){
        ChromosomeUtil chromosomeUtil = new ChromosomeUtil();
        Double expect = 12.0;
        //create a new chromosome
        List<Double> chromosome = ChromosomeUtil.randomChromosome(expect);
        //assert it has the proper total value
        Assert.assertEquals(expect, ChromosomeUtil.sum(chromosome));

        //shuffle the new chromsome into another new chromsome
        List<Double> chromosome2 = new ArrayList<>(chromosomeUtil.shuffleSchema(chromosome));
        //assert that the new chromsome has the proper sum also.
        Assert.assertEquals(expect, ChromosomeUtil.sum(chromosome2));
    }
}
