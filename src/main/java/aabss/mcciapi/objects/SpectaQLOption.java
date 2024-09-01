package aabss.mcciapi.objects;

/**
 * Internal key/value pair for documentation options.
 */
public record SpectaQLOption(String key, String value) {

    /**
     * @return The key.
     */
    @Override
    public String key() {
        return key;
    }

    /**
     * @return The value.
     */
    @Override
    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "SpectaQLOption{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
