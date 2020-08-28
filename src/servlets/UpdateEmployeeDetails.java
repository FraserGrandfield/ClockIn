package servlets;

import database.SQLQueryUpdate;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet to update employee details (name and email).
 * @author Fraser Grandfield
 * @version 1.0
 * @since 24/06/20
 */
public class UpdateEmployeeDetails extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession(false);
            String oldEmail = (String) session.getAttribute("email");
                String newEmail = request.getParameter("email");
                String newFName = request.getParameter("firstName");
                String newSName = request.getParameter("secondName");
                String newPay = request.getParameter("pay");

                SQLQueryUpdate.updateEmployeeDetails(oldEmail, newEmail, newFName, newSName, newPay);
                response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
