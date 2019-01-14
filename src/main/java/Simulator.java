import java.util.*;

public class Simulator {

    //Chromosome order//
    //stamina, agility, crit, attack, armour//

    /**
     * Notes to your future self regarding this project:
     * Remember the Goal:
     *
     * Speciman that are more fit, reproduce to make offspring. These
     * offspring have the chance of being more fit to defeat the player
     * in battle.
     *
     * -    Methods to consider:
     *      todo - Mutation
     *      todo - Eliminate Duplicates
     *
     * -    Methods Completed:
     *      DONE: - calculateRunningFitness
     *      DONE: - findParents
     *      DONE: - Crossover
     *      DONE: - 12:30AM 1/10/2019 - New Fitness Evaluation. See PDF
     *      DONE: - Reproduction
     *
     * After the above is done, and parents can be found:
     *
     * Create mutation operator
     * ABSTRACT: Reintroduce diversity into the population. Change
     * random attributes, by random amounts, at random intervals.
     *
     *  - Subtract x.x number of points from one trait, add these
     *      to another trait. The two traits chosen should be
     *      random.
     *
     * Create crossover operator
     * ABSTRACT: Schema that contribute to this speciman being fit
     * should have a higher chance of being mixed into the offspring.
     *
     *  - Create a fitness score for each stat, which is assigned
     *      Throughout battle. A fighters fitness should be a sum
     *      of these scores. After the parents are selected, The
     *      schema selected to be crossed, should be the more fit
     *      of each parrent.
     *      - If it does not fit into the child while meeting the
     *          criteria, subtract the difference / amount of
     *          elements from each attribute > this amount.
     *
     * Assign values (fitness score) to the two operators above
     *
     * Create reproduction method using the above described
     * operators.
     *
     * Consider eliminating duplicates from a population.
     * (elitsm)
     *
     * todo - damage can be negative, an enemy can heal player if its armour is too high.
     * todo - 3 generations: environment chromosome: {8.0, 3.0, 2.0, 2.0, 1.0} (sum = 16)
     *      todo - yields decreasing results in generation 2, only slighly increased results in generation 3.
     * todo - 3 generations: environemnt chromosome: {2.0, 3.0, 2.0, 7.0, 2.0} (sum = 16)
     *      todo - yields constantly increasing results. each generation increasing fitness by over 100, where new generations focus attribute points in attack.
     * todo - consider grading attack some other way. also run more tests on environment chromosome: {8.0, 3.0, 2.0, 2.0, 1.0} (sum = 16)
     */
    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        Population population = new Population();
        //create the population
        ChromosomeUtil.setTotalAttributePoints(16.0);
        ChromosomeUtil.initiatePopulation(10);
        ChromosomeUtil.printChromosomes();
        //create the player
        Fighter player = createPlayer();
        //battle to create fitness scores
        simulte(player);
        //print population fitness
        ChromosomeUtil.printPopulationFitness();
        ChromosomeUtil.getPopulation().calculateRunningFitness();
        //print population running fitness
        System.out.println("Running Fitness: " + ChromosomeUtil.getPopulation().getRunningFitness() + "\n");
        //print parents selected
        List<Fighter[]> parentList = printParents();

        System.out.println("\nGENERATION 2\n");

        //create the children and store them in a list [childrenList].
        List<Fighter> childrenList = new ArrayList<>();
        for(Fighter[] parents : parentList){
            Fighter[] childrenArray = population.crossOver(parents[0], parents[1]);
            childrenList.add(childrenArray[0]);
            childrenList.add(childrenArray[1]);
        }
        //print the chromosomes for each child in the new population.
        int counter = 0;
        for(Fighter fighter : childrenList){
            System.out.println(counter + ": " + fighter.getStats().getChromosome().toString());
        }
        System.out.println("");
        //set the new population to be the children of the initial population.
        ChromosomeUtil.setPopulationList(childrenList);
        //battle to create fitness scores.
        simulte(player);
        //print population fitness.
        ChromosomeUtil.printPopulationFitness();
        ChromosomeUtil.getPopulation().calculateRunningFitness();
        //print population running fitness.
        System.out.println("Running Fitness: " + ChromosomeUtil.getPopulation().getRunningFitness() + "\n");
        //print parents selected.
        List<Fighter[]> parentList2 = printParents();

        System.out.println("\nGENERATION 3\n");

        //create the children and store them in a list [childrenList].
        List<Fighter> childrenList2 = new ArrayList<>();
        for(Fighter[] parents : parentList2){
            Fighter[] childrenArray = population.crossOver(parents[0], parents[1]);
            childrenList2.add(childrenArray[0]);
            childrenList2.add(childrenArray[1]);
        }
        //print the chromosomes for each child in the new population.
        int counter2 = 0;
        for(Fighter fighter : childrenList2){
            System.out.println(counter2 + ": " + fighter.getStats().getChromosome().toString());
        }
        System.out.println("");
        //set the new population to be the children of the second generation.
        ChromosomeUtil.setPopulationList(childrenList2);
        //battle to create fitness scores.
        simulte(player);
        //print population fitness.
        ChromosomeUtil.printPopulationFitness();
        ChromosomeUtil.getPopulation().calculateRunningFitness();
        //print population running fitness.
        System.out.println("Running Fitness: " + ChromosomeUtil.getPopulation().getRunningFitness() + "\n");
        //print parents selected.
        List<Fighter[]> parentList3 = printParents();
    }

    public static void simulte(Fighter environment){
        Battle battle = new Battle();
        int counter = 0;
        for(Fighter fighter : ChromosomeUtil.getPopulationList()){
            fighter.setName("Enemy " + counter);
            battle.battle(environment, fighter);
            counter ++;
        }
    }

    public static Fighter createPlayer(){
        Double[] playerSchema = {2.0, 3.0, 2.0, 7.0, 2.0};
        List<Double> playerChromo = new ArrayList<>(Arrays.asList(playerSchema));
        Fighter player = new Player(playerChromo);
        player.setName("Player");
        return player;
    }

    public static List<Fighter[]> printParents(){
        List<Fighter[]> parentList = new ArrayList<>();
        for(int i = 0; i < ChromosomeUtil.getPopulationList().size() / 2; i ++){
            Fighter[] parents = ChromosomeUtil.getPopulation().getParents();
            parentList.add(parents);
            try {
                System.out.printf("Parent A: %-10s | Parent B: %-10s\n", parents[0].getName(), parents[1].getName());
            } catch (NullPointerException e) {
                System.out.println(Arrays.toString(parents));
            }
        }
        return parentList;
    }
}
