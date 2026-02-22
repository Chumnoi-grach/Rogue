package domain.level;

import domain.Position;

import java.util.ArrayList;
import java.util.List;

public class Generation {
    private final int[][] roomCoords = {
            {0, 0, 22, 8},    // комната 1
            {25, 0, 47, 8},   // комната 2
            {50, 0, 72, 8},   // комната 3
            {0, 11, 22, 19},  // комната 4
            {25, 11, 47, 19}, // комната 5
            {50, 11, 72, 19}, // комната 6
            {0, 22, 22, 30},  // комната 7
            {25, 22, 47, 30}, // комната 8
            {50, 22, 72, 30}  // комната 9
    };

    public Level generateLevel(int levelNumber){
        // 1. Создаем комнаты
        List<Room> rooms = generateRooms();

        // 2. Создаем коридоры между комнатами
        //List<Corridor> corridors = generateCorridors(rooms);

        // 3. Создаем уровень
//        Level level = new Level(levelNumber, rooms, corridors);
        Level level = new Level(levelNumber, rooms);

        // 4. Размещаем сущности
        //populateLevel(level, levelNumber);

        return level;
    }

    public ArrayList<Room> generateRooms() {
        ArrayList<Room> rooms = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            Position min = new Position(roomCoords[i][0], roomCoords[i][1]);
            Position max = new Position(roomCoords[i][2], roomCoords[i][3]);
            rooms.add(new Room(min, max));
        }
        return rooms;
    }
}
