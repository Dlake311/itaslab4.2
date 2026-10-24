import java.sql.SQLException;

/**
 * Лабораторная работа 4, часть 2 (сложный вариант).
 * ООП и коллекции: класс Listik с protected-методами random() (1000 случайных int)
 * и input() (10 строк с клавиатуры).
 * Каждый пункт меню - отдельный класс-наследник PunktMenu.
 */
public class Main {
    public static void main(String[] args) {
        Baza b = new Baza("id SERIAL PRIMARY KEY, spisok VARCHAR(10), nomer INT, znachenie TEXT");
        if (!b.podkl()) return;

        PunktMenu[] menu = {
                new VyvodTablic(b),
                new SozdanieTablicy(b),
                new VvodSpiska(b),
                new UdalenieElementa(b),
                new EksportExcel(b)
        };

        while (true) {
            System.out.println();
            System.out.println("========== МЕНЮ ==========");
            for (int i = 0; i < menu.length; i++) {
                System.out.println((i + 1) + ". " + menu[i].nazvanie());
            }
            System.out.println("0. Выход");
            System.out.print("Ваш выбор: ");

            int vib = b.chitatVybor();
            if (vib == 0) {
                b.zakr();
                System.out.println("Работа завершена");
                return;
            }
            if (vib < 1 || vib > menu.length) {
                System.out.println("Нет такого пункта меню, введите число от 0 до " + menu.length);
                continue;
            }
            try {
                menu[vib - 1].vypolnit();
            } catch (SQLException e) {
                System.out.println("Ошибка работы с базой данных: " + e.getMessage());
            }
        }
    }
}
