package servlets;

import database.SQLQueryDelete;
import database.SQLQuerySelect;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        String authHeader = request.getHeader("authorization");
        String encodedHeader = authHeader.substring(authHeader.indexOf(' ') + 1);
        String token = new String(Base64.getDecoder().decode(encodedHeader));

        try {
            String companyEmail = SQLQuerySelect.getCompanyEmailFromToken(token);
            if (SQLQuerySelect.doesCompanyHaveValidToken(token, companyEmail)) {
                String employeeEmail = request.getParameter("email");
                if (SQLQuerySelect.isEmployeeWithCompany(employeeEmail, companyEmail)) {
                    SQLQueryDelete.deleteEmployee(employeeEmail);
                    response.sendError(HttpServletResponse.SC_OK);
                } else {
                    //Error 403: Employee isn't with the company.
                    response.sendError(403);
                    return;
                }
            } else {
                //Error: 406 invalid token
                response.sendError(406);
                return;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
