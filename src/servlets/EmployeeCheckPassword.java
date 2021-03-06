package servlets;

import core.BCrypt;
import database.SQLQuerySelect;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Get employee encrypted password.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 20/06/20
 */
public class EmployeeCheckPassword extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String email = request.getParameter("email");
            if (SQLQuerySelect.isEmailInDatabase(email)) {
                String password = request.getParameter("password");
                if (email == "" || password == "") {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                String encryptedPassword = SQLQuerySelect.getEmployeePassword(email);
                if (!BCrypt.checkpw(password, encryptedPassword)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                HttpSession session = request.getSession(false);
                if(session != null) {
                    session.invalidate();
                }
                HttpSession newSession = request.getSession();
                newSession.setAttribute("email", email);
                newSession.setAttribute("user", "0");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
