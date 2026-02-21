package domain.level;

import domain.Entity;
import domain.Position;

import java.util.*;

// Конструктор комнаты возвращает объект со случайным размером комнаты в заданном диапазоне координат углов
public class Room {
    private final Position leftCorner;
    private final Position rightCorner;
    private boolean isFreePositions;

    //Координаты сущностей в комнате.
    private List<Entity> entities = new ArrayList<>();

    // минимальные размеры комнаты
    private static final int minRoomWidth = 6;
    private static final int minRoomHeight = 4;

    // Конструктор случайно генерирует оба угла комнаты в заданном квадрате
    public Room(Position roomBoundsMin, Position roomBoundsMax) {

        int leftX = rndBetween(roomBoundsMin.getX(), roomBoundsMax.getX() - minRoomWidth);
        int leftY = rndBetween(roomBoundsMin.getY(), roomBoundsMax.getY() - minRoomHeight);

        // Правый нижний угол генерируем с гарантией минимальных размеров
        int rightX = rndBetween(
                Math.max(leftX + minRoomWidth, roomBoundsMin.getX() + minRoomWidth),
                roomBoundsMax.getX()
        );

        int rightY = rndBetween(
                Math.max(leftY + minRoomHeight, roomBoundsMin.getY() + minRoomHeight),
                roomBoundsMax.getY()
        );

        this.isFreePositions = true;
        this.leftCorner = new Position(leftX, leftY);
        this.rightCorner = new Position(rightX, rightY);
    }

    public Position getRandomFreePosition() {
        if (!isFreePositions) return null;

        //Собрать координаты занятых позиций в виде Set String "x,y"
        Set<String> occupied = new HashSet<>();
        for (Entity entity : entities) {
            Position pos = entity.getPosition();
            occupied.add(pos.getX() + "," + pos.getY());
        }

        // Список свободных клеток
        List<Position> freePositions = new ArrayList<>();
        for (int x = this.getLeftCorner().getX() + 1; x < this.getRightCorner().getX(); x++) {
            for (int y = this.getLeftCorner().getY() + 1; y < this.getRightCorner().getY(); y++) {
                if (!occupied.contains(x + "," + y)) {
                    freePositions.add(new Position(x,y));
                }
            }
        }

        if (freePositions.isEmpty()) {
            this.isFreePositions = false;
            return null;
        }

        return freePositions.get(rndBetween(0,freePositions.size() - 1));
    }

    // Гетеры, сеттеры
    public Position getLeftCorner() {
        return leftCorner;
    }

    public Position getRightCorner() {
        return rightCorner;
    }

    public static int rndBetween(int min, int max) {
        Random rnd = new Random();
        return rnd.nextInt(min, max + 1);
    }

    //Проверить что позиция внутри комнаты
    private boolean isPositionInRoom(Position position) {
        return position.getX() > leftCorner.getX() &&
                position.getX() < rightCorner.getX() &&
                position.getY() > leftCorner.getY() &&
                position.getY() < rightCorner.getY();
    }

    //Проверить что позиция не занята
    private boolean isPositionFree(Position position) {
        for (Entity entity : entities) {
            if (position.equal(entity.getPosition())) {
                return false;
            }
        }
        return true;
    }

    public boolean addEntity(Entity entity) {
        if (isFreePositions &&
                isPositionInRoom(entity.getPosition()) &&
                isPositionFree(entity.getPosition())) {
            entities.add(entity);
            return true;
        }
        return false;
    }

    public void deleteEntity(Entity entity) {
        entities.remove(entity);
    }

}
