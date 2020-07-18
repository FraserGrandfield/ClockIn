package servlets;

import database.SQLQueryInsert;
import database.SQLQuerySelect;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Scanner;

/**
 * Servlet to add clock in time stamp.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 24/06/20
 */
public class ClockIn extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authHeader = request.getHeader("authorization");
        String authEncoded = authHeader.substring(authHeader.indexOf(' ') + 1);
        String token = new String(Base64.getDecoder().decode(authEncoded));

        String clockInTS = request.getParameter("timestamp");

        try {
            String email = SQLQuerySelect.getEmployeeEmailFromToken(token);
            if (SQLQuerySelect.doesEmployeeHaveValidToken(token, email)) {
                String timeStampId = email + clockInTS;

                if (SQLQuerySelect.isThereClockOutTSOfNull(email)) {
                    //Error: 460 There is already a clock in time needing a clock out pair
                    response.sendError(460);
                    return;
                }

                if (SQLQuerySelect.doesTimeStampIdExist(timeStampId)) {
                    //Error: 407 time stamp ID already exists
                    response.sendError(407);
                    return;
                }

                SQLQueryInsert.addClockInTimeStamp(timeStampId, email, clockInTS);
                response.sendError(HttpServletResponse.SC_OK);
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
