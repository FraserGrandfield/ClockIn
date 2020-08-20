package servlets;

import database.SQLQueryDelete;
import database.SQLQuerySelect;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

/**
 * Servlet for a company to delete an employee.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 24/06/20
 */
public class CompanyDeleteEmployee extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            HttpSession session = request.getSession(false);
            String companyEmail = (String) session.getAttribute("email");

            String employeeEmail = request.getParameter("email");
            if (SQLQuerySelect.isEmployeeWithCompany(employeeEmail, companyEmail)) {
                SQLQueryDelete.deleteEmployee(employeeEmail);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                //Error 403: Employee isn't with the company.
                response.setStatus(403);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
