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
 * Servlet to add a company to the database.
 * Needs check that the company name doesn't include the character '|'.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 18/06/20
 */
public class CreateCompany extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader("authorization");
        String encodedAuth = authHeader.substring(authHeader.indexOf(' ') + 1);
        String decodedAuth = new String(Base64.getDecoder().decode(encodedAuth));
        String compEmail = decodedAuth.substring(0, decodedAuth.indexOf('|'));
        String compPassword = decodedAuth.substring(decodedAuth.indexOf('|') + 1);
        try {
            if (SQLQuerySelect.doesCompanyEmailExist(compEmail)) {
                //402: company already exists
                response.sendError(402);
                return;
            }
            String name = request.getParameter("companyName");

            SQLQueryInsert.addCompany(compEmail, name, compPassword);
            response.sendError(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            e.printStackTrace();
            //403: error adding company
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
