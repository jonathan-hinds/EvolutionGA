import java.util.ArrayList;
import java.util.List;

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

    public Stats(Double stamina, Double agility, Double crit, Double armour, Double attack) {
        this.stamina = stamina;
        this.agility = agility;
        this.crit = crit;
        this.attack = attack;
        this.armour = armour;
        this.damage = calculateDamage();
        this.speed = calculateSpeed();
        this.health = calculateHealth();
        this.maxHealth = this.health;
    }

    /**
     * @return calculates a random damage based on the minimum damage and the maximum damage.
     */
    public Double calculateDamage(){
        double minDamage = (this.attack * 2) - (this.armour * .25);
        double maxDamage = (this.attack * 2) - (this.armour * .25) + (this.crit * 2);
        return Math.ceil(Math.random() * maxDamage) + minDamage;
    }

    /**
     * @return calculate the speed based on the agility and the armouy. Base speed defaults to 2.0
     */
    public Double calculateSpeed(){
        return (-(1.5/(1+ Math.exp(.01 * (-10 * (this.agility) + 111)))) + 2.4) + (this.armour * .25);
    }

    /**
     * @return calculate the health, health is calculated by stamina * a constant of 8.
     */
    public Double calculateHealth(){
        return this.stamina * 8;
    }

    /**
     * @param amount amount to be added to the fitness score for these stats.
     * @return the newly calculated total fitness.
     */
    public Double increaseFitness(Double amount){
        this.fitness += amount;
        return this.fitness;
    }

    public Double getStamina() {
        return stamina;
    }

    public void setStamina(Double stamina) {
        this.stamina = stamina;
    }

    public Double getHealth() {
        return health;
    }

    public void setHealth(Double health) {
        this.health = health;
    }

    public Double getAgility() {
        return agility;
    }

    public void setAgility(Double agility) {
        this.agility = agility;
    }

    public Double getCrit() {
        return crit;
    }

    public void setCrit(Double crit) {
        this.crit = crit;
    }

    public Double getArmour() {
        return armour;
    }

    public void setArmour(Double armour) {
        this.armour = armour;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getAttack() {
        return attack;
    }

    public void setAttack(Double attack) {
        this.attack = attack;
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

    public Double getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(Double baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public Double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(Double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public List<Double> getChromosome() {
        return chromosome;
    }

    public void setChromosome(List<Double> chromosome) {
        this.chromosome = chromosome;
    }

    public Fitness getFitnessOBJ() {
        return fitnessOBJ;
    }

    public void setFitnessOBJ(Fitness fitnessOBJ) {
        this.fitnessOBJ = fitnessOBJ;
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
