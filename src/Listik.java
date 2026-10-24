import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Класс Listik с двумя protected-методами:
 *   random() - список из 1000 случайных значений int (генератор);
 *   input()  - список из 10 строк, вводимых с клавиатуры.
 */
public class Listik {
    public static final int RAZMER_RANDOM = 1000;
    public static final int RAZMER_INPUT = 10;
    public static final int MIN_ZNACH = -10000;
    public static final int MAX_ZNACH = 10000;

    private final Random generator = new Random();

    protected List<Integer> random() {
        List<Integer> spisok = new ArrayList<>(RAZMER_RANDOM);
        for (int i = 0; i < RAZMER_RANDOM; i++) {
            spisok.add(MIN_ZNACH + generator.nextInt(MAX_ZNACH - MIN_ZNACH + 1));
        }
        return spisok;
    }

    protected List<String> input(Scanner scan) {
        List<String> spisok = new ArrayList<>(RAZMER_INPUT);
        System.out.println("Введите " + RAZMER_INPUT + " строк (каждую - с новой строки):");
        while (spisok.size() < RAZMER_INPUT) {
            System.out.print((spisok.size() + 1) + ") ");
            String s = scan.nextLine();
            if (s.trim().isEmpty()) {
                System.out.println("Строка не может быть пустой");
                continue;
            }
            spisok.add(s);
        }
        return spisok;
    }
}
