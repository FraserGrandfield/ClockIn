package servlets;

import core.BCrypt;
import database.SQLQuerySelect;

import javax.jms.Session;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.UUID;

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
                String encryptedPassword = SQLQuerySelect.getEmployeePassword(email);
                String password = request.getParameter("password");

                if (!BCrypt.checkpw(password, encryptedPassword)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                HttpSession session = request.getSession(false);
                if(session != null) {
                    session.invalidate();
                    HttpSession newSession = request.getSession();
                } else {
                    HttpSession newSession = request.getSession();
                }
                session.setAttribute("email", email);
                response.sendError(HttpServletResponse.SC_OK);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

}
