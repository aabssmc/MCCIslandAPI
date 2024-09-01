package cc.aabss.mcciapi.objects;

/**
 * Data on the amount of trophies a user has/can have.
 */
public record TrophyData(Integer bonus, Integer obtainable, Integer obtained) {

    /**
     * @return The amount of bonus trophies.
     */
    @Override
    public Integer bonus() {
        return bonus;
    }

    /**
     * @return The maximum amount of trophies that can be obtained.
     */
    @Override
    public Integer obtainable() {
        return obtainable;
    }

    /**
     * @return The amount of trophies obtained.
     */
    @Override
    public Integer obtained() {
        return obtained;
    }

    @Override
    public String toString() {
        return "TrophyData{" +
                "bonus=" + bonus +
                ", obtainable=" + obtainable +
                ", obtained=" + obtained +
                '}';
    }
}
