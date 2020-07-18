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
 * Servlet to add clock out time stamp.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 24/06/20
 */
public class ClockOut extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader("authorization");
        String encodedHeader = authHeader.substring(authHeader.indexOf(' ') + 1);
        String token = new String(Base64.getDecoder().decode(encodedHeader));

        try {
            String email = SQLQuerySelect.getEmployeeEmailFromToken(token);
            if (SQLQuerySelect.doesEmployeeHaveValidToken(token, email)) {
                if (SQLQuerySelect.isThereClockOutTSOfNull(email)) {
                    String timeStamp = request.getParameter("timestamp");
                    SQLQueryUpdate.updateClockOutTimeStamp(email, timeStamp);
                    response.sendError(HttpServletResponse.SC_OK);
                } else {
                    //Error: 461 no clock in value to pair with
                    response.sendError(461);
                    return;
                }
            } else {
                //Error: 406 invalid token
                response.sendError(406);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
