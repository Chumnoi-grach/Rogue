package domain.monsters;

import domain.Position;
import domain.level.Room;
import domain.player.Player;

import java.util.Random;

public class Ghost extends Enemy {
    private boolean isInvisible = false;
    private int invisibleTurns = 0;
    private boolean inCombat = false;

    public Ghost(Position position) {
        super(position, 20, 20, 20, 5,
                EnemyType.GHOST, 4, 75);
    }

    @Override
    public void movePattern(Room currentRoom, Player player) {
        // Привидение постоянно телепортируется
        if (!shouldChase(currentRoom, player)) {
            // Случайная телепортация по комнате
            if (Math.random() < 0.3) { // 30% шанс телепортации
                teleportRandomly(currentRoom);
            }

            // Периодическая невидимость
            if (!isInvisible && Math.random() < 0.2) {
                isInvisible = true;
                invisibleTurns = 3;
            }

            if (isInvisible) {
                invisibleTurns--;
                if (invisibleTurns <= 0) {
                    isInvisible = false;
                }
            }
        } else {
            chasePlayer(currentRoom, player);
        }
    }

    private void teleportRandomly(Room currentRoom) {
        if (currentRoom != null) {
            this.position = currentRoom.getRandomFreePosition();
        }
    }

//    @Override
//    public boolean shouldChase(Player player) {
//        boolean chase = super.shouldChase(player);
//        if (chase) {
//            inCombat = true;
//            invisible = false; // В бою становится видимым
//        }
//        return chase;
//    }

    @Override
    protected void applySpecialAttackEffects(Player player) {
        // Привидение не имеет специальных эффектов атаки
    }

    public boolean isInvisible() {
        return isInvisible;
    }

    @Override
    public char getDisplayChar() {
        return isInvisible ? ' ' : 'g';
    }
}