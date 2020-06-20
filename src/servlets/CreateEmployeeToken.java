package servlets;

import database.SQLQuery;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

/**
 * Servlet to create a token.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 19/06/20
 */
public class CreateEmployeeToken extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader("authorization");
        String encodedAuth = authHeader.substring(authHeader.indexOf(' ') + 1);
        String employeeIdString = new String(Base64.getDecoder().decode(encodedAuth));
        int employeeId = Integer.parseInt(employeeIdString);

        try {
            if (SQLQuery.doesEmployeeHaveToken(employeeId)) {
                SQLQuery.deleteOldEmployeeToken(employeeId);
            }

            String token = generateToken();
            LocalDateTime dateTime = LocalDateTime.now();
            dateTime = dateTime.plusMinutes(20);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dataTimeStr = dateTimeFormatter.format(dateTime);
            SQLQuery.addEmployeeToken(employeeId, token, dataTimeStr);
            PrintWriter out = response.getWriter();
            out.println(token);
            out.close();
            out.flush();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
        response.sendError(HttpServletResponse.SC_OK);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}
