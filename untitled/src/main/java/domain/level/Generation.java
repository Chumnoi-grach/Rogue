package domain.level;

import domain.Position;

import java.util.ArrayList;
import java.util.List;

public class Generation {
    private static final int WINDOW_WIDTH = 100;
    private static final int WINDOW_HEIGHT = 40;

    // Отступы для текста сверху и снизу
    private static final int TEXT_TOP_OFFSET = 1;
    private static final int TEXT_BOTTOM_OFFSET = 1;

    // Доступное пространство для комнат
    private static final int AVAILABLE_HEIGHT = WINDOW_HEIGHT - TEXT_TOP_OFFSET - TEXT_BOTTOM_OFFSET;

    // Размеры комнат с учётом зазоров между ними
    private static final int ROOM_WIDTH = (WINDOW_WIDTH - 2) / 3;  // 2 зазора между 3 комнатами
    private static final int ROOM_HEIGHT = (AVAILABLE_HEIGHT - 2) / 3;  // 2 зазора между 3 комнатами

    private final int[][] roomCoords = {
            // ряд 1 (верхний)
            {1, TEXT_TOP_OFFSET, ROOM_WIDTH, TEXT_TOP_OFFSET + ROOM_HEIGHT - 1},                              // комната 1
            {ROOM_WIDTH + 2, TEXT_TOP_OFFSET, ROOM_WIDTH * 2 + 1, TEXT_TOP_OFFSET + ROOM_HEIGHT - 1},        // комната 2
            {ROOM_WIDTH * 2 + 3, TEXT_TOP_OFFSET, ROOM_WIDTH * 3, TEXT_TOP_OFFSET + ROOM_HEIGHT - 1},        // комната 3

            // ряд 2 (средний)
            {1, TEXT_TOP_OFFSET + ROOM_HEIGHT + 1, ROOM_WIDTH, TEXT_TOP_OFFSET + ROOM_HEIGHT * 2},           // комната 4
            {ROOM_WIDTH + 2, TEXT_TOP_OFFSET + ROOM_HEIGHT + 1, ROOM_WIDTH * 2 + 1, TEXT_TOP_OFFSET + ROOM_HEIGHT * 2},     // комната 5
            {ROOM_WIDTH * 2 + 3, TEXT_TOP_OFFSET + ROOM_HEIGHT + 1, ROOM_WIDTH * 3, TEXT_TOP_OFFSET + ROOM_HEIGHT * 2},     // комната 6

            // ряд 3 (нижний)
            {1, TEXT_TOP_OFFSET + ROOM_HEIGHT * 2 + 2, ROOM_WIDTH, TEXT_TOP_OFFSET + ROOM_HEIGHT * 3 + 1},    // комната 7
            {ROOM_WIDTH + 2, TEXT_TOP_OFFSET + ROOM_HEIGHT * 2 + 2, ROOM_WIDTH * 2 + 1, TEXT_TOP_OFFSET + ROOM_HEIGHT * 3 + 1}, // комната 8
            {ROOM_WIDTH * 2 + 3, TEXT_TOP_OFFSET + ROOM_HEIGHT * 2 + 2, ROOM_WIDTH * 3, TEXT_TOP_OFFSET + ROOM_HEIGHT * 3 + 1}  // комната 9
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
