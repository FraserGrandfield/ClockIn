package servlets;

import core.BCrypt;
import database.SQLQueryInsert;
import database.SQLQuerySelect;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import org.apache.commons.validator.routines.*;

/**
 * Servlet to create a employee and add them to the database.
 * @author Fraser Grandfield
 * @version 1.0
 * @Since 19/06/20
 */
public class CreateEmployee extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        String email = request.getParameter("email");
        String firstPassword = new String(Base64.getDecoder().decode(request.getParameter("firstPassword")));
        String secondPassword = new String(Base64.getDecoder().decode(request.getParameter("secondPassword")));
        String pay = request.getParameter("pay");
        String token = request.getParameter("token");
        try {

            EmailValidator validator = EmailValidator.getInstance();
            if (!(validator.isValid(email))) {
                //482: Invalid email
                response.sendError(482);
                return;
            } else if (!firstPassword.equals(secondPassword)) {
                //480: Passwords don't match
                response.sendError(480);
                return;
            } else if (firstPassword.length() < 6){
                //480: Password is too short.
                response.sendError(481);
                return;
            } else if (!SQLQuerySelect.doesEmployeeHaveCreateAccountValidToken(token)) {
                //401: Client doesn't have a valid token
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } else if (SQLQuerySelect.isEmailInDatabase(email)) {
                //402: Employee email is already with the company
                response.sendError(402);
                return;
            }

            String companyEmail = SQLQuerySelect.getCompanyEmailFromCreateEmployeeToken(token);
            String hashedPassword = BCrypt.hashpw(firstPassword, BCrypt.gensalt(12));

            SQLQueryInsert.addEmployee(email, firstName, secondName, hashedPassword, companyEmail, pay);
            response.sendError(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
