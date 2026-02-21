package domain.monsters;

import domain.Character;

public class Enemy extends Character {
    //Состояния врага Возможно добавление
    enum EnemyState {
        WANDERING,          //БЛУЖДАЮЩИЙ
        CHASING            //ПРЕСЛЕДУЮЩИЙ
    }
    private int hostilityRange;
    private EnemyState state = EnemyState.WANDERING;
    private boolean firstHitMiss = false;   // для вампира — первый удар по нему всегда промах
    private boolean isVisible = true;       // для привидения (периодическая невидимость) будет работать через вероятность

    private char EnemyType;

    public char getEnemyType(){
        return this.EnemyType;
    }

    public Enemy(char type, int level, int x, int y, int roomHeight, int roomWidth) {
        super(type, level, x, y, roomHeight, roomWidth);

        if (!TypesOfCharacters.chars().anyMatch(c -> c == type) && type != '@') {
            throw new IllegalArgumentException("Не соответствие типу: g, O, s, v, z");
        }
        this.hostilityRange = calculateHostility(type, level); // пример масштабирования
        this.firstHitMiss = (type == 'v');
    }

    public int calculateHostility(char type, int level) {
        switch (type) {
            case 'z':
                return 3 + level / 4;
            case 'O':
                return 3 + level / 4;
            case 's':
                return 4 + level / 3;
            case 'v':
                return 5 + level / 2;
            case 'g':
                return 2;
            default:
                return 0;
        }
    }
}
