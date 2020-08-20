package servlets;

import core.BCrypt;
import database.SQLQueryInsert;
import database.SQLQuerySelect;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        PrintWriter writer = response.getWriter();

        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        String email = request.getParameter("email");
        String firstPassword = request.getParameter("firstPassword");
        String secondPassword = request.getParameter("secondPassword");
        String pay = request.getParameter("pay");
        String token = request.getParameter("token");
        try {

            EmailValidator validator = EmailValidator.getInstance();
            if (!(validator.isValid(email))) {
                WriteError("Error: Email is invalid.", writer);
                return;
            } else if (!firstPassword.equals(secondPassword)) {
                WriteError("Error: Passwords do not match.", writer);
                return;
            } else if (firstPassword.length() < 6){
                WriteError("Error: Password is too short.", writer);
                return;
            } else if (!SQLQuerySelect.doesEmployeeHaveCreateAccountValidToken(token)) {
                WriteError("Error: Incorrect company token.", writer);
                return;
            } else if (SQLQuerySelect.isEmailInDatabase(email)) {
                WriteError("Error: Email is already registered with that company.", writer);
                return;
            }

            String companyEmail = SQLQuerySelect.getCompanyEmailFromCreateEmployeeToken(token);
            String hashedPassword = BCrypt.hashpw(firstPassword, BCrypt.gensalt(12));

            SQLQueryInsert.addEmployee(email, firstName, secondName, hashedPassword, companyEmail, pay);
            response.sendRedirect("index.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            WriteError("Error: Currently having issues communicating to the server.", writer);
            return;
        }
    }

    private static void WriteError(String error, PrintWriter writer) {
        writer.println("<script type=\"text/javascript\">");
        writer.println("alert('" + error + "');");
        writer.println("location='index.jsp';");
        writer.println("</script>");
    }
}
