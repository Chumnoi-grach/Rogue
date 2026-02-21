package presentation;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.InputProvider;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;

public class Presentation {
    private final Terminal terminal;
    private final Screen screen;

    public Presentation() throws IOException {
        DefaultTerminalFactory factory = new DefaultTerminalFactory();
        factory.setInitialTerminalSize(new TerminalSize(100, 40)); // 100x40 символов
        try {
            terminal = factory.createTerminal();
            terminal.enterPrivateMode();
            screen = new TerminalScreen(terminal);
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

    public void putCh(char ch, int x, int y) throws IOException {
        // Получаем объект для рисования текста
        TextGraphics textGraphics = screen.newTextGraphics();

        // Устанавливаем цвет
        textGraphics.setForegroundColor(TextColor.ANSI.RED);

        // Рисуем символ в нужной позиции
        textGraphics.putString(x, y, String.valueOf(ch));

        screen.refresh();
    }

    public InputProvider getScreen() {
        return screen;
    }
}
