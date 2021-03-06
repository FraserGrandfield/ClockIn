package servlets;

import database.SQLQuerySelect;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Servlet to get createCompany token.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 01/09/2020
 */
public class GetCompanyToken extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        try {
            String token = SQLQuerySelect.getCreateEmployeeToken(email);
            System.out.println(token);
            PrintWriter writer = response.getWriter();
            writer.print(token);
            writer.flush();
            writer.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
