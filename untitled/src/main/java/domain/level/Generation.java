package domain.level;

import domain.Position;

import java.util.ArrayList;
import java.util.List;
/*
Класс для генерации уровня
-Комнаты
-Двери
-Коридоры
-Предметы
-Монстры
 */

public class Generation {
    private static final int WINDOW_WIDTH = 100;
    private static final int WINDOW_HEIGHT = 40;
    private static final int ROOM_COUNT = 9;


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
        Room[] rooms = generateRooms();

        //Создаем связный граф
        RoomGraph roomGraph = new RoomGraph();
        //roomGraph.printGraph();

        // 2. создаем двери в комнатах на основе таблицы связности графа
        for (int i = 0; i < ROOM_COUNT; i++) {
            rooms[i].genDoors(roomGraph.getRoomDoors(i));
        }

        // 2. Создаем коридоры между комнатами
        List<Corridor> corridors = genCorridors(rooms);

        // 3. Создаем уровень
//        Level level = new Level(levelNumber, rooms, corridors);
        Level level = new Level(levelNumber, rooms, corridors);

        // 4. Размещаем сущности
        //populateLevel(level, levelNumber);

        return level;
    }

    private List<Corridor> genCorridors(Room[] rooms) {
        List<Corridor> corridors = new ArrayList<>();
        //Создаем горизонтальные коридоры. Слева направо
        for (int i = 0; i < ROOM_COUNT - 1; i++) {
           if (rooms[i].getRigthDoor() != null) {//восточная стена с дверью.
               //Значит есть соседняя комната с дверью в западной стене
               Position doorLeft = rooms[i].getRigthDoor().getPosition();
               Position doorRight = rooms[i + 1].getLeftDoor().getPosition();
               createHorizontalCorridor(corridors, doorLeft, doorRight);
           }
        }

        //Создаем вертикальные коридоры. Слева направо
        for (int i = 0; i < ROOM_COUNT - 3; i++) {
            if (rooms[i].getBottomDoor() != null) {//южная стена с дверью.
                //Значит есть соседняя комната с дверью в северной стене
                Position doorBottom = rooms[i].getBottomDoor().getPosition();
                Position doorUpper = rooms[i + 3].getUpperDoor().getPosition();
                createVerticalCorridor(corridors, doorBottom, doorUpper);
            }
        }

        return corridors;
    }

    private void createHorizontalCorridor(List<Corridor> corridors, Position left, Position right) {
        int leftX = left.getX() + 1;
        int leftY = left.getY();
        int rightX = right.getX() - 1;
        int rightY = right.getY();
        //Двери на одном уровне. Связываем одним горизонтальным коридором
        if (left.getX() == right.getX()) {
            corridors.add(new Corridor( new Position(leftX, leftY), new Position(rightX, rightY)));
        } else {
            //Двери на разных уровнях, создаем коридор с поворотом.
            int cornerX = Room.rndBetween(leftX, rightX);
            corridors.add(new Corridor( new Position(leftX, leftY), new Position(cornerX, leftY)));
            corridors.add(new Corridor( new Position(cornerX, leftY), new Position(cornerX, rightY)));
            corridors.add(new Corridor( new Position(cornerX, rightY), new Position(rightX, rightY)));
        }
    }

    private void createVerticalCorridor(List<Corridor> corridors, Position bottom, Position upper) {
        int bottomX = bottom.getX();
        int bottomY = bottom.getY() + 1;
        int upperX = upper.getX();
        int upperY = upper.getY() - 1;
        //Двери на одной вертикали - связываем одним коридором
        if (bottom.getY() == upper.getY()) {
            corridors.add(new Corridor( new Position(bottomX, bottomY), new Position(upperX, upperY)));
        } else {
            //Двери на разных X, создаем коридор с поворотом.
            int cornerY = Room.rndBetween(bottomY, upperY);
            corridors.add(new Corridor( new Position(bottomX, bottomY), new Position(bottomX, cornerY)));
            corridors.add(new Corridor( new Position(bottomX, cornerY), new Position(upperX, cornerY)));
            corridors.add(new Corridor( new Position(upperX, cornerY), new Position(upperX, upperY)));
        }
    }


    public Room[] generateRooms() {
        Room[] rooms = new Room[ROOM_COUNT];
        for (int i = 0; i < ROOM_COUNT; i++) {
            Position min = new Position(roomCoords[i][0], roomCoords[i][1]);
            Position max = new Position(roomCoords[i][2], roomCoords[i][3]);
            rooms[i] = new Room(min, max);
        }
        return rooms;
    }
}
