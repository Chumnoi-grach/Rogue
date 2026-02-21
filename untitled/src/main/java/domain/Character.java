package domain;

public abstract class Character {
    protected String TypesOfCharacters = "gOsvz@";
    private String type;              // Ghost, Ogre, SnakeMagician, Vampire, Zombie, Player
    private int strength;             // сила
    private int dexterity;            // ловкость
    private int maxHealth;            // максимальное здоровье
    private int currentHealth;        // текущее здоровье
    private int level = 0;
    private int roomHeight;
    private int roomWidth;

    //ход
    //Position

    //Положение в комнате
    private int x;
    private int y;

    protected Character(char type, int level, int x, int y, int roomHeight, int roomWidth) {
        this.type = type;
        if (!TypesOfCharacters.chars().anyMatch(c -> c == type)) {
            throw new IllegalArgumentException("Не соответствие типу: g, O, s, v, z, @");
        }
        this.strength = calculateStrength(type, level);
        this.dexterity = calculateDexterity(type, level);
        this.maxHealth = calculateMaxHealth(type, level);
        this.currentHealth = this.maxHealth;
        this.x = x;
        this.y = y;
        this.roomHeight = roomHeight;
        this.roomWidth = roomWidth;
        this.level = level;
        if (type == '@') this.level = 0;
    }

    // Геттеры
    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public boolean takeDamage(int damage) {
        if (damage <= 0) return false;

        this.currentHealth -= damage;
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
        }

        return this.currentHealth == 0;
    }

    public double calculateHitChance(int targetDexterity){
        return 0.5 + (this.getDexterity() - targetDexterity) * 0.03 + Math.random() * 0.2 - 0.1;
    }

    public int calculateHitDamageWithoutWeapon(int targetDexterity){
        int base = strength / 3;
        int varianceMax = strength / 5;
        return Math.max(1, (int) (base + Math.random() * (varianceMax + 1)));
    }

    void applySpecialAttackEffects(Character target, boolean targetDied){

    }



    public boolean attackWithoutWeapon(Character target) {
        if (!target.isAlive() || !this.isAlive()){
            return false;
        }
        double hitChance = this.calculateHitChance(target.getDexterity());
        if (Math.random() >= hitChance) return false;

        int damage = calculateHitDamageWithoutWeapon(target.getStrength());
        boolean targetDied = target.takeDamage(damage);
        applySpecialAttackEffects(target, targetDied);
        return true;
    }

    @Override
    public String toString() {
        return String.format("%c (%d/%d hp) | Сила: %d | Ловкость: %d",
                type, currentHealth, maxHealth, strength, dexterity);
    }

    public int calculateMaxHealth(char type, int level) {
        switch (type) {
            case 'z':
                return 40 + level * 2 + (int)(Math.random() * 6 - 3);
            case 'O':
                return 60 + level * 4 + (int)(Math.random() * 6 - 3);
            case 's':
                return 30 + level * 1 + (int)(Math.random() * 6 - 3);
            case 'v':
                return 35 + level * 3 + (int)(Math.random() * 6 - 3);
            case 'g':
                return 20 + level * 2 + (int)(Math.random() * 6 - 3);
            case '@':
                return 80;
            default:
                return 0;
        }
    }
    public  int calculateDexterity(char type, int level) {
        switch (type) {
            case 'z':
                return 5 + level * 1;
            case 'O':
                return 4 + level * 1;
            case 's':
                return 20 + level * 3;
            case 'v':
                return 10 + level * 2;
            case 'g':
                return 15 + level * 3;
            case '@':
                return 10;
            default:
                return 0;
        }
    }
    public int calculateStrength(char type, int level){
        switch (type) {
            case 'z':
                return 12 + level * 2;
            case 'O':
                return 20 + level * 4;
            case 's':
                return 8 + level * 3;
            case 'v':
                return 10 + level * 3;
            case 'g':
                return 6 + level * 1;
            case '@':
                return 10;
            default:
                return 0;
        }
    }

    public boolean isAlive(){
        return this.currentHealth > 0;
    }
}