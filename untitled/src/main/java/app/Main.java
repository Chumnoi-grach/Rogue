package app;
import domain.Character;
import domain.monsters.Enemy;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello");
        Enemy z = new Enemy('z', 1,  3, 2,  9, 9);
        Enemy O = new Enemy('O', 6,  3, 2,  9, 9);
        System.out.println(z);
        System.out.println(O);
        while (z.isAlive()){
            System.out.printf("Шанс удара: %.2f\n", O.calculateHitChance(z.getDexterity()));
            if (O.attackWithoutWeapon(z)) System.out.println("Удар");
            System.out.println(z);
            System.out.println(O);
        }
    }

}
