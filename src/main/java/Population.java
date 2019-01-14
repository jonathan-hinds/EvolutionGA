import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Population {

    private List<Fighter> population = new ArrayList<>();
    private Double runningFitness = 0.0;

    public Population() {
    }

    /**
     * @param parent1 - Parent1 to take place in have its chromosomes crossed with another (parent2)
     *                to produce two offspring.
     * @param paren2 - Parent2 to take place in have its chromosomes crossed with another (parent1)
     *               to produce two offspring.
     * @return the two children produce from corssing over the chromsomes from both parents.
     */
    public Fighter[] crossOver(Fighter parent1, Fighter paren2){
        Fighter[] children = new Fighter[2];
        List<Double> chromosome1 = parent1.getStats().getChromosome();
        List<Double> chromosome2 = paren2.getStats().getChromosome();
        int crosspoint = (int)((Math.random() * 4));
        List<List<Double>> parent1sec = getSectionsOfChromosome(chromosome1, crosspoint);
        List<List<Double>> parent2sec = getSectionsOfChromosome(chromosome2, crosspoint);
        List<List<Double>> childrenChromosomes = getChildrenChromosomes(parent1sec, parent2sec);
        children[0] = new Enemy(childrenChromosomes.get(0));
        children[1] = new Enemy(childrenChromosomes.get(1));
        return children;
    }

    /**
     * @param parent1sec both spliced sections of parent1.
     * @param parent2sec both spliced sections of parent2.
     * @return a list with the children created from both parent1 and parent2.
     */
    public List<List<Double>> getChildrenChromosomes(List<List<Double>> parent1sec, List<List<Double>> parent2sec){
        List<List<Double>> childChromosomes = new ArrayList<>();
        List<Double> child1chromo = produceChild1(parent1sec, parent2sec);
        List<Double> child2chromo = produceChild2(parent1sec, parent2sec);
        childChromosomes.add(child1chromo);
        childChromosomes.add(child2chromo);
        return childChromosomes;
    }

    /**
     * @param parent1sec Sections of parent1's chromsomes after being spliced.
     * @param parent2sec Sections of parent2's chromsomes after being spliced.
     * @return Child1 created from the crossedover sections of parent1 and parent2 - adjusted to have
     * its sum equal to the total attribute points.
     * Difference between this and Child2 is that it use's alternate sections from both parents.
     */
    public List<Double> produceChild1(List<List<Double>> parent1sec, List<List<Double>> parent2sec){
        List<Double> child1chromo = new ArrayList<>();
        child1chromo.addAll(parent1sec.get(0));
        child1chromo.addAll(parent2sec.get(1));
        return adjustChromosome(child1chromo);
    }

    /**
     * @param parent1sec Sections of parent1's chromsomes after being spliced.
     * @param parent2sec Sections of parent2's chromsomes after being spliced.
     * @return Child2 created from the crossedover sections of parent1 and parent2 - adjusted to have
     * its sum equal to the total attribute points.
     * Difference between this and Child1 is that it use's alternate sections from both parents.
     */
    public List<Double> produceChild2(List<List<Double>> parent1sec, List<List<Double>> parent2sec){
        List<Double> child2chromo = new ArrayList<>();
        child2chromo.addAll(parent2sec.get(0));
        child2chromo.addAll(parent1sec.get(1));
        return adjustChromosome(child2chromo);
    }

    /**
     * todo - move to chromosome util.
     * @param chromosome Chromsome to be adjusted to fit the total attribute points of a population.
     * @return the chromosome after it has been adjusted.
     */
    public List<Double> adjustChromosome(List<Double> chromosome){
        List<Double> adjustedChromsome = new ArrayList<>();
        if(ChromosomeUtil.sum(chromosome) < ChromosomeUtil.getTotalAttributePoints()){
            adjustedChromsome = adjustChromsomeSmall(chromosome);
        } else if (ChromosomeUtil.sum(chromosome) > ChromosomeUtil.getTotalAttributePoints()){
            adjustedChromsome = adjustChromsomeLarge(chromosome);
        } else {
            return chromosome;
        }
        return adjustedChromsome;
    }

    /**
     * todo - move to chromosome util.
     * @param chromosome Chromsome to be adjusted if its sum exceeds the total amount of allowed
     *                   attribute points.
     * @return a new chromosome with its elements adjusted to the total amount of attribute points.
     */
    public List<Double> adjustChromsomeLarge(List<Double> chromosome){
        List<Double> adjustedChromo = new ArrayList<>();
        if(ChromosomeUtil.sum(chromosome) > ChromosomeUtil.getTotalAttributePoints()){
            Double chromoSum = ChromosomeUtil.sum(chromosome);
            Double difference = chromoSum - ChromosomeUtil.getTotalAttributePoints();
            Double adjustment = difference / findCountGreaterThan(difference, chromosome);
            for(Double doub : chromosome){
                if (doub > 1.0 && doub > difference){
                    doub -= adjustment;
                    adjustedChromo.add(doub);
                } else {
                    adjustedChromo.add(doub);
                }
            }
        }
        return adjustedChromo;
    }

    /**
     * todo - move to chromosome util.
     * @param chromosome Chromsome to be adjusted if its sum is under the total amount of allowed
     *                   attribute points.
     * @return a new chromosome with its elements adjusted to fit the total amount of attribute points.
     */
    public List<Double> adjustChromsomeSmall(List<Double> chromosome){
        List<Double> adjustedChromo = new ArrayList<>();
        if(ChromosomeUtil.sum(chromosome) < ChromosomeUtil.getTotalAttributePoints()){
            Double chromoSum = ChromosomeUtil.sum(chromosome);
            Double difference = ChromosomeUtil.getTotalAttributePoints() - chromoSum;
            Double adjustment = difference / 5;
            for(Double schema : chromosome){
                schema += adjustment;
                adjustedChromo.add(schema);
            }
        }
        return adjustedChromo;
    }

    /**
     * todo - move to chromosome util.
     * @param greaterThan value to compare each schema to, if the schema is greater that this
     *                    number, it is included in the count for the returned result.
     * @param chromsome chromosome to get the count of schema which are greater than a specified number.
     * @return number of schema which are greater than a specific amount and 1.
     */
    private Double findCountGreaterThan(Double greaterThan, List<Double> chromsome){
        Double count = 0.0;
        for(Double doub : chromsome){
            if(doub > 1.0 && doub > greaterThan){
                count ++;
            }
        }
        return count;
    }


    /**
     * todo - move to chromosome util.
     * @param chromosome Chromosome to be split.
     * @param crosspoint Point to split the chromosome at.
     * @return a List with thw teo sections of the chromosome after it's been split.
     */
    public List<List<Double>> getSectionsOfChromosome(List<Double> chromosome, int crosspoint){
        List<List<Double>> results = new ArrayList<>();
        Double[] chromoArray = new Double[chromosome.size()];
        chromoArray = chromosome.toArray(chromoArray);
        Double[] firstSection = Arrays.copyOfRange(chromoArray, 0, crosspoint);
        Double[] secondSection = Arrays.copyOfRange(chromoArray, crosspoint, chromoArray.length);
        results.add(Arrays.asList(firstSection));
        results.add(Arrays.asList(secondSection));
        return results;
    }

    /**
     * @param fighter to be added to a population.
     */
    public void add(Fighter fighter){
        population.add(fighter);
    }

    /**
     * @return the total fitness across an entire population.
     */
    public double calculateRunningFitness(){
        double running_fitness = 0.0;
        for(Fighter fighter : population){
            running_fitness += fighter.getStats().getFitness();
        }
        this.runningFitness = running_fitness;
        return running_fitness;
    }

    /**
     * @return ParentA and ParentB that will be used in the reproduction process.
     */
    public Fighter[] getParents(){
        Fighter[] parents = new Fighter[2];
        parents[0] = getParent();
        parents[1] = getParent(parents[0]);
        return parents;
    }

    /**
     * @param parentA Parent A that has already been selected to be used in the reproduction process.
     * @return another fighter (parent B) that cannot be the same as parent A.
     * todo - get someones input on if this is the best way to get a different parent.
     */
    public Fighter getParent(Fighter parentA){
        Fighter parentB = getParent();
        while(parentA == (parentB)){
            parentB = getParent();
        }
        return parentB;
    }

    /**
     * @return a fighter to be selected as a parent using rhoulette wheel selection
     */
    public Fighter getParent(){
        double fitness = calculateRunningFitness();
        double random = Math.round(Math.random() * fitness) + 1;
        for(Fighter fighter: population){
            random -= fighter.getStats().getFitness();
            if(random <= 0){
                return fighter;
            }
        }
        return null;
    }

    public List<Fighter> getPopulation() {
        return population;
    }

    public void setPopulation(List<Fighter> population) {
        this.population = population;
    }

    public Double getRunningFitness() {
        return runningFitness;
    }
}
