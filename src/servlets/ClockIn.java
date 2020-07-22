package servlets;

import database.SQLQueryInsert;
import database.SQLQuerySelect;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

        String clockInTS = request.getParameter("timestamp");

        try {
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");

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
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
