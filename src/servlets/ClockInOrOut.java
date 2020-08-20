package servlets;

import database.SQLQueryInsert;
import database.SQLQuerySelect;
import database.SQLQueryUpdate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class ClockInOrOut extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            HttpSession session = request.getSession(false);
            String email = (String) session.getAttribute("email");
            response.setContentType("text/plain;charset=UTF-8");
            if (SQLQuerySelect.isThereClockOutTSOfNull(email)) {
                response.setStatus(270);
                String dateTime = SQLQuerySelect.getLatestClockIn(email);
                PrintWriter writer = response.getWriter();
                writer.print(dateTime);
                writer.flush();
                writer.close();
            } else {
                response.setStatus(271);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
