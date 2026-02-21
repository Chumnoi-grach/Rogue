package domain.items;

public enum ScrollType {
    HEALTH("здоровья"),
    STRENGTH("силы"),
    DEXTERITY("ловкости");

    private final String displayName;

    ScrollType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
