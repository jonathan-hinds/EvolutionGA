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
     *      todo - Reproduction
     *      todo - Eliminate Duplicates
     *      todo - 12:30AM 1/10/2019 - New Fitness Evaluation. See PDF
     *      todo - Calculate Fitness
     *
     * -    Methods Completed:
     *      DONE: - calculateRunningFitness
     *      DONE: - findParents
     *      DONE: - Crossover
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
     * todo - seems damage is overpowered, if an enemy dumps all of its stats into attack, it is more likely to have a high fitness evaluation.
     * todo - the above may change after implementing new fitness evaluation.
     */

    public static void main(String[] args) {

        //create the population
        ChromosomeUtil.setTotalAttributePoints(12.0);
        ChromosomeUtil.initiatePopulation(10);
        ChromosomeUtil.printChromosomes();

        //create the player
        Double[] playerSchema = {1.0, 1.0, 1.0, 7.0, 2.0};
        List<Double> playerChromo = new ArrayList<>(Arrays.asList(playerSchema));
        Fighter player = new Player(playerChromo);
        player.setName("Player");

        //battle to create fitness scores
        Battle battle = new Battle();

        int counter = 0;
        for(Fighter fighter : ChromosomeUtil.getPopulationList()){
            fighter.setName("Enemy " + counter);
            battle.battle(player, fighter);
            counter ++;
        }

        //print population fitness
        ChromosomeUtil.printPopulationFitness();
        ChromosomeUtil.getPopulation().calculateRunningFitness();

        //print population running fitness
        System.out.println("Running Fitness: " + ChromosomeUtil.getPopulation().getRunningFitness() + "\n");

        //print parents selected
        for(int i = 0; i < ChromosomeUtil.getPopulationList().size(); i ++){
            Fighter[] parents = ChromosomeUtil.getPopulation().getParents();
            System.out.printf("ParentA: %-10s | ParentB: %-10s\n", parents[0].getName(), parents[1].getName());
        }

    }
}
