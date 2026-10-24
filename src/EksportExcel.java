import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

/** Пункт меню 5: выгрузка обоих списков из БД в Excel (CSV) и вывод в консоль. */
public class EksportExcel extends PunktMenu {

    public EksportExcel(Baza b) {
        super(b);
    }

    @Override
    public String nazvanie() {
        return "Сохранить итоговые результаты из PostgreSQL в Excel и вывести их в консоль";
    }

    @Override
    public void vypolnit() throws SQLException {
        if (!b.estTabl()) return;
        File file = new File(b.papkaDlyaFaila(), b.tablename + ".csv");
        List<SpiskiBD.Element> vse = SpiskiBD.zagruzit(b, null);

        // CSV с BOM и разделителем ';' - Excel открывает его с правильной кодировкой
        try (Writer w = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            w.write('﻿');
            w.write("ID;Список;Позиция;Значение\n");
            for (SpiskiBD.Element e : vse) {
                w.write(e.id + ";" + e.spisok + ";" + e.nomer + ";" + b.csv(e.znachenie) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла: " + e.getMessage());
            return;
        }

        System.out.println("\n=== Содержимое таблицы '" + b.tablename + "' ===");
        if (vse.isEmpty()) System.out.println("Таблица пуста");
        else SpiskiBD.pechat(b);
        System.out.println("Данные сохранены в Excel-файл: " + file.getAbsolutePath() + " (записей: " + vse.size() + ")");
        b.otkrytFail(file);
    }
}
