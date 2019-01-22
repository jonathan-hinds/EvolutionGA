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
     * -    Observations:
     *      -   An enemy lost a battle with a fitness score of 0.0 in stamina because it was killed
     *      in one attack. In this case, this is quonincdence that stamina fitness score equals 0.0
     *      but ultaimtly todo - the amount of attacks taken need to be considered in regards to
     *                     the fitness score of stamina.
     *
 *                     todo endurance = number of attacks taken / number of attacks required to kill
     *                     based on average damage taken.
     *
     *           -   An enemy was able to defeat the player, who had a larger attack range, it did so
     *           by withstanding more attack due to a higher stamina. It's fitness score reflected
     *           0.88 when it deserved much higher for doing this.
     *
     *           Here is an example where the enemy did not get a high fitness score when deserved.
     *
     *           STARTING HEALTH:
                 * Player     has  72.00 HP.
                 * Enemy 2    has 272.00 HP.
                 *
                 * SIMULATION:
                 * Enemy 2      has dealt   8.30, Player       HP:  63.70
                 * Player       has dealt  32.44, Enemy 2      HP: 239.56
                 * Enemy 2      has dealt   7.69, Player       HP:  56.01
                 * Enemy 2      has dealt   6.14, Player       HP:  49.87
                 * Player       has dealt  31.75, Enemy 2      HP: 207.81
                 * Enemy 2      has dealt   6.33, Player       HP:  43.54
                 * Player       has dealt  31.96, Enemy 2      HP: 175.85
                 * Enemy 2      has dealt   9.95, Player       HP:  33.59
                 * Enemy 2      has dealt   7.27, Player       HP:  26.32
                 * Player       has dealt  28.22, Enemy 2      HP: 147.63
                 * Enemy 2      has dealt   8.06, Player       HP:  18.26
                 * Enemy 2      has dealt   6.28, Player       HP:  11.98
                 * Player       has dealt  26.91, Enemy 2      HP: 120.72
                 * Enemy 2      has dealt   6.99, Player       HP:   5.00
                 * Player       has dealt  31.37, Enemy 2      HP:  89.35
                 * Enemy 2      has dealt   9.57, Player       HP:  -4.58
                 *
                 * RESULTS:
                 * OUTCOME: Player has been defeated, HP: -4.576873987157874
                 * Fitness{staminaFit=0.3284811500227394, agilityFit=0.2853311267726196, criticalFit=0.1382069478281838, attackFit=0.10635676942660817, armorFit=0.02463686261021422, totalFit=0.8830128566603653}
                 * Fighting is done
     *
     *      -   It seems that as evolution continues, all members of the population increase their
     *      attack to more quickly kill thier opponent. This means the most fit will almost always
     *      be the member with the highest attack. Some tests should be run to avoid this outcome.
     *
     *      attack has been changed to a range of (attack - agility) to (attack + crit * 2) - agility
     *      speed has been changed to increase by 10% of attack.
     *
     *      because of this, a check has been put into place under the attack method to check that
     *      damage is > 0, otherwise it defaults to 1.
     *
     *      CONSIDER: Maybe attack and agility should interchangably calculate damage. Which ever
     *      is higher, should have the lower subtracted from it. This could prevent enemies from
     *      always dealing 1 damage if they default for every attack
     *
     *      expectations:
     *          I expect that instead of dumping all attributes into attack, they will be dumped
     *          into crit since crit determines the max damage dealt, and the population members
     *          are currently trying to achieve maximum damage dealt in the shortest amount of
     *          time.
     *
     *              My expectations above were correct. Later generations focus their attributes
     *              in critical now instead of attack, although they are spread around more they
     *              are still not quite even - not that in all environments they should be.
     *
     *          it's also possible that this could spread the points more evenly between crit and
     *          agility since attack damage is now reliant more so on all three, as opposed to mostly
     *          on attack, and somewhat on crit.
     *
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
     * Create mutation operator
     * ABSTRACT: Reintroduce diversity into the population. Change
     * random attributes, by random amounts, at random intervals.
     *
     * Consider eliminating duplicates from a population.
     * (elitsm)
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
        Double[] playerSchema = {9.0, 24.0, 11.0, 2.0, 4.0};
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
