package domain.monsters;

import domain.Position;
import domain.level.Room;
import domain.player.Player;

import java.util.Random;

public class SnakeMagician extends Enemy {
    private boolean moveRight = true;
    private boolean moveDown = true;
    private static final double SLEEP_CHANCE = 0.3;

    public SnakeMagician(Position position) {
        super(position, 70, 70, 12, 30,
                EnemyType.SNAKE_MAGICIAN, 10, 125);
    }

    @Override
    public void movePattern(Room currentRoom, Player player) {
        if (!shouldChase(currentRoom, player)) {
            // Змей-маг ходит по диагонали, постоянно меняя сторону
            moveZigZag(currentRoom);
        }else{
            chasePlayer(currentRoom, player);
        }
    }

    private void moveZigZag(Room currentRoom) {
        Position nextPosition = calculateNextZigZagPosition();
        if (!currentRoom.isPositionInRoom(nextPosition)) {
            moveRight = !moveRight;
            nextPosition = calculateNextZigZagPosition();
            if (!currentRoom.isPositionInRoom(nextPosition)) {
                moveDown = !moveDown;
                nextPosition = calculateNextZigZagPosition();
            }
        }
        if (currentRoom.isPositionInRoom(nextPosition)) {
            position = nextPosition;
        }
    }
    private Position calculateNextZigZagPosition() {
        int dx = moveRight ? 1 : -1;
        int dy = moveDown ? 1 : -1;

        return position.translate(dx, dy);
    }

    @Override
    public void chasePlayer(Room currentRoom, Player player) {
        if (player == null || !isAlive() || !player.isAlive()) {
            return;
        }
        // При преследовании змей двигается напрямую к игроку
        int dXOne = Integer.signum(player.getPosition().getX() - this.position.getX());
        int dYOne = Integer.signum(player.getPosition().getY() - this.position.getY());

        int dx = player.getPosition().getX() - this.position.getX();
        int dy = player.getPosition().getY() - this.position.getY();
        // Пытаемся двигаться сначала по горизонтали, потом по вертикали
        if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1) {
            attack(player);
        } else {
            this.position = this.position.translate(dXOne, dYOne);
        }
    }

    @Override
    protected void applySpecialAttackEffects(Player player) {
        // Шанс усыпить игрока
        if (Math.random() < SLEEP_CHANCE) {
            // TODO: Implement sleep mechanism in Game class
            System.out.println("Player is put to sleep for one turn!");
        }
    }

    @Override
    public char getDisplayChar() {
        return 's';
    }
}
