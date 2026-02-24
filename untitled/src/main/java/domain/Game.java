package domain;

import domain.level.Generation;
import domain.level.Level;
import domain.player.Player;

public class Game {
    private Level currentLevel;
    private Player player;
    private Generation generator = new Generation();

    public Game() {
        // Создаем игрока
        this.player = new Player("John", new Position(0,0));
        // генерируем первый уровень
        generateLevel(1);

    }


    public void generateLevel(int levelNumber) {
        this.currentLevel = generator.generateLevel(levelNumber);
        //После генерации уровня помещаем игрока в стартовую комнату
        player.setPosition(currentLevel.getRoom(currentLevel.getStartRoom()).getRandomFreePosition());
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public Player getPlayer() {
        return  player;
    }

    public void moveLeft() {
        //проверка на возможность сдвига
        //player.getPosition().getX()
    }
}
