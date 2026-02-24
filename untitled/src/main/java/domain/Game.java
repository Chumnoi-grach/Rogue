package domain;

import domain.level.Generation;
import domain.level.Level;
import domain.player.Player;

public class Game {
    private Level currentLevel;
    private Player player;
    private Generation generator = new Generation();
    private int currentRoom;//-1 если игрок не в комнате
    private int currentCorridor;//-1 если игрок не в коридоре

    public Game() {
        // Создаем игрока
        this.player = new Player("John", new Position(0,0));
        // генерируем первый уровень
        generateLevel(1);

    }


    public void generateLevel(int levelNumber) {
        this.currentLevel = generator.generateLevel(levelNumber);
        //После генерации уровня помещаем игрока в стартовую комнату
        currentRoom = currentLevel.getStartRoom();
        player.setPosition(currentLevel.getRoom(currentRoom).getRandomFreePosition());
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public Player getPlayer() {
        return  player;
    }

    public void moveLeft() {
        //проверка на возможность сдвига
        Position newPosition = new Position(player.getPosition().getX() - 1, player.getPosition().getY());
        //проверить стенку слева
        if (currentRoom >= 0) {
            if (currentLevel.getRoom(currentRoom).isPositionInRoom(newPosition)
            || currentLevel.getRoom(currentRoom).isPositionInDoor(newPosition)) {
                player.setPosition(newPosition);
            }

        }
        //проверить стенку коридора слева

        //проверить монстра слева
    }

    public void moveRight() {
        //проверка на возможность сдвига
        Position newPosition = new Position(player.getPosition().getX() + 1, player.getPosition().getY());
        //проверить стенку слева
        if (currentRoom >= 0) {
            if (currentLevel.getRoom(currentRoom).isPositionInRoom(newPosition)
                    || currentLevel.getRoom(currentRoom).isPositionInDoor(newPosition)) {
                player.setPosition(newPosition);
            }

        }
        //проверить стенку коридора слева

        //проверить монстра слева
    }

    public void moveUp() {
        //проверка на возможность сдвига
        Position newPosition = new Position(player.getPosition().getX(), player.getPosition().getY() - 1);
        //проверить стенку слева
        if (currentRoom >= 0) {
            if (currentLevel.getRoom(currentRoom).isPositionInRoom(newPosition)
                    || currentLevel.getRoom(currentRoom).isPositionInDoor(newPosition)) {
                player.setPosition(newPosition);
            }

        }
        //проверить стенку коридора слева

        //проверить монстра слева
    }

    public void moveDown() {
        //проверка на возможность сдвига
        Position newPosition = new Position(player.getPosition().getX(), player.getPosition().getY() + 1);
        //проверить стенку слева
        if (currentRoom >= 0) {
            if (currentLevel.getRoom(currentRoom).isPositionInRoom(newPosition)
                    || currentLevel.getRoom(currentRoom).isPositionInDoor(newPosition)) {
                player.setPosition(newPosition);
            }

        }
        //проверить стенку коридора слева

        //проверить монстра слева
    }
}
