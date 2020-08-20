package servlets;

import database.SQLQueryInsert;
import database.SQLQuerySelect;
import database.SQLQueryUpdate;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        String timeStamp = request.getParameter("timestamp");

        try {
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");

            String timeStampId = email + timeStamp;

            if (SQLQuerySelect.isThereClockOutTSOfNull(email)) {
                //TODO check clock out cant be behind clock in
                String timestampOut = request.getParameter("timestampOut");
                LocalDateTime localDateTime = LocalDateTime.parse(timeStamp).plusDays(1);
                LocalDateTime localDateTimeOut = LocalDateTime.parse(timestampOut).plusDays(1);
                if (localDateTime.isAfter(localDateTimeOut)) {
                    response.setStatus(274);
                    return;
                } else {
                    SQLQueryUpdate.updateClockOutTimeStamp(email, timeStamp, timestampOut);
                    response.setStatus(272);
                    return;
                }
            }

            if (SQLQuerySelect.doesTimeStampIdExist(timeStampId)) {
                //Error: 407 time stamp ID already exists
                response.setStatus(407);
                return;
            }

            SQLQueryInsert.addClockInTimeStamp(timeStampId, email, timeStamp);
            response.setStatus(273);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
