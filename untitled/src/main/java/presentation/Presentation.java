package presentation;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.InputProvider;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import domain.Entity;
import domain.Game;
import domain.Position;
import domain.items.BaseItem;
import domain.level.Corridor;
import domain.level.Door;
import domain.level.Level;
import domain.level.Room;
import domain.monsters.Enemy;
import domain.player.Player;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Presentation {
    private static final int ROOM_COUNT = 9;
    private static final int MAX_DOORS_IN_ROOM = 4;
    private static final int WINDOW_WIDTH = 100;
    private static final int WINDOW_HEIGHT = 40;
    private final Terminal terminal;
    private final Screen screen;

    private static final TextColor COLORPLAYER = TextColor.ANSI.WHITE;
    private static final TextColor COLORENEMY = TextColor.ANSI.RED_BRIGHT;
    private static final TextColor COLORITEM = TextColor.ANSI.GREEN;
    private static final TextColor COLORBOUND = TextColor.ANSI.YELLOW;
    private static final TextColor COLORDOOR = TextColor.ANSI.YELLOW_BRIGHT;
    private static final TextColor COLORPASSAGE = TextColor.Factory.fromString("#555555");
    private static final TextColor COLORSTAIRS = TextColor.ANSI.BLUE;
    private static final String LEFTTOPBOUND = "╔";
    private static final String LEFTBOTBOUND = "╚";
    private static final String RIGHTTOPBOUND = "╗";
    private static final String RIGHTBOTBOUND = "╝";
    private static final String VERTBOUND = "║";
    private static final String HORIZBOUND = "═";
    private static final String HORIZDOOR = "━";
    private static final String VERTDOOR = "┃";
    private static final String PASSAGE = "░";
    private static final String ROOMFLOOR = ".";
    private static final String PLAYER = "@";
    private static final String STAIRSDOWN = "#";
/*
╔═════╗
║     ┃░░░
║     ║
╚══━══╝
   ░

 ╔┓┏╦━━ ╦ ┓╔┓╔━━╗╔╗ ║┗┛║┗━╣┃║┃║╯╰║║║ ║┏┓║┏ ━ ╣┗ ╣┗╣╰╯║╠ ╣ ╚┛┗╩━━ ╩ ━╩━╩━━╝╚╝



 */

    public Presentation() throws IOException {
        DefaultTerminalFactory factory = new DefaultTerminalFactory();
        factory.setInitialTerminalSize(new TerminalSize(WINDOW_WIDTH, WINDOW_HEIGHT)); // 100x40 символов

        // НАСТРОЙКА ШРИФТА - добавляем перед createTerminal()
        Font font = new Font("Monospaced", Font.BOLD, 18); // жирный, размер 24
        SwingTerminalFontConfiguration fontConfig =
                SwingTerminalFontConfiguration.newInstance(font);
        factory.setTerminalEmulatorFontConfiguration(fontConfig);

        try {
            terminal = factory.createTerminal();
            // Проверяем, является ли терминал SwingTerminalFrame
            if (terminal instanceof SwingTerminalFrame) {
                SwingTerminalFrame swingTerminal = (SwingTerminalFrame) terminal;
                // Центрируем окно на экране
                swingTerminal.setLocationRelativeTo(null); // null = относительно центра экрана
            }
            terminal.enterPrivateMode();
            screen = new TerminalScreen(terminal);
            System.out.println("New Presentation");
        } catch (IOException e) {
            System.err.println("Failed to initialize terminal: " + e.getMessage());
            throw e;
        }
    }

    public void clear() {
        screen.clear();
    }

    public void start() throws IOException {
        screen.startScreen();
        clear();
        terminal.setCursorVisible(false);
    }

    public void end() throws IOException {
        screen.stopScreen();
        terminal.setCursorVisible(true);
        terminal.close();
    }

    public void displayGame(Game game) throws IOException {
        clear();
        printRooms(game.getCurrentLevel());
        printDoors(game.getCurrentLevel());
        printCorridors(game.getCurrentLevel());
        // ПЕЧАТЬ СУЩНОСТЕЙ

        printAllEntities(game.getCurrentLevel());
        // ИГРОКА

        putCh(STAIRSDOWN.charAt(0), game.getCurrentLevel().getStairsDown().getX(), game.getCurrentLevel().getStairsDown().getY(), COLORSTAIRS);

        printPlayer(game.getPlayer());
    }


    public void printPlayer(Player player) throws IOException {
        if (player != null)
            putCh(PLAYER.charAt(0), player.getPosition().getX(), player.getPosition().getY(), COLORPLAYER);
    }

    private void printAllEntities(Level level) throws IOException {
        Set<Entity> allEntities = level.getAllEntities();

        for (Entity entity : allEntities) {
            // Пропускаем игрока (его отображаем отдельно)
            if (entity instanceof Player) continue;

            Position pos = entity.getPosition();
            if (pos == null) continue;

            // Определяем символ и цвет для каждого типа сущности
            if (entity instanceof Enemy) {
                Enemy enemy = (Enemy) entity;
                // Используем getDisplayChar() из Enemy
                char symbol = enemy.getDisplayChar();
                //TextColor color = getEnemyColor(enemy);
                putCh(symbol, pos.getX(), pos.getY(), COLORENEMY);

            } else if (entity instanceof BaseItem) {
                BaseItem item = (BaseItem) entity;
                // Определяем символ для предмета
                char symbol = item.getDisplayChar();
                //TextColor color = getItemColor(item);
                putCh(symbol, pos.getX(), pos.getY(), COLORITEM);
            }
        }
    }

    private void printCorridors(Level currentLevel) throws IOException {
        List<Corridor> corridors = currentLevel.getCorridors();
        for (int i = 0; i < corridors.size(); i++) {
            Corridor corridor = corridors.get(i);
            int x1 = corridor.getLeftCorner().getX();
            int y1 = corridor.getLeftCorner().getY();
            int x2 = corridor.getRightCorner().getX();
            int y2 = corridor.getRightCorner().getY();

            // нормализация, чтобы x1 <= x2 и y1 <= y2
            if (x1 > x2) { int t = x1; x1 = x2; x2 = t; }
            if (y1 > y2) { int t = y1; y1 = y2; y2 = t; }

            // рисуем включительно по x2 и y2
            for (int x = x1; x <= x2; x++) {
                for (int y = y1; y <= y2; y++) {
                    putCh(PASSAGE.charAt(0), x, y, COLORPASSAGE);
                }
            }
        }
    }

    private void printDoors(Level currentLevel) throws IOException {
        for (int i = 0; i < ROOM_COUNT; i++) {
            Door[] doors = currentLevel.getRoom(i).getDoors();

            if(currentLevel.getRoom(i).getUpperDoor() != null) {
                putCh(HORIZDOOR.charAt(0),
                        currentLevel.getRoom(i).getUpperDoor().getPosition().getX(),
                        currentLevel.getRoom(i).getUpperDoor().getPosition().getY(),
                        COLORDOOR);
            }
            if(doors[1] != null) {
                putCh(VERTDOOR.charAt(0),
                        currentLevel.getRoom(i).getRigthDoor().getPosition().getX(),
                        currentLevel.getRoom(i).getRigthDoor().getPosition().getY(),
                        COLORDOOR);
            }
            if(doors[2] != null) {
                putCh(HORIZDOOR.charAt(0),
                        currentLevel.getRoom(i).getBottomDoor().getPosition().getX(),
                        currentLevel.getRoom(i).getBottomDoor().getPosition().getY(),
                        COLORDOOR);
            }
            if(doors[3] != null) {
                putCh(VERTDOOR.charAt(0),
                        currentLevel.getRoom(i).getLeftDoor().getPosition().getX(),
                        currentLevel.getRoom(i).getLeftDoor().getPosition().getY(),
                        COLORDOOR);
            }
        }
    }

    private void printRooms(Level currentLevel) throws IOException {
        for (int i = 0; i < ROOM_COUNT; i++) {
            Room room = currentLevel.getRoom(i);

            int leftX = room.getLeftCorner().getX();
            int leftY = room.getLeftCorner().getY();
            int rightX = room.getRightCorner().getX();
            int rightY = room.getRightCorner().getY();

            // Рисуем верхнюю горизонтальную стену (с углами)
            for (int x = leftX + 1; x < rightX; x++) {
                putCh(HORIZBOUND.charAt(0), x, leftY, COLORBOUND);
            }

            // Рисуем нижнюю горизонтальную стену (с углами)
            for (int x = leftX + 1; x < rightX; x++) {
                putCh(HORIZBOUND.charAt(0), x, rightY, COLORBOUND);
            }

            // Рисуем левую вертикальную стену (с углами)
            for (int y = leftY + 1; y < rightY; y++) {
                putCh(VERTBOUND.charAt(0), leftX, y, COLORBOUND);
            }

            // Рисуем правую вертикальную стену (с углами)
            for (int y = leftY + 1; y < rightY; y++) {
                putCh(VERTBOUND.charAt(0), rightX, y, COLORBOUND);
            }

            // Рисуем углы
            putCh(LEFTTOPBOUND.charAt(0), leftX, leftY, COLORBOUND);     // ╔
            putCh(RIGHTTOPBOUND.charAt(0), rightX, leftY, COLORBOUND);  // ╗
            putCh(LEFTBOTBOUND.charAt(0), leftX, rightY, COLORBOUND);   // ╚
            putCh(RIGHTBOTBOUND.charAt(0), rightX, rightY, COLORBOUND); // ╝

            // Заготовка для рисования дверей
            // TODO: после добавления координат дверей в Room
        /*
        // Рисуем двери (если есть)
        for (Door door : room.getDoors()) {
            int doorX = door.getX();
            int doorY = door.getY();

            if (door.isHorizontal()) {
                putCh(horizontalDoor.charAt(0), doorX, doorY, TextColor.ANSI.YELLOW);
            } else {
                putCh(verticalDoor.charAt(0), doorX, doorY, TextColor.ANSI.YELLOW);
            }
        }

// В классе Door предполагается:
// - getX(), getY() - координаты двери
// - isHorizontal() - true для горизонтальной двери, false для вертикальной

for (Door door : room.getDoors()) {
    if (door.isHorizontal()) {
        putCh(horizontalDoor.charAt(0), door.getX(), door.getY(), TextColor.ANSI.YELLOW);
    } else {
        putCh(verticalDoor.charAt(0), door.getX(), door.getY(), TextColor.ANSI.YELLOW);
    }
}
        */
        }
    }

    public void putCh(char ch, int x, int y, TextColor color) throws IOException {
        // Получаем объект для рисования текста
        TextGraphics textGraphics = screen.newTextGraphics();

        // Устанавливаем цвет
        textGraphics.setForegroundColor(color);

        // Рисуем символ в нужной позиции
        textGraphics.putString(x, y, String.valueOf(ch));

//        screen.refresh();
    }

    public void refresh() throws IOException {
        screen.refresh();
    }

    public InputProvider getScreen() {
        return screen;
    }
}
