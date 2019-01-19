import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;

import static java.time.temporal.ChronoUnit.MILLIS;

public class Battle {

    Writer writer = new Writer();

    public Battle() {
    }

    /**
     * @param fighter fighter who be participating in the battle.
     * @param target target of fighter who will be participating as well.
     */
    public void battle(Fighter fighter, Fighter target){

        CountDownLatch latch = new CountDownLatch(2);

        LocalTime time = java.time.LocalTime.now();

        fighter.timedAttack(target, latch);
        target.timedAttack(fighter, latch);

        try {
            latch.await();
            LocalTime time2 = java.time.LocalTime.now();
            Double timeSpent = time.until(time2, MILLIS) / 1000.0;

            //set fitness scores
            target.getStats().getFitnessOBJ().setStaminaFit(target.getStats().getHealth() / target.getStats().getMaxHealth());
            target.getStats().getFitnessOBJ().setAttackFit((target.getStats().getFitnessOBJ().getAttackFit() / target.getStats().getFitnessOBJ().getAgilityFit())/fighter.getStats().getMaxHealth());
            target.getStats().getFitnessOBJ().setCriticalFit(target.getStats().getFitnessOBJ().getMaxDamage() / fighter.getStats().getMaxHealth());
            target.getStats().getFitnessOBJ().setArmorFitness(target.getStats().getMaxHealth() - target.getStats().getHealth());
            target.getStats().getFitnessOBJ().setAgilityFit(target.getStats().getFitnessOBJ().getAgilityFit() / timeSpent);
            target.getStats().getFitnessOBJ().getTotalFit();
            target.getStats().getFitnessOBJ().setTotalFit();
            target.getStats().setFitness(target.getStats().getFitnessOBJ().returnTotalFit());

            //reset player health
            fighter.getStats().setHealth(fighter.getStats().getMaxHealth());

            //print fitness
            System.out.println(target.getStats().getFitnessOBJ().toString());
            writer.write(target.getStats().getFitnessOBJ().toString() + "\n");

            System.out.println("Fighting is done\n");
            writer.write("Fighting is done\n\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
