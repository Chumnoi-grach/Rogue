package domain.monsters;

import domain.Position;
import domain.level.Room;
import domain.player.Player;

public class Vampire extends Enemy {
    private boolean firstAttack = true;

    public Vampire(Position position) {
        super(position, 50, 50, 9, 15,
                EnemyType.VAMPIRE, 12, 100);
    }

    @Override
    public void movePattern(Room currentRoom, Player player) {

        if (!shouldChase(currentRoom, player)) {
            boolean wasMoveMade = false;
            while (!wasMoveMade) {
                int direction = (int) (Math.random() * 8);
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
                    case 4:
                        if (currentRoom.isPositionInRoom(position.translate(1, 1))) {
                            position.setX(position.getX() + 1);
                            position.setY(position.getY() + 1);
                            wasMoveMade = true;
                        }
                        break;
                    case 5:
                        if (currentRoom.isPositionInRoom(position.translate(1, -1))) {
                            position.setX(position.getX() + 1);
                            position.setY(position.getY() - 1);
                            wasMoveMade = true;
                        }
                        break;
                    case 6:
                        if (currentRoom.isPositionInRoom(position.translate(-1, 1))) {
                            position.setX(position.getX() - 1);
                            position.setY(position.getY() + 1);
                            wasMoveMade = true;
                        }
                        break;
                    case 7:
                        if (currentRoom.isPositionInRoom(position.translate(-1, -1))) {
                            position.setX(position.getX() - 1);
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

    /**
     * Реализация calculateHitChance с 100%
     * промахом первой атаки по вампиру
     *
     * @param targetDexterity
     * @return шанс совершения атаки
     */
    @Override
    public double calculateHitChance(int targetDexterity) {
        if (firstAttack) {
            firstAttack = false;
            return 0; // Первый удар всегда промах
        }
        return super.calculateHitChance(targetDexterity);
    }

    /**
     * Вампир отнимает максимальное здоровье
     * у игрока после успешного удара и восстанавливает
     * себе долю отнятого у игрока здоровья
     *
     * @param player
     */
    @Override
    protected void applySpecialAttackEffects(Player player) {

        int healthReduction = 5;
        player.setMaxHealth(player.getMaxHealth() - healthReduction);
        // Вампир восстанавливает здоровье
        this.heal(healthReduction);
    }

    @Override
    public char getDisplayChar() {
        return 'v';
    }
}