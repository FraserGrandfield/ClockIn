package servlets;

import database.SQLQueryUpdate;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class UpdateEmployeeDetailsAsCompany extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String oldEmail = request.getParameter("oldEmail");
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
