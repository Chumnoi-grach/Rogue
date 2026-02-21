package domain;

public class Player extends Character_tmp implements Entity {

    //Конструктор
    public Player(String name, Position position) {
        super(name, position);
        setHealth(30);
        setMaxHealth(45);
    }


}
