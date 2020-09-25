package servlets;

import core.BCrypt;
import database.SQLQueryUpdate;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Change employees password.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 04/07/2020
 */
public class ChangeEmployeePassword extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");
            String newPassword1 = request.getParameter("newPassword1");
            String newPassword2 = request.getParameter("newPassword2");
            if (email == "" || newPassword1 == "" || newPassword2 == "") {
                response.setStatus(475);
                return;
            }
            if (!newPassword1.equals(newPassword2)) {
                response.setStatus(471);
                return;
            }
            if (newPassword1.length() < 6) {
                response.setStatus(472);
                return;
            }
            String hashedPassword = BCrypt.hashpw(newPassword1, BCrypt.gensalt(12));
            SQLQueryUpdate.updateEmployeePassword(email, hashedPassword);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
    }
}
