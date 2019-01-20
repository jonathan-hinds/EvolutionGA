import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Stats {

    private List<Double> chromosome = new ArrayList<>();

    private Fitness fitnessOBJ = new Fitness();
    private Double fitness = 0.0;

    //base stats
    private Double stamina;
    private Double agility;
    private Double crit;
    private Double armour;
    private Double attack;

    //calculated stats
    private Double speed;
    private Double health;
    private Double baseSpeed = 2.0;
    private Double damage;
    private Double maxHealth;

    public Stats(List<Double> chromosome) {
        this.chromosome = chromosome;
        this.stamina = chromosome.get(0);
        this.agility = chromosome.get(1);
        this.crit = chromosome.get(2);
        this.attack = chromosome.get(3);
        this.armour = chromosome.get(4);
        this.damage = calculateDamage();
        this.speed = calculateSpeed();
        this.health = calculateHealth();
        this.maxHealth = this.health;
    }

    public Stats() {
    }

    /**
     * @return calculates a random damage based on the minimum damage and the maximum damage.
     */
    public Double calculateDamage(){
        double minDamage = this.attack - this.agility;
        double maxDamage = ((this.attack) + (this.crit * 2)) - this.agility;
        Random random = new Random();
        double damage =  minDamage + (maxDamage - minDamage) * random.nextDouble();
        if(damage <= 0){
            damage = 1;
        }
        return damage;
    }

    /**
     * @return calculate the speed based on the agility and the armouy. Base speed defaults to 2.0
     */
    public Double calculateSpeed(){
        return (-(1.5/(1+ Math.exp(.01 * (-10 * (this.agility) + 111)))) + 2.4) + ((this.attack * .10) + (this.armour * .05));
    }

    /**
     * @return calculate the health, health is calculated by stamina * a constant of 8.
     */
    public Double calculateHealth(){
        return this.stamina * 8;
    }

    public Double getHealth() {
        return health;
    }

    public void setHealth(Double health) {
        this.health = health;
    }

    public Double getArmour() {
        return armour;
    }

    public Double getSpeed() {
        return speed;
    }

    public Double getDamage() {
        return damage;
    }

    public void setDamage(Double damage) {
        this.damage = damage;
    }

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public Double getMaxHealth() {
        return maxHealth;
    }

    public List<Double> getChromosome() {
        return chromosome;
    }

    public Fitness getFitnessOBJ() {
        return fitnessOBJ;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "chromosome=" + chromosome +
                ", fitnessOBJ=" + fitnessOBJ +
                ", fitness=" + fitness +
                ", stamina=" + stamina +
                ", agility=" + agility +
                ", crit=" + crit +
                ", armour=" + armour +
                ", attack=" + attack +
                ", speed=" + speed +
                ", health=" + health +
                ", baseSpeed=" + baseSpeed +
                ", damage=" + damage +
                ", maxHealth=" + maxHealth +
                '}';
    }
}
