package domain;

import java.util.List;

public abstract class Character implements Entity{
    protected int strength;             // сила
    protected int dexterity;            // ловкость
    protected int maxHealth;            // максимальное здоровье
    protected int currentHealth;        // текущее здоровье
    protected Position position;        // текущая позиция


    protected Character(Position position, int currentHealth, int maxHealth, int strength, int dexterity) {
        this.position = position;
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.strength = strength;
        this.dexterity = dexterity;
    }

    // Геттеры
    public int getStrength() {return strength;}

    public int getDexterity() {return dexterity;}

    public int getMaxHealth() {return maxHealth;}

    public int getCurrentHealth() {return currentHealth;}

    @Override
    public Position getPosition() {return this.position;}

    // Сеттеры

    public void setMaxHealth(int health) {
        this.maxHealth = maxHealth;
        if (this.currentHealth > this.maxHealth) {
            this.currentHealth = this.maxHealth;
        }
    }

    public void setCurrentHealth(int health) {
        this.currentHealth = Math.max(0, Math.min(health, this.maxHealth));
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    @Override
    public void setPosition(Position position) {this.position = position;}

    @Override
    public String toString() {
        return String.format("Character (%d/%d hp) | Сила: %d | Ловкость: %d | Position: %s",
                currentHealth, maxHealth, strength, dexterity, position);
    }

    //Принятие урона
    public boolean takeDamage(int damage, Character fromUnit) {
        if (damage <= 0) return false;

        this.currentHealth -= damage;
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
        }

        return this.currentHealth == 0;
    }
    //Шанс попадания атаки
    protected double calculateHitChance(int targetDexterity){
        return 0.5 + (this.getDexterity() - targetDexterity) * 0.03 + Math.random() * 0.2 - 0.1;
    }

    //Расчет урона от атаки
    protected int calculateHitDamage(){
        int base = strength / 2;
        int varianceMax = strength / 5;
        return Math.max(1, (int) (base + Math.random() * (varianceMax + 1)));
    }

    //public void applySpecialAttackEffects(Character target, boolean targetDied){

    //}

    public void heal(int amount) {
        this.currentHealth = Math.min(this.currentHealth + amount, maxHealth);
    }

//    public boolean attack(Character target) {
//        if (!target.isAlive() || !this.isAlive()){
//            return false;
//        }
//        double hitChance = this.calculateHitChance(target.getDexterity());
//        if (Math.random() >= hitChance) return false;
//
//        int damage = calculateHitDamage(target.getStrength());
//        target.takeDamage(damage);
//        //applySpecialAttackEffects(target, targetDied);
//        return true;
//    }

    public boolean isAlive(){
        return this.currentHealth > 0;
    }

    public abstract char getDisplayChar();
}