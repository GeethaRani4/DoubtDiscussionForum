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

@WebServlet("/view-doubts")
public class ViewDoubtsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String url = "jdbc:postgresql://aws-0-ap-southeast-2.pooler.supabase.com:5432/postgres";
        String user = "postgres.gnqorukgtjgevltndcku";
        String password = "geetharani@2004";

        try {
            Class.forName("org.postgresql.Driver");

            Connection con = DriverManager.getConnection(url, user, password);

            String sql = "SELECT id, question, name FROM doubts ORDER BY id DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>All Doubts</title>");

            out.println("<style>");
            out.println("body { font-family: Arial; background:#f4f6f8; padding:30px; }");
            out.println(".doubt { background:white; padding:20px; margin-bottom:25px; border-radius:10px; }");
            out.println(".answer { background:#eef6ff; padding:12px; margin-top:10px; border-radius:8px; }");
            out.println("h1 { color:#26384a; }");
            out.println("h2 { color:#222; }");
            out.println("</style>");

            out.println("</head>");
            out.println("<body>");

            out.println("<h1>All Doubts</h1>");

            while (rs.next()) {

                int doubtId = rs.getInt("id");
                String question = rs.getString("question");
                String name = rs.getString("name");

                out.println("<div class='doubt'>");

                out.println("<h2>" + question + "</h2>");
                out.println("<p><b>Posted by:</b> " + name + "</p>");

                // Get answers for this doubt
                String answerSql = "SELECT answer FROM answers WHERE doubt_id = ? ORDER BY id";

                PreparedStatement aps = con.prepareStatement(answerSql);
                aps.setInt(1, doubtId);

                ResultSet ars = aps.executeQuery();

                out.println("<h3>Answers:</h3>");

                boolean hasAnswer = false;

                while (ars.next()) {

                    hasAnswer = true;

                    out.println("<div class='answer'>");
                    out.println("<p>" + ars.getString("answer") + "</p>");
                    out.println("</div>");
                }

                if (!hasAnswer) {
                    out.println("<p>No answers yet.</p>");
                }

                ars.close();
                aps.close();

                out.println("</div>");
            }

            out.println("</body>");
            out.println("</html>");

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            out.println("<h1>Error</h1>");
            out.println("<p>" + e.getMessage() + "</p>");

            e.printStackTrace();
        }
    }
}