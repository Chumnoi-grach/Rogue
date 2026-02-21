package domain;

public abstract class Character_tmp {
    private final String name; //Andrey, Zombie, Ghost
    private Position position;
    private int health;
    private int maxHealth;

    public Character_tmp(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    // Метод перемещения
    public void move(int dx, int dy) {
        this.position = position.translate(dx, dy);  // создаём новую позицию
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public String getName() {
        return this.name;
    }
}
