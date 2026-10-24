import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Пункт меню 4: удаление элемента из списка по ID. */
public class UdalenieElementa extends PunktMenu {

    public UdalenieElementa(Baza b) {
        super(b);
    }

    @Override
    public String nazvanie() {
        return "Удалить элемент из списка в PostgreSQL по ID";
    }

    @Override
    public void vypolnit() throws SQLException {
        if (!b.estTabl()) return;
        System.out.println(SpiskiBD.diapazonId(SpiskiBD.zagruzit(b, "random"), "random") + "; "
                + SpiskiBD.diapazonId(SpiskiBD.zagruzit(b, "input"), "input"));

        int id = b.chitatCeloe("Введите ID элемента для удаления: ");
        PreparedStatement ps = b.conn.prepareStatement("DELETE FROM " + b.tablename
                + " WHERE id = ? RETURNING spisok, nomer, znachenie");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println("Удалён элемент ID " + id + " из списка " + rs.getString("spisok")
                    + " (позиция " + rs.getInt("nomer") + ", значение '" + rs.getString("znachenie") + "')");
        } else {
            System.out.println("Элемент с ID " + id + " не найден");
        }
        rs.close();
        ps.close();

        ResultSet cnt = b.stat.executeQuery("SELECT spisok, COUNT(*) FROM " + b.tablename + " GROUP BY spisok ORDER BY spisok DESC");
        System.out.print("Осталось элементов:");
        while (cnt.next()) System.out.print(" " + cnt.getString(1) + " - " + cnt.getInt(2) + ";");
        System.out.println();
        cnt.close();
    }
}
