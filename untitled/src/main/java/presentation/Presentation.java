package presentation;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.InputProvider;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import domain.level.Level;
import domain.level.Room;

import java.io.IOException;

public class Presentation {
    private static final int WINDOW_WIDTH = 100;
    private static final int WINDOW_HEIGHT = 40;
    private final Terminal terminal;
    private final Screen screen;

    private static final TextColor COLORBOUND = TextColor.ANSI.YELLOW;
    private static final TextColor COLORDOOR = TextColor.ANSI.YELLOW_BRIGHT;
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

    public void printLevel(Level currentLevel) throws IOException {
        clear();
        printRooms(currentLevel);
        // ПЕЧАТЬ ДВЕРЕЙ
        // ПЕЧЕТЬ КОРИДОРОВ
        // ПЕЧАТЬ СУЩНОСТЕЙ
        // ИГРОКА
        // ВЫВОД СТРОК
    }

    private void printRooms(Level currentLevel) throws IOException {
        for (int i = 0; i < 9; i++) {
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
