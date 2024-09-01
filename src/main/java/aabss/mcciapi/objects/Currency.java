package aabss.mcciapi.objects;

/**
 * A player's earned currency.
 */
public record Currency(Integer coins, Integer gems, Integer materialDust, Integer royalReputation, Integer silver) {

    /**
     * @return The number of coins the player currently has.
     */
    @Override
    public Integer coins() {
        return coins;
    }

    /**
     * @return The number of gems the player currently has.
     */
    @Override
    public Integer gems() {
        return gems;
    }

    /**
     * @return The amount of material dust the player currently has.
     */
    @Override
    public Integer materialDust() {
        return materialDust;
    }

    /**
     * @return The amount of Royal Reputation the player currently has.
     */
    @Override
    public Integer royalReputation() {
        return royalReputation;
    }

    /**
     * @return The amount of silver the player currently has.
     */
    @Override
    public Integer silver() {
        return silver;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "coins=" + coins +
                ", gems=" + gems +
                ", materialDust=" + materialDust +
                ", royalReputation=" + royalReputation +
                ", silver=" + silver +
                '}';
    }
}
