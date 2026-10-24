import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/** Пункт меню 3: создание двух списков (random и input) и сохранение их в БД. */
public class VvodSpiska extends PunktMenu {

    public VvodSpiska(Baza b) {
        super(b);
    }

    @Override
    public String nazvanie() {
        return "Ввести список и сохранить в PostgreSQL";
    }

    @Override
    public void vypolnit() throws SQLException {
        if (!b.estTabl()) return;

        Spiski spiski = new Spiski();
        spiski.zapolnit(b.scan);
        List<Integer> chisla = spiski.getChisla();
        List<String> stroki = spiski.getStroki();

        // Новые списки заменяют старые; сохраняем одной транзакцией
        b.conn.setAutoCommit(false);
        try {
            b.stat.executeUpdate("DELETE FROM " + b.tablename);
            PreparedStatement ps = b.conn.prepareStatement("INSERT INTO " + b.tablename
                    + " (spisok, nomer, znachenie) VALUES (?,?,?)");
            for (int i = 0; i < chisla.size(); i++) {
                ps.setString(1, "random");
                ps.setInt(2, i + 1);
                ps.setString(3, String.valueOf(chisla.get(i)));
                ps.addBatch();
            }
            for (int i = 0; i < stroki.size(); i++) {
                ps.setString(1, "input");
                ps.setInt(2, i + 1);
                ps.setString(3, stroki.get(i));
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
            b.conn.commit();
        } catch (SQLException e) {
            b.conn.rollback();
            throw e;
        } finally {
            b.conn.setAutoCommit(true);
        }

        System.out.println("\nСписки сохранены в таблицу '" + b.tablename + "': "
                + chisla.size() + " чисел и " + stroki.size() + " строк\n");
        SpiskiBD.pechat(b);
    }
}
