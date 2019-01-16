import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class Fighter {

    private int level = 1;
    private Double exp = 0.0;
    private Double maxExp = level * 100.0;
    private Stats stats;
    private String name = "";

    public Fighter(Stats stats) {
        this.stats = stats;
    }

    public Fighter(List<Double> chromosome) {
        this.stats = new Stats(chromosome);
    }

    /**
     * increase level of fighter by 1.
     */
    public void levelUp(){
        this.level += 1;
    }

    /**
     * @return if the current fighter is alive or not
     */
    public boolean isAlive(){
        return this.stats.getHealth() > 0;
    }

    /**
     * @param target that target that is to be attacked by this fighter.
     * @return the amount of damage that was dealt to this fighters target.
     */
    public Double attack(Fighter target){
        Double targetArmour = target.stats.getArmour();
        Double targetHealth = target.stats.getHealth();
        Double currentDamage = this.stats.calculateDamage() - (targetArmour * .25);
        this.stats.setDamage(currentDamage);
        if(isAlive()) {
            target.getStats().setHealth(targetHealth - currentDamage);
        }
        return currentDamage;

    }

    /**
     * This method is used in a TimerTask inside a Timer to execute attacks in regular
     * intervals based on the speed of the fighter inflicting the attacks.
     *
     * @param task - the task that this method is required to be called in.
     * @param timer - the timer that the task for this method is passed into.
     * @param fighter - the fighter that will be executing the attacks in timed intervals.
     * @param target - the target that will be attacked by the fighter whom is attacking.
     */
    @SuppressWarnings("Duplicates")
    public void attackInTime(TimerTask task, Timer timer, Fighter fighter, Fighter target, CountDownLatch latch){
        if(target.isAlive()) {
            if(!fighter.isAlive()){
                if(target instanceof Enemy) {
                    this.getStats().setHealth(this.getStats().getMaxHealth());
                    //set target fitness scores once the battle is over.
                    target.getStats().getFitnessOBJ().setStaminaFit(target.getStats().getHealth() / target.getStats().getMaxHealth());
                    target.getStats().getFitnessOBJ().setAttackFit(target.getStats().getFitnessOBJ().getAttackFit() / target.getStats().getFitnessOBJ().getAgilityFit());
                    target.getStats().getFitnessOBJ().setCriticalFit(target.getStats().getFitnessOBJ().getMaxDamage());
                    target.getStats().getFitnessOBJ().getTotalFit();
                    target.getStats().getFitnessOBJ().setTotalFit();
                    target.getStats().setFitness(target.getStats().getFitnessOBJ().returnTotalFit());
                    System.out.println("\n" + target.getName() + " Fitness Score: " + target.getStats().getFitness());
                    System.out.println(target.getStats().getFitnessOBJ().toString());

                }
                task.cancel();
                timer.cancel();
                latch.countDown();
            } else {
                Double damageDealt = attack(target);
                if(this instanceof Enemy){
                    //set target fitness scores once the battle is over.
                    this.getStats().getFitnessOBJ().increaseAttackFittness(damageDealt);
                    this.getStats().getFitnessOBJ().increaseAgilityFitness();
                    this.getStats().getFitnessOBJ().increaseMaxDamage(damageDealt);
                } else if(this instanceof Player) {
                    target.getStats().getFitnessOBJ().increaseArmourFitness(target.getStats().getArmour() * .25);
                }
                System.out.printf("%-12s has dealt %6.2f, %-12s HP: %6.2f\n", fighter.getName(), fighter.getStats().getDamage(), target.name, target.getStats().getHealth());
            }
            if(!target.isAlive()){
                System.out.println("OUTCOME: " + target.getName() + " has been defeated, HP: " + target.getStats().getHealth());
                if(target instanceof Enemy) {
                    this.getStats().setHealth(this.getStats().getMaxHealth());
                    //set target fitness scores once the battle is over.
                    target.getStats().getFitnessOBJ().setStaminaFit(target.getStats().getHealth() / target.getStats().getMaxHealth());
                    target.getStats().getFitnessOBJ().setAttackFit(target.getStats().getFitnessOBJ().getAttackFit() / target.getStats().getFitnessOBJ().getAgilityFit());
                    target.getStats().getFitnessOBJ().setCriticalFit(target.getStats().getFitnessOBJ().getMaxDamage());
                    target.getStats().getFitnessOBJ().getTotalFit();
                    target.getStats().getFitnessOBJ().setTotalFit();
                    target.getStats().setFitness(target.getStats().getFitnessOBJ().returnTotalFit());
                    System.out.println("\n" + target.getName() + " Fitness Score: " + target.getStats().getFitness());
                    System.out.println(target.getStats().getFitnessOBJ().toString());
                }
                task.cancel();
                timer.cancel();
                latch.countDown();
            }
        }
    }

    /**
     * @param target target to be attacked after the amount of time in milliseconds,
     *               that the speed stat equates to.
     */
    public void timedAttack(Fighter target, CountDownLatch latch) {
        Fighter fighter = this;
        printStartHP(fighter);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() { attackInTime(this, timer, fighter, target, latch); }
        };
        long speed = Math.round(getStats().getSpeed() * 1000);
        timer.scheduleAtFixedRate(task, speed, speed);
    }

    /**
     * @param fighter the fighter who's starting health will be printed to the terminal
     */
    public void printStartHP(Fighter fighter){
        System.out.printf("%-10s has %6.2f HP.\n", fighter.getName(), fighter.getStats().getHealth());
    }

    /**
     * @param expAmount amount of expiereince to increase the current fighters total expierence by.
     */
    public void addExp(Double expAmount){
        if(exp + expAmount < maxExp){
            exp += expAmount;
        } else {
            exp = (exp + expAmount) - maxExp;
            levelUp();
        }
    }

    public Stats getStats(){
        return this.stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Fighter{" +
                "level=" + level +
                ", exp=" + exp +
                ", maxExp=" + maxExp +
                ", stats=" + stats +
                ", name='" + name + '\'' +
                '}';
    }
}
