import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String url = "jdbc:postgresql://aws-0-ap-southeast-2.pooler.supabase.com:5432/postgres";
        String user = "postgres.gnqorukgtjgevltndcku";
        String dbPassword = "geetharani@2004";

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        try {

            Class.forName("org.postgresql.Driver");

            Connection con = DriverManager.getConnection(url, user, dbPassword);

            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                out.println("<h1>Login Successful!</h1>");
                out.println("<p>Welcome, " + rs.getString("name") + "!</p>");

            } else {

                out.println("<h1>Login Failed</h1>");
                out.println("<p>Invalid email or password.</p>");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            out.println("<h1>Login Failed</h1>");
            out.println("<p>" + e.getMessage() + "</p>");

            e.printStackTrace();
        }
    }
}