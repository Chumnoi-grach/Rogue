package app;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import presentation.Presentation;

public class Main {
    public static void main(String[] args)  throws Exception {
        Presentation presentation = new Presentation();
        presentation.start();

        int x = 5;
        int y = 5;

        presentation.putCh('H',x,y);

        boolean running = true;

        while (running) {
            KeyStroke keyStroke = presentation.getScreen().readInput(); // readInput() ждет нажатия

            // Обрабатываем нажатую клавишу
            if (keyStroke != null) {
                KeyType keyType = keyStroke.getKeyType();

                switch (keyType) {
                    case Enter:
                        System.out.println("Нажата клавиша Enter");
                        break;
                    case Character:
                        char c = keyStroke.getCharacter();
                        switch (c) {
                            case 'w':
                            case 'W':
                                presentation.putCh('H',x,--y);
                                System.out.println("Движение вверх (W)");
                                break;
                            case 'a':
                            case 'A':
                                presentation.putCh('H',--x,y);
                                System.out.println("Движение влево (A)");
                                break;
                            case 's':
                            case 'S':
                                presentation.putCh('H',x,++y);
                                System.out.println("Движение вниз (S)");
                                break;
                            case 'd':
                            case 'D':
                                presentation.putCh('H',++x,y);
                                System.out.println("Движение вправо (D)");
                                break;
                            case 'q':
                            case 'Q':
                                System.out.println("Выход по Q");
                                running = false;
                                break;
                            default:
                                System.out.println("Нажата клавиша: " + c);
                        }

                    default:
                        System.out.println("Нажата другая клавиша: " + keyType);
                }
            }
        }
        presentation.end();
    }
}
