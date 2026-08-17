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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
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

            String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.executeUpdate();

            ps.close();
            con.close();

            out.println("<h1>Registration Successful!</h1>");
            out.println("<p>Name: " + name + "</p>");
            out.println("<p>Email: " + email + "</p>");

        } catch (Exception e) {

            out.println("<h1>Registration Failed</h1>");
            out.println("<p>" + e.getMessage() + "</p>");

            e.printStackTrace();
        }
    }
}