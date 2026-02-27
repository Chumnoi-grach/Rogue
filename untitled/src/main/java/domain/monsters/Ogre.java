package domain.monsters;

import domain.Character;
import domain.Position;
import domain.level.Room;
import domain.player.Player;

import java.util.Random;

public class Ogre extends Enemy {
    private boolean resting = false;
    private int moveCounter = 0;
    private static final int MOVE_STEP = 2;

    public Ogre(Position position) {
        super(position, 100, 100, 25, 3,
                EnemyType.OGRE, 10, 150);
    }

    /**
     * Паттерн подразумевает хождение Огра на 2 клетки
     * @param currentRoom
     * @param player
     */
    @Override
    public void movePattern(Room currentRoom, Player player) {
        if (!shouldChase(currentRoom, player)) {
            // Огр ходит по комнате на две клетки
            while (moveCounter < MOVE_STEP) {
                int direction = (int) (Math.random() * 4);
                switch (direction) {
                    case 0:
                        if (currentRoom.isPositionInRoom(position.translate(1, 0))) {
                            position.setX(position.getX() + 1);
                            moveCounter++;
                        }
                        break;
                    case 1:
                        if (currentRoom.isPositionInRoom(position.translate(-1, 0))) {
                            position.setX(position.getX() - 1);
                            moveCounter++;
                        }
                        break;
                    case 2:
                        if (currentRoom.isPositionInRoom(position.translate(0, 1))) {
                            position.setY(position.getY() + 1);
                            moveCounter++;
                        }
                        break;
                    case 3:
                        if (currentRoom.isPositionInRoom(position.translate(0, -1))) {
                            position.setY(position.getY() - 1);
                            moveCounter++;
                        }
                        break;
                }
            }
            moveCounter = 0;
        } else {
            chasePlayer(currentRoom, player);
        }
    }

    @Override
    protected void applySpecialAttackEffects(Player player) {

    }

    @Override
    public void chasePlayer(Room currentRoom, Player player) {
        if (player == null || !isAlive() || !player.isAlive()) {
            return;
        }

        int dXOne = Integer.signum(player.getPosition().getX() - this.position.getX());
        int dYOne = Integer.signum(player.getPosition().getY() - this.position.getY());

        int dx = player.getPosition().getX() - this.position.getX();
        int dy = player.getPosition().getY() - this.position.getY();
        // Пытаемся двигаться сначала по горизонтали, потом по вертикали
        while (moveCounter < MOVE_STEP){
            boolean wasAttackInStep = false;
            if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1 && !wasAttackInStep) {
                if (!resting) attack(player);
                resting = !resting;
                wasAttackInStep = !wasAttackInStep;
            } else if (Math.abs(dx) > Math.abs(dy)) {
                this.position = this.position.translate(dXOne, 0);
            } else {
                this.position = this.position.translate(0, dYOne);
            }
        }
    }

    @Override
    public boolean attack(Player player) {
        if (resting) {
            resting = false;
            return false;
        }

        super.attack(player);
        resting = true; // Отдыхает после атаки
        return true;
    }

    @Override
    public boolean takeDamage(int damage, Character fromUnit) {
        if (damage <= 0 || !(fromUnit instanceof Player)) return false;


        this.currentHealth -= damage;
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
        }
        if (this.isAlive()){
            counterattack((Player) fromUnit);
        }

        return !this.isAlive();
    }

    protected void counterattack(Player player) {
        // После успешной атаки гарантированно контратакует
        if (player.isAlive()) {
            player.takeDamage(this.calculateHitDamage(), this);
        }
    }


    @Override
    public char getDisplayChar() {
        return 'O';
    }
}