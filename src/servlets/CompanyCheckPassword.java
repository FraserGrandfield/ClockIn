package servlets;

import core.BCrypt;
import database.SQLQuerySelect;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Base64;

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
        String authHeader = request.getHeader("authorization");
        String encodedAuth = authHeader.substring(authHeader.indexOf(' ') + 1);
        String companyEmail = new String(Base64.getDecoder().decode(encodedAuth));

        try {
            if (SQLQuerySelect.doesCompanyEmailExist(companyEmail)) {
                String encryptedPassword = SQLQuerySelect.getCompanyPassword(companyEmail);

                String password = request.getParameter("password");

                if (!BCrypt.checkpw(password, encryptedPassword)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                response.sendError(HttpServletResponse.SC_OK);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
