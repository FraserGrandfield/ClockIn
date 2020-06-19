package servlets;

import database.SQLQuery;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

/**
 * Servlet to create a employee and add them to the database.
 * @author Fraser Grandfield
 * @version 1.0
 * @Since 19/06/20
 */
public class CreateEmployee extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authHeader = request.getHeader("authorization");
        String encodedAuth = authHeader.substring(authHeader.indexOf(' ') + 1);
        String decodedAuth = new String(Base64.getDecoder().decode(encodedAuth));
        String token = decodedAuth.substring(0, decodedAuth.indexOf('|'));
        String password = decodedAuth.substring(decodedAuth.indexOf('|') + 1);

        String name = request.getParameter("name");
        String companyName = request.getParameter("companyname");
        String email = request.getParameter("email");

        try {
            if (!SQLQuery.doesCompanyHaveValidToken(token, companyName)) {
                //401: Client doesn't have a valid token
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }

        try {
            if (SQLQuery.isEmployeeInCompany(email, companyName)) {
                //402: Employee email is already with the company
                response.sendError(402);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }

        try {
            int employeeId = generateEmployeeId();
            SQLQuery.addEmployee(employeeId, name, email, password, companyName);
            response.sendError(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }

    /**
     * Generates ID for employee, gets current max Id + 1.
     * @return Id
     */
    //TODO throw the exception instead of catching it here
    private int generateEmployeeId() {
        boolean gotId = false;
        int maxId = -1;
        int count = 10;
        //Tries it ten times incase the Id it was gonna add had already been added.
        while (!gotId || count > 0) {
            try {
                if (SQLQuery.doesEmployeeInTableExist()) {
                    maxId = SQLQuery.getMaxEmployeeId();
                    maxId++;
                    gotId = true;
                } else {
                    maxId = 0;
                    gotId = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            count--;
        }
        return maxId;
    }
}
