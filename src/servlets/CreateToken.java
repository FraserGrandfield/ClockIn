package servlets;

import database.SQLQuery;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
public class CreateToken extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader("authorization");
        String encodedAuth = authHeader.substring(authHeader.indexOf(' ') + 1);
        int employeeId = new Integer(String.valueOf(Base64.getDecoder().decode(encodedAuth)));

        try {
            if (SQLQuery.doesEmployeeHaveToken(employeeId)) {
                SQLQuery.deleteOldToken(employeeId);
            }
            String token = generateToken();
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dataTimeStr = dateTimeFormatter.format(dateTime);
            SQLQuery.addToken(employeeId, token, dataTimeStr);
            response.getOutputStream().print(token);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        response.sendError(HttpServletResponse.SC_OK);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

}
