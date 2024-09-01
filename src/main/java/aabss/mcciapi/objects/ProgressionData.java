package aabss.mcciapi.objects;

/**
 * Data for types that track some form of progression.
 */
public record ProgressionData(Integer obtainable, Integer obtained) {

    /**
     * @return The amount that can be obtained.
     */
    @Override
    public Integer obtainable() {
        return obtainable;
    }

    /**
     * @return The amount obtained.
     */
    @Override
    public Integer obtained() {
        return obtained;
    }

    @Override
    public String toString() {
        return "ProgressionData{" +
                "obtainable=" + obtainable +
                ", obtained=" + obtained +
                '}';
    }
}
