import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Работа с таблицей списков. Каждый элемент - отдельная запись:
 * (id, spisok = 'random' или 'input', nomer - позиция в списке, znachenie).
 */
public class SpiskiBD {

    // Одна запись таблицы
    static class Element {
        final int id;
        final String spisok;
        final int nomer;
        final String znachenie;

        Element(int id, String spisok, int nomer, String znachenie) {
            this.id = id;
            this.spisok = spisok;
            this.nomer = nomer;
            this.znachenie = znachenie;
        }
    }

    static List<Element> zagruzit(Baza b, String spisok) throws SQLException {
        List<Element> list = new ArrayList<>();
        ResultSet rs = b.stat.executeQuery("SELECT * FROM " + b.tablename
                + (spisok == null ? "" : " WHERE spisok = '" + spisok + "'") + " ORDER BY id");
        while (rs.next()) {
            list.add(new Element(rs.getInt("id"), rs.getString("spisok"), rs.getInt("nomer"), rs.getString("znachenie")));
        }
        rs.close();
        return list;
    }

    // Компактный вывод: список случайных чисел - по 10 элементов "ID:значение" в строке,
    // список строк - таблицей
    static void pechat(Baza b) throws SQLException {
        List<Element> chisla = zagruzit(b, "random");
        List<Element> stroki = zagruzit(b, "input");

        System.out.println("Список случайных чисел (random), элементов: " + chisla.size() + ", формат ID:значение");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chisla.size(); i++) {
            Element e = chisla.get(i);
            sb.append(String.format("%11s", e.id + ":" + e.znachenie));
            if ((i + 1) % 10 == 0 || i == chisla.size() - 1) {
                System.out.println(sb);
                sb.setLength(0);
            }
        }

        System.out.println("\nСписок строк (input), элементов: " + stroki.size());
        System.out.println("+--------+-----+------------------------------------------+");
        System.out.printf("| %-6s | %-3s | %-40s |%n", "ID", "№", "Значение");
        System.out.println("+--------+-----+------------------------------------------+");
        for (Element e : stroki) System.out.printf("| %-6d | %-3d | %-40s |%n", e.id, e.nomer, e.znachenie);
        System.out.println("+--------+-----+------------------------------------------+");
        if (!chisla.isEmpty() || !stroki.isEmpty()) {
            System.out.println(diapazonId(chisla, "random") + "; " + diapazonId(stroki, "input"));
        }
    }

    static String diapazonId(List<Element> list, String imya) {
        if (list.isEmpty()) return imya + ": пусто";
        return imya + ": ID от " + list.get(0).id + " до " + list.get(list.size() - 1).id;
    }
}
