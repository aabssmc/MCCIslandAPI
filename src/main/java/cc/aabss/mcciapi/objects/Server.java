package cc.aabss.mcciapi.objects;

/**
 * A server on the network.
 */
public record Server(Game associatedGame, ServerCategory category, String subType) {

    /**
     * @return The game associated with this server, if any.
     */
    @Override
    public Game associatedGame() {
        return associatedGame;
    }

    /**
     * @return The category of the server.
     */
    @Override
    public ServerCategory category() {
        return category;
    }

    /**
     * @return The subtype of the server that can hold additional information about the server.
     */
    @Override
    public String subType() {
        return subType;
    }

    @Override
    public String toString() {
        return "Server{" +
                "associatedGame=" + associatedGame +
                ", category=" + category +
                ", subType='" + subType + '\'' +
                '}';
    }
}
