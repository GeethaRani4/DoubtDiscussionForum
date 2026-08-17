import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public static Connection getConnection() throws Exception {

        String url = "jdbc:postgresql://aws-0-ap-southeast-2.pooler.supabase.com:5432/postgres";
        String user = "postgres.gnqorukgtjgevltndcku";
        String password = "geetharani@2004";

        return DriverManager.getConnection(url, user, password);
    }
}