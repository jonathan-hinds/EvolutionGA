public class Fitness {

    //fitness scores for each stat
    //percentage of remaining health after battle.
    private Double staminaFit = 0.0;
    //amount of attacks in a battle.
    private Double agilityFit = 0.0;
    //highest attack damage dealt during battle.
    private Double criticalFit = 0.0;
    //average attack damage dealt during battle.
    private Double attackFit = 0.0;
    //damage resisted during battle.
    private Double armorFit = 0.0;
    private Double totalFit = 0.0;

    //variables to help calculate fitness scores
    private Double maxDamage = 0.0;

    public Fitness(Double staminaFit, Double agilityFit, Double criticalFit, Double attackFit, Double armorFit, Double totalFit) {
        this.staminaFit = staminaFit;
        this.agilityFit = agilityFit;
        this.criticalFit = criticalFit;
        this.attackFit = attackFit;
        this.armorFit = armorFit;
        this.totalFit = totalFit;
    }

    public Fitness() {
    }

    public Double getStaminaFit() {
        return staminaFit;
    }

    public void setStaminaFit(Double staminaFit) {
        if(staminaFit < 0.0){
            this.staminaFit = 0.0;
        } else {
            this.staminaFit = staminaFit;
        }
    }

    public void increaseAttackFittness(Double damageDealt){
        this.attackFit += damageDealt;
    }

    public Double getAgilityFit() {
        return agilityFit;
    }

    public void increaseAgilityFitness(){
        this.agilityFit ++;
    }

    public void increaseArmourFitness(Double amount){
        this.armorFit +=amount;
    }

    public void setAgilityFit(Double agilityFit) {
        this.agilityFit = agilityFit;
    }

    public Double getCriticalFit() {
        return criticalFit;
    }

    public void setCriticalFit(Double criticalFit) {
        this.criticalFit = criticalFit;
    }

    public Double getAttackFit() {
        return attackFit;
    }

    public void setAttackFit(Double attackFit) {
        if(attackFit > 0.0) {
            this.attackFit = attackFit;
        } else {
            this.attackFit = 0.0;
        }
    }

    public Double getArmorFit() {
        return armorFit;
    }

    public void setArmorFit(Double armorFit) {
        this.armorFit = armorFit;
    }

    public Double getTotalFit() {
        return this.staminaFit + this.criticalFit + this.agilityFit + this.armorFit + this.attackFit;
    }

    public void setTotalFit() {
        this.totalFit = getTotalFit();
    }

    public Double returnTotalFit(){
        return totalFit;
    }

    public Double getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(Double maxDamage) {
        this.maxDamage = maxDamage;
    }

    public void increaseMaxDamage(Double damageDealt){
        if(damageDealt > maxDamage){
            maxDamage = damageDealt;
        }
    }

    @Override
    public String toString() {
        return "Fitness{" +
                "staminaFit=" + staminaFit +
                ", agilityFit=" + agilityFit +
                ", criticalFit=" + criticalFit +
                ", attackFit=" + attackFit +
                ", armorFit=" + armorFit +
                ", totalFit=" + totalFit +
                '}';
    }
}
