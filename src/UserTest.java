import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UserTest {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://aws-0-ap-southeast-2.pooler.supabase.com:5432/postgres";
        String user = "postgres.gnqorukgtjgevltndcku";
        String password = "geetharani@2004";

        try {
            Connection con = DriverManager.getConnection(url, user, password);

            String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "Geetha");
            ps.setString(2, "geetha@gmail.com");
            ps.setString(3, "12345");

            ps.executeUpdate();

            System.out.println("User inserted successfully!");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
