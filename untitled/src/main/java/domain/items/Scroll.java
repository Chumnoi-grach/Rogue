package domain.items;

import domain.Entity;
import domain.Position;
import domain.Character_tmp;

public class Scroll implements Entity {
    private final String name;              // Название свитка
    private final Position position;        // final - положение не меняется
    private final ScrollType type;          // Тип свитка (enum)
    private final int increaseRate;         // На сколько повышает характеристику

    public Scroll(String name, Position position, ScrollType type, int increaseRate) {
        this.name = name;
        this.position = position;
        this.type = type;
        this.increaseRate = increaseRate;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        throw new UnsupportedOperationException("Свиток нельзя переместить!");
    }

    public String getName() {
        return name;
    }

    public ScrollType getType() {
        return type;
    }

    public int getIncreaseRate() {
        return increaseRate;
    }

    @Override
    public String toString() {
        return String.format("Свиток '%s' (%s +%d) на %s",
                name, type, increaseRate, position);
    }

    /**
     * Использовать свиток на персонаже
     * @param character персонаж, на котором используется свиток
     */
    public void useOn(Character_tmp character) {
        switch (type) {
            case HEALTH:
                //здесь потом заменить вывод сообщения под статусную строку
                System.out.printf("%s зачитал %s и увеличил максимальное здоровье до %d\n",
                        character.getName(), name, character.getMaxHealth() + increaseRate
                        );
                character.setMaxHealth(character.getMaxHealth() + increaseRate);
                break;
//            case STRENGTH:
//                character.setStrength(character.getStrength() + increaseRate);
//                System.out.println(name + " увеличивает силу на " + increaseRate + "!");
//                break;
            // ... другие типы
        }
    }
}
