package servlets;

import database.SQLQuery;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Base64;

public class GetEmployeeId extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader("authorization");
        String encodedAuth = authHeader.substring(authHeader.indexOf(' ') + 1);
        String decodedAuth = new String(Base64.getDecoder().decode(encodedAuth));
        String email = decodedAuth.substring(0, decodedAuth.indexOf('|'));
        String companyName = decodedAuth.substring(decodedAuth.indexOf('|') + 1);

        try {
            if (SQLQuery.isEmployeeInCompany(email, companyName)) {
                String encryptedPassword = SQLQuery.getEmployeeId(companyName, email);

                PrintWriter out = response.getWriter();
                out.println(encryptedPassword);
                out.close();
                out.flush();
                response.sendError(HttpServletResponse.SC_OK);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
