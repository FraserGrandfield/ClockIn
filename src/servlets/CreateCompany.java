package servlets;

import core.BCrypt;
import database.SQLQueryInsert;
import database.SQLQuerySelect;
import org.apache.commons.validator.routines.EmailValidator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

/**
 * Servlet to add a company to the database.
 * Needs check that the company name doesn't include the character '|'.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 18/06/20
 */
public class CreateCompany extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            String name = request.getParameter("companyName");
            String firstPassword = new String(Base64.getDecoder().decode(request.getParameter("firstPassword")));
            String secondPassword = new String(Base64.getDecoder().decode(request.getParameter("secondPassword")));
            String compEmail = request.getParameter("email");

            EmailValidator validator = EmailValidator.getInstance();
            if (!(validator.isValid(compEmail))) {
                //482: Invalid email
                response.sendError(482);
                return;
            } else if (!firstPassword.equals(secondPassword)) {
                //480: Passwords don't match
                response.sendError(480);
                return;
            } else if (firstPassword.length() < 6) {
                //480: Password is too short.
                response.sendError(481);
                return;
            } else if (SQLQuerySelect.doesCompanyEmailExist(compEmail)) {
                //402: company already exists
                response.sendError(402);
                return;
            }

            String hashedPassword = BCrypt.hashpw(firstPassword, BCrypt.gensalt(12));

            SQLQueryInsert.addCompany(compEmail, name, hashedPassword);
            response.sendError(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            e.printStackTrace();
            //503: error adding company
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
