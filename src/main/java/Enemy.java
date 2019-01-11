import java.util.List;

public class Enemy extends Fighter {

    private Double expReward = 0.0;

    public Enemy(List<Double> chromosome) {
        super(chromosome);
    }

    public Enemy(Stats stats) {
        super(stats);
    }

    /**
     * @param minReward the minimum amount of exp rewarded for defeating this enemy.
     * @param maxReward the maxmium amount of exp rewarded for defeating this enemy.
     */
    public void setExpReward(Double minReward, Double maxReward) {
        expReward = calculateExpReward(minReward, maxReward);
    }

    /**
     * @param minReward the minimum amount of exp rewarded for defeating this enemy.
     * @param maxReward the maxmium amount of exp rewarded for defeating this enemy.
     * @return the randomly calculated exp reward for defeating this enemy, given the minimum and
     * maximum exp rewards specified.
     */
    public Double calculateExpReward(Double minReward, Double maxReward){
        return Math.ceil(Math.random() * maxReward) + minReward;
    }

    public Double getExpReward() {
        return expReward;
    }
}
