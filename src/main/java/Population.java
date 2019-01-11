import java.util.ArrayList;
import java.util.List;

public class Population {

    private List<Fighter> population = new ArrayList<>();
    private Double runningFitness = 0.0;

    public Population() {
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

    public void setRunningFitness(Double runningFitness) {
        this.runningFitness = runningFitness;
    }
}
