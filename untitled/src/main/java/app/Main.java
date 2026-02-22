package app;
import domain.Character;
import domain.items.*;
import domain.monsters.Ogre;
import domain.player.Player;
import domain.Position;
import domain.monsters.Enemy;

import static domain.items.ConsumableType.*;

public class Main {
    public static void main(String[] args) {

        Position position = new Position(1,1);
        System.out.println("Hello");
        Enemy z = new Enemy("Zombie", 1,  position);
        Enemy O = new Enemy("Ogre", 6,  position);
        System.out.println(z);
        System.out.println(O);
        while (z.isAlive()){
            System.out.printf("Шанс удара: %.2f\n", O.calculateHitChance(z.getDexterity()));
            if (O.attack(z)) System.out.println("Удар");
            System.out.println(z);
            System.out.println(O);
        }
        Player player = new Player("Данил Колбасенко", position);
        System.out.println(player);
        Weapon sword = new Weapon("Sword", '/',20,5, position);
        Potion potion = new Potion("Heeeeal", '^',5,5, HEALTH, position);
        Scroll scroll = new Scroll("Трамвайный билет", '~',100, HEALTH, position);

        player.pickUpItem(sword);
        player.pickUpItem(potion);
        player.pickUpItem(scroll);

        System.out.println("\tВзял меч:\n\t" + player);

        player.useItem(ItemType.WEAPON,1);
        System.out.println("\tВзял меч:\n\t" + player);

        System.out.println(potion);
        System.out.println(scroll);

        player.useItem(ItemType.SCROLL, 1);
        player.useItem(ItemType.POTION, 1);
        System.out.println("\n" + player);

        player.attack(O);
        System.out.println(O);
    }

}
