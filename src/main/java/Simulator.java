import java.util.*;

public class Simulator {

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
     * todo - create a curve that increases damage as attack goes up and decreases damage as armor goes up. This should never be allowed below 0.
     *
     * -    Methods Completed:
     * DONE: - calculateRunningFitness
     * DONE: - findParents
     * DONE: - Crossover
     * DONE: - 12:30AM 1/10/2019 - New Fitness Evaluation. See PDF
     * DONE: - Reproduction
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
     * CHANGE:
     *
     *  todo - change SOUT's to print output to a text
     *      document, this way larger populations can run for
     *      more generations.
     *
     * REFACTOR:
     *  todo - refactor fighter into a fightable interface implemented on enemy
     *      player. Q: Should this be the case if they both implement the methods the same way?
     *
     *  todo - refactor Fitness into a scoreable interface which is implemented on the fitness class.
     *
     *  todo - refactor timedAttack into multiple methods. (follow single responsibility)
     */

    public static void main(String[] args) {
        Population population = new Population();
        // create the population.
        ChromosomeUtil.setTotalAttributePoints(16.0);
        ChromosomeUtil.initiatePopulation(10);
        ChromosomeUtil.printChromosomes();
        // create the player.
        Fighter player = createPlayer();
        // run the simulation.
        run(1, 10 , player);
    }

    public static void run(int generation, int endgeneration, Fighter environment){
        Population population = new Population();
        //for each generation, until all generations are scored
        for(int i = generation; i <= endgeneration; i ++) {
            System.out.println("GENERATION: " + i + "\n");
            //battle to create fitness scores
            simulte(environment);
            //print population fitness
            ChromosomeUtil.printPopulationFitness();
            ChromosomeUtil.getPopulation().calculateRunningFitness();
            //print population running fitness
            System.out.println("Running Fitness: " + ChromosomeUtil.getPopulation().getRunningFitness() + "\n");
            //print parents selected
            List<Fighter[]> parentList = printParents();
            System.out.println("");
            //create the children and store them in a list [childrenList].
            List<Fighter> childrenList = new ArrayList<>();
            for (Fighter[] parents : parentList) {
                Fighter[] childrenArray = population.crossOver(parents[0], parents[1]);
                childrenList.add(childrenArray[0]);
                childrenList.add(childrenArray[1]);
            }
            //print the chromosomes for each child in the new population.
            int counter = 0;
            for (Fighter fighter : childrenList) {
                System.out.println(counter + ": " + fighter.getStats().getChromosome().toString());
                counter ++;
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
        Double[] playerSchema = {2.0, 3.0, 2.0, 7.0, 2.0};
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
            } catch (Exception e) {
                System.out.println(Arrays.toString(parents));
            }
        }
        return parentList;
    }
}
