import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChromosomeUtil {

    private static Writer writer = new Writer();
    private static Population population = new Population();
    private static List<List<Double>> chromosomes = new ArrayList<>();
    private static Double totalAttributePoints = 12.0;

    /**
     * @param expect the number of atributes to be distributed to the chromosome. Each chromsome must be larger than 0.
     * @return
     */
    public static List<Double> randomChromosome(Double expect) {
        List<Double> chromosome = new ArrayList<>();
        Double limit = expect;
        int schemaLimit = 5;
        for(int i = 1; i <= schemaLimit -1; i ++){
            Double schema = Math.floor(Math.random() * (limit - (schemaLimit - i)) + 1);
            limit -= schema;
            chromosome.add(schema);
        }
        chromosome.add(limit);
        chromosome = shuffleSchema(chromosome);
        return chromosome;
    }

    /**
     * @param chromosome chromsome to be shuffled. This method is responsible for shuffling the order
     *                   of the schema in a single chromosome.
     * @return the new chromosome now that it's original has been shuffled.
     */
    public static List<Double> shuffleSchema(List<Double> chromosome){
        Collections.shuffle(chromosome);
        return chromosome;
    }

    /**
     * @param chromosome chromosome to have its values summed. Calculates the total of a choromosomes combined
     *                   schema.
     * @return the sum of all schema within a specified chromosome.
     */
    public static Double sum(List<Double> chromosome) {
        Double sum = 0.0;
        for(Double doub : chromosome){
            sum += doub;
        }
        return sum;
    }

    /**
     * creates a population of Enemies.
     * @param populationLimit - members of a population to be created.
     */
    public static void initiatePopulation(int populationLimit){
        for(int i = 0; i < populationLimit; i ++){
            List<Double> chromosome = randomChromosome(totalAttributePoints);
            population.add(new Enemy(chromosome));
            chromosomes.add(chromosome);
        }
    }

    /**
     * Print to the terminal, the fitness score for each Enemy in a population.
     */
    public static void printPopulationFitness(){
        for(Fighter fighter : population.getPopulation()){
            System.out.printf("%-10s Fitness Score: %6.2f\n", fighter.getName() + ",", fighter.getStats().getFitness());
            writer.write(String.format("%-10s Fitness Score: %6.2f\n", fighter.getName() + ",", fighter.getStats().getFitness()));
        }
    }

    /**
     * Print the chromosomes for each of the Enemies in the population.
     */
    public static void printChromosomes(){
        int counter = 0;
        for(List<Double> chomo : chromosomes){
            System.out.println(counter + ": " + chomo.toString());
            writer.write(counter + ": " + chomo.toString() + "\n");
            counter ++;
        }
        System.out.println("");
    }

    public static List<Fighter> getPopulationList() {
        return population.getPopulation();
    }

    public static void setPopulationList(List<Fighter> newPopulation) {
        population.setPopulation(newPopulation);
    }

    public static Double getTotalAttributePoints() {
        return totalAttributePoints;
    }

    public static void setTotalAttributePoints(Double totalAttributePoints) {
        ChromosomeUtil.totalAttributePoints = totalAttributePoints;
    }

    public static Population getPopulation() {
        return population;
    }

    public static void setPopulation(Population population) {
        ChromosomeUtil.population = population;
    }
}
