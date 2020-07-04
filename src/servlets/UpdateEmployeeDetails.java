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
 * Servlet to update employee details (name and email).
 * @author Fraser Grandfield
 * @version 1.0
 * @since 24/06/20
 */
public class UpdateEmployeeDetails extends HttpServlet {
//TODO test this servlet
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader("authorization");
        String encodedHeader = authHeader.substring(authHeader.indexOf(' ') + 1);
        String token = new String(Base64.getDecoder().decode(encodedHeader));

        try {
            if (SQLQuerySelect.doesEmployeeHaveValidToken(token)) {
                String oldEmail = SQLQuerySelect.getEmployeeEmailFromToken(token);
                String newEmail = request.getParameter("email");
                String newName = request.getParameter("name");
                SQLQueryUpdate.updateEmployeeDetails(oldEmail, newEmail, newName);
                response.sendError(HttpServletResponse.SC_OK);
            } else {
                //Error: 406 invalid token
                response.sendError(406);
                return;            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
