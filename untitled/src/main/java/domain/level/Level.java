package domain.level;

import domain.Entity;
import domain.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level {
    private final int levelNumber;
    private final List<Room> rooms;
    //private final List<Corridor> corridors;
    //private final Map<Position, Entity> entities; // позиция -> сущность
    private Position stairsDown; // лестница вниз

    public Level(int levelNumber, List<Room> rooms /*, List<Corridor> corridors */) {
        this.levelNumber = levelNumber;
        this.rooms = rooms;
        //this.corridors = corridors;
        //this.entities = new HashMap<>();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Room getRoom(int roomNumber) {
        return rooms.get(roomNumber);
    }

    //    private static final int[][] ROOM_COORDS = {
//            {0, 0, 22, 9},    // комната 1
//            {25, 0, 47, 9},   // комната 2
//            {50, 0, 72, 9},   // комната 3
//            {0, 11, 22, 20},  // комната 4
//            {25, 11, 47, 20}, // комната 5
//            {50, 11, 72, 20}, // комната 6
//            {0, 22, 22, 30},  // комната 7
//            {25, 22, 47, 30}, // комната 8
//            {50, 22, 72, 30}  // комната 9
//    };
//
//    public Level(int level) {
//        this.rooms = generateRooms();
//    }
//
//    private ArrayList<Room> generateRooms() {
//        ArrayList<Room> rooms = new ArrayList<>(9);
//        for (int i = 0; i < 9; i++) {
//            Position min = new Position(ROOM_COORDS[i][0], ROOM_COORDS[i][1]);
//            Position max = new Position(ROOM_COORDS[i][2], ROOM_COORDS[i][3]);
//            rooms.add(new Room(min, max));
//        }
//        return rooms;
//    }
}