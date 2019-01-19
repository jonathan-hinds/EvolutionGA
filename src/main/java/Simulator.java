import java.util.*;

public class Simulator {

    private static Writer writer = new Writer();

    /**
     * CHROMOSOME ORDER:
     * stamina, agility, crit, attack, armour
     *
     * Notes to your future self regarding this project:
     * Remember the Goal:
     *
     * Speciman that are more fit, reproduce to make offspring. These
     * offspring have the chance of being more fit to defeat the player
     * in battle.
     *
     * -    Methods to consider:
     * todo - Mutation
     * todo - Eliminate Duplicates
     * todo - Windowing -  measure an individuals fitness score, based on how much it has surpassed the fitness of the lowest in the population.
     *
     * -    Methods Completed:
     * DONE: - calculateRunningFitness
     * DONE: - findParents
     * DONE: - Crossover
     * DONE: - 12:30AM 1/10/2019 - New Fitness Evaluation. See PDF
     * DONE: - Reproduction
     * DONE: - SOUT's printed to file.
     *
     * After the above is done, and parents can be found:
     *
     * Create mutation operator
     * ABSTRACT: Reintroduce diversity into the population. Change
     * random attributes, by random amounts, at random intervals.
     *
     * - Subtract x.x number of points from one trait, add these
     * to another trait. The two traits chosen should be
     * random.
     *
     * Create crossover operator
     * ABSTRACT: Schema that contribute to this specimen being fit
     * should have a higher chance of being mixed into the offspring.
     *
     * - Create a fitness score for each stat, which is assigned
     * Throughout battle. A fighters fitness should be a sum
     * of these scores. After the parents are selected, The
     * schema selected to be crossed, should be the more fit
     * of each parrent.
     * - If it does not fit into the child while meeting the
     * criteria, subtract the difference / amount of
     * elements from each attribute > this amount.
     *
     * Assign values (fitness score) to the two operators above
     *
     * Create reproduction method using the above described
     * operators.
     *
     * Consider eliminating duplicates from a population.
     * (elitsm)
     *
     */

    public static void main(String[] args) {
        Population population = new Population();
        // create the population.
        ChromosomeUtil.setTotalAttributePoints(50.0);
        ChromosomeUtil.initiatePopulation(100);
        // create the player.
        Fighter player = createPlayer();
        // run the simulation.
        run(1, 40 , player);
    }

    public static void run(int generation, int endgeneration, Fighter environment){
        Population population = new Population();
        //for each generation, until all generations are scored
        for(int i = generation; i <= endgeneration; i ++) {
            System.out.println("GENERATION: " + i + "\n");
            writer.write("GENERATION: " + i + "\n\n");
            // print the chromosomes for each member of the population.
            int counter = 0;
            for (Fighter fighter : ChromosomeUtil.getPopulationList()) {
                System.out.println(counter + ": " + fighter.getStats().getChromosome().toString());
                writer.write(counter + ": " + fighter.getStats().getChromosome().toString() + "\n");
                counter ++;
            }
            writer.write("\n");
            //battle to create fitness scores
            simulte(environment);
            //print population fitness
            ChromosomeUtil.printPopulationFitness();
            ChromosomeUtil.getPopulation().calculateRunningFitness();
            //print population running fitness
            System.out.println("Running Fitness: " + ChromosomeUtil.getPopulation().getRunningFitness() + "\n");
            writer.write("\nRunning Fitness: " + ChromosomeUtil.getPopulation().getRunningFitness() + "\n\n");
            //print parents selected
            List<Fighter[]> parentList = printParents();
            System.out.println("");
            writer.write("\n");
            //create the children and store them in a list [childrenList].
            List<Fighter> childrenList = new ArrayList<>();
            for (Fighter[] parents : parentList) {
                Fighter[] childrenArray = population.crossOver(parents[0], parents[1]);
                childrenList.add(childrenArray[0]);
                childrenList.add(childrenArray[1]);
            }
            //set the new population to be the children of the initial population.
            ChromosomeUtil.setPopulationList(childrenList);
        }
    }

    public static void simulte(Fighter environment) {
        Battle battle = new Battle();
        int counter = 0;
        for (Fighter fighter : ChromosomeUtil.getPopulationList()) {
            fighter.setName("Enemy " + counter);
            battle.battle(environment, fighter);
            counter++;
        }
    }

    public static Fighter createPlayer() {
        Double[] playerSchema = {14.0, 10.0, 5.0, 13.0, 8.0};
        List<Double> playerChromo = new ArrayList<>(Arrays.asList(playerSchema));
        Fighter player = new Player(playerChromo);
        player.setName("Player");
        return player;
    }

    public static List<Fighter[]> printParents() {
        List<Fighter[]> parentList = new ArrayList<>();
        for (int i = 0; i < ChromosomeUtil.getPopulationList().size() / 2; i++) {
            Fighter[] parents = ChromosomeUtil.getPopulation().getParents();
            parentList.add(parents);
            try {
                System.out.printf("Parent A: %-10s | Parent B: %-10s\n", parents[0].getName(), parents[1].getName());
                writer.write(String.format("Parent A: %-10s | Parent B: %-10s\n", parents[0].getName(), parents[1].getName()));
            } catch (Exception e) {
                System.out.println(Arrays.toString(parents));
            }
        }
        return parentList;
    }
}
