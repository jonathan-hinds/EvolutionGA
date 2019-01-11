import java.util.List;

public class Player extends Fighter {

    private Enemy target;

    public Player(Stats stats) {
        super(stats);
    }

    public Player(List<Double> chromosome) {
        super(chromosome);
    }

//    /**
//     * @param target enemy that the player is to battle against. If the player wins the battle,
//     *               they are rewarded with the enemies exp reward.
//     * @return the outcome of the battle. If true, the player has won the battle for defeating the enemy.
//     * if false, the player has lost for being defeated before the enemy.
//     */

    public Enemy getTarget() {
        return this.target;
    }

    public void setTarget(Enemy target) {
        this.target = target;
    }
}
