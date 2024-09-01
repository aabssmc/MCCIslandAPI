package aabss.mcciapi.objects;

/**
 * Collections data.
 */
public record Collections(Currency currency) {

    /**
     * @return The player's earned currency.
     */
    @Override
    public Currency currency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Collections{" +
                "currency=" + currency +
                '}';
    }
}
