package servlets;

import database.SQLQuerySelect;
import database.SQLQueryUpdate;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

/**
 * Change company password.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 04/07/2020
 */
public class ChangeCompanyPassword extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader("authorization");
        String encodedToken = authHeader.substring(authHeader.indexOf(' ') + 1);
        String token = new String(Base64.getDecoder().decode(encodedToken));

        try {
            String compName = SQLQuerySelect.getCompanyNameFromToken(token);
            if (SQLQuerySelect.doesCompanyHaveValidToken(token, compName)) {
                String newPassword = request.getParameter("newPassword");
                SQLQueryUpdate.updateCompanyPassword(compName, newPassword);
                response.sendError(HttpServletResponse.SC_OK);
            } else {
                //Error: 406 invalid token
                response.sendError(406);
                return;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }
    }
}
