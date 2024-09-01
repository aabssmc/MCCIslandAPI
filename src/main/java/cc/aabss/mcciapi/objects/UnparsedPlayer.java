package cc.aabss.mcciapi.objects;

/**
 * A player with just the uuid or username, can be turned into a {@link Player} object later.
 */
public class UnparsedPlayer {
    private String uuid;
    private String name;

    public UnparsedPlayer(String nameOrUuid) {
        if (nameOrUuid.length() > 16) {
            uuid = nameOrUuid;
        } else {
            name = nameOrUuid;
        }
    }

    public UnparsedPlayer(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "UnparsedPlayer{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
