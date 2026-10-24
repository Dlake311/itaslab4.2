import java.util.List;
import java.util.Scanner;

/**
 * Наследник Listik: получает доступ к protected-методам random() и input()
 * и хранит два полученных списка.
 */
public class Spiski extends Listik {
    private List<Integer> chisla;  // 1000 случайных int
    private List<String> stroki;   // 10 строк с клавиатуры

    public void zapolnit(Scanner scan) {
        chisla = random();
        System.out.println("Сгенерирован список из " + chisla.size() + " случайных чисел ("
                + MIN_ZNACH + ".." + MAX_ZNACH + ")");
        stroki = input(scan);
    }

    public List<Integer> getChisla() {
        return chisla;
    }

    public List<String> getStroki() {
        return stroki;
    }
}
