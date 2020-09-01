package servlets;

import core.BCrypt;
import database.SQLQuerySelect;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Retrieves companies encrypted password.
 * Need to get token if password correct when logging in.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 19/06/20
 */
public class CompanyCheckPassword extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String companyEmail = request.getParameter("email");
            if (SQLQuerySelect.doesCompanyEmailExist(companyEmail)) {
                String encryptedPassword = SQLQuerySelect.getCompanyPassword(companyEmail);
                String password = request.getParameter("password");
                if (!BCrypt.checkpw(password, encryptedPassword)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
                HttpSession session = request.getSession(false);
                if(session != null) {
                    session.invalidate();
                }
                HttpSession newSession = request.getSession();
                newSession.setAttribute("email", companyEmail);
                newSession.setAttribute("user", "1");
                response.setStatus(HttpServletResponse.SC_OK);
                response.sendRedirect("DashBoard.jsp");
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
