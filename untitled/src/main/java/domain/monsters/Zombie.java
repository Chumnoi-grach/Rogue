package domain.monsters;

import domain.Position;
import domain.level.Room;
import domain.player.Player;

import java.util.Random;

public class Zombie extends Enemy {

    public Zombie(Position position) {
        super(position, 60, 60, 5, 6,
                EnemyType.ZOMBIE, 6, 50);
    }

    /*
    Зомби двигается медленно и предсказуемо
    Просто двигается в случайном направлении, если не преследует игрока
     */
    @Override
    public void movePattern(Room currentRoom, Player player) {

        if (!shouldChase(currentRoom, player)) {
            boolean wasMoveMade = false;
            while (!wasMoveMade) {
                int direction = (int) (Math.random() * 4);
                switch (direction) {
                    case 0:
                        if (currentRoom.isPositionInRoom(position.translate(1, 0))) {
                            position.setX(position.getX() + 1);
                            wasMoveMade = true;
                        }
                        break;
                    case 1:
                        if (currentRoom.isPositionInRoom(position.translate(-1, 0))) {
                            position.setX(position.getX() - 1);
                            wasMoveMade = true;
                        }
                        break;
                    case 2:
                        if (currentRoom.isPositionInRoom(position.translate(0, 1))) {
                            position.setY(position.getY() + 1);
                            wasMoveMade = true;
                        }
                        break;
                    case 3:
                        if (currentRoom.isPositionInRoom(position.translate(0, -1))) {
                            position.setY(position.getY() - 1);
                            wasMoveMade = true;
                        }
                        break;
                }
            }
        } else {
            chasePlayer(currentRoom, player);
        }
    }

    @Override
    protected void applySpecialAttackEffects(Player player) {

    }

    @Override
    public char getDisplayChar() {
        return 'z';
    }
}