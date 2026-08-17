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

@WebServlet("/answer")
public class AnswerServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String doubtId = request.getParameter("doubt_id");
        String answer = request.getParameter("answer");

        String url = "jdbc:postgresql://aws-0-ap-southeast-2.pooler.supabase.com:5432/postgres";
        String user = "postgres.gnqorukgtjgevltndcku";
        String dbPassword = "geetharani@2004";

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {

            Class.forName("org.postgresql.Driver");

            Connection con = DriverManager.getConnection(
                    url, user, dbPassword);

            String sql = "INSERT INTO answers (doubt_id, answer) VALUES (?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(doubtId));
            ps.setString(2, answer);

            ps.executeUpdate();

            ps.close();
            con.close();

            out.println("<h1>Answer Posted Successfully!</h1>");
            out.println("<p>Your answer has been saved.</p>");
            out.println("<a href='view-doubts'>View Doubts</a>");

        } catch (Exception e) {

            out.println("<h1>Answer Failed</h1>");
            out.println("<p>" + e.getMessage() + "</p>");

            e.printStackTrace();
        }
    }
}