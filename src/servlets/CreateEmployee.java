package servlets;

import database.SQLQueryInsert;
import database.SQLQuerySelect;

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

        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        String email = request.getParameter("email");
        String pay = request.getParameter("pay");

        try {

            if (!SQLQuerySelect.doesEmployeeHaveCreateAccountValidToken(token)) {
                //401: Client doesn't have a valid token
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String companyEmail = SQLQuerySelect.getCompanyEmailFromCreateEmployeeToken(token);

            if (SQLQuerySelect.isEmailInDatabase(email)) {
                //402: Employee email is already with the company
                response.sendError(402);
                return;
            }

            SQLQueryInsert.addEmployee(email, firstName, secondName, password, companyEmail, pay);
            response.sendError(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
