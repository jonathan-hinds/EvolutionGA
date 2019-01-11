import java.util.concurrent.CountDownLatch;

public class Battle {

    public Battle() {
    }

    /**
     * @param fighter fighter who be participating in the battle.
     * @param target target of fighter who will be participating as well.
     */
    public void battle(Fighter fighter, Fighter target){

        CountDownLatch latch = new CountDownLatch(2);

        fighter.timedAttack(target, latch);
        target.timedAttack(fighter, latch);

        try {
            latch.await();
            System.out.println("Fighting is done\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
