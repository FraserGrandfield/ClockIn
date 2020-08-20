package servlets;

import database.SQLQuerySelect;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class GetShiftByDay extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);
        String email= (String) session.getAttribute("email");
        String dateString = request.getParameter("date");
        String dateString2 = LocalDate.parse(dateString).plusDays(1).toString();

        dateString += " 00:00";
        dateString2 += " 00:00";

        System.out.println(dateString + " " + dateString2);
        response.setContentType("text/plain;charset=UTF-8");

        try {
            ArrayList<ArrayList<String>> list = SQLQuerySelect.getShiftByDay(email, dateString, dateString2);
            String out = "";
            for (int i = 0; i < list.size(); i++) {
                out += list.get(i).get(0);
                out += list.get(i).get(1) + " ";
            }
            PrintWriter writer = response.getWriter();
            System.out.println(out);
            writer.print(out);
            writer.flush();
            writer.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}
