package domain;

import domain.level.Generation;
import domain.level.Level;

public class Game {
    private Level currentLevel;
    private Player player;
    private Generation generator;

    public Game() {
        this.generator = new Generation();
        this.player = new Player("John", new Position(5, 5)); // стартовая позиция

        // генерируем первый уровень
        this.currentLevel = generator.generateLevel(1);

        // размещаем игрока
        currentLevel.getRoom(0).putEntintyToRndPlace(player);

    }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}
