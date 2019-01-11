import org.junit.Test;

public class TestSpeed {

    @Test
    public void testSpeed(){
        Enemy enemy = new Enemy(ChromosomeUtil.randomChromosome(12.0));
        enemy.setName("1");
        Enemy enemy2 = new Enemy(ChromosomeUtil.randomChromosome(12.0));
        enemy2.setName("2");

    }

    public static void main(String[] args) {
        Enemy enemy = new Enemy(ChromosomeUtil.randomChromosome(12.0));
        System.out.println(enemy.getStats().getSpeed());
        enemy.setName("1");
        Enemy enemy2 = new Enemy(ChromosomeUtil.randomChromosome(12.0));
        enemy2.setName("2");
    }

}
