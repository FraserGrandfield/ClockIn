package servlets;

import database.SQLQueryUpdate;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Change company password.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 04/07/2020
 */
public class ChangeCompanyPassword extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession(false);
            String compEmail = (String) session.getAttribute("email");
            String newPassword = request.getParameter("newPassword");
            SQLQueryUpdate.updateCompanyPassword(compEmail, newPassword);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
    }
}
