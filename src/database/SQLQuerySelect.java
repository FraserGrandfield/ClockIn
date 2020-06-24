package database;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * SQL query class for selecting from the database.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 24/06/20
 */
public class SQLQuerySelect {

    /**
     * Check if company name is in database.
     * @param compName name of the company.
     * @return boolean true if company exists.
     * @throws SQLException
     */
    public synchronized static boolean doesCompanyNameExist(String compName) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT companieName FROM companies.companies WHERE companieName = '%s';", compName));
        String names = "";

        while (resultSet.next()) {
            names = resultSet.getString(1);
        }
        return names.equals(compName);
    }

    /**
     * Checks if an employee already has a token.
     * @param email
     * @return boolean true if they already have a token.
     * @throws SQLException
     */
    public synchronized static boolean doesEmployeeHaveToken(String email) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT email FROM companies.token WHERE email = '%s';", email));

        String name = "";
        while (resultSet.next()) {
            name = resultSet.getString(1);
        }

        return (name.equals(email));
    }

    /**
     * Checks if there are any fields in the employee table.
     * @return boolean true if there are fields in the employee table.
     * @throws SQLException
     */
    public synchronized static boolean doesEmployeeInTableExist() throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT email FROM companies.employees;");

        String temp = "";

        //TODO make code netter
        int i = 0;
        while (resultSet.next() && i == 0) {
            temp = resultSet.getString(1);
            i++;
        }

        return (!temp.equals(""));
    }

    /**
     * Checks if the company logged in has a valid token
     * @param token token.
     * @param companyName company name.
     * @return boolean true if company has a valid token.
     * @throws SQLException
     */
    public synchronized static boolean doesCompanyHaveValidToken(String token, String companyName) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM companies.tokencompany WHERE companyname = '%s';", companyName));

        String tempToken = "";
        String dataTime = "";
        while (resultSet.next()) {
            tempToken = resultSet.getString(2);
            dataTime = resultSet.getString(3);
        }

        if (!token.equals(tempToken)) {
            //Delete token
            statement.execute(String.format("DELETE FROM companies.tokencompany WHERE companyname = '%s';", companyName));
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime tokenTime = LocalDateTime.parse(dataTime, dateTimeFormatter);
        if (currentTime.isAfter(tokenTime)) {
            statement.execute(String.format("DELETE FROM companies.tokencompany WHERE companyname = '%s';", companyName));
            return false;
        }
        return true;
    }

    /**
     * Checks if the company has a token in the database.
     * @param companyName company name.
     * @return boolean true if the company has a token.
     * @throws SQLException
     */
    public synchronized static boolean doesCompanyHaveToken(String companyName) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT companyname FROM companies.tokencompany WHERE companyname = '%s';", companyName));

        String tempName = "";
        while (resultSet.next()) {
            tempName = resultSet.getString(1);
        }

        return tempName.equals(companyName);
    }

    /**
     * Gets the encrypted password of the company.
     * @param companyName company name.
     * @return String encrypted password.
     * @throws SQLException
     */
    public synchronized static String getCompanyPassword(String companyName) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT companiePassword FROM companies.companies WHERE companieName = '%s';", companyName));

        String temp = "";
        while (resultSet.next()) {
            temp = resultSet.getString(1);
        }
        return temp;
    }

    /**
     * Gets the encrypted password of the employee.
     * @param email employee email.
     * @return String encrypted password of employee.
     * @throws SQLException
     */
    public synchronized static String getEmployeePassword(String email) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT password FROM companies.employees WHERE email = '%s';", email));

        String temp = "";
        while (resultSet.next()) {
            temp = resultSet.getString(1);
        }
        return temp;
    }

    /**
     * Checks if a company has a create employee token.
     * @param companyName company name.
     * @return boolean true if the company has a token.
     * @throws SQLException
     */
    public synchronized static boolean doesCompanyHaveCreateEmployeeToken(String companyName) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT companyName FROM companies.createemployeetoken WHERE companyName = '%s';", companyName));

        String tempName = "";
        while (resultSet.next()) {
            tempName = resultSet.getString(1);
        }

        return tempName.equals(companyName);
    }

    /**
     * Checks if an employee has a valid token to create an account.
     * @param token token.
     * @return boolean true if the employee has a valid token.
     * @throws SQLException
     */
    public synchronized static boolean doesEmployeeHaveCreateAccountValidToken(String token) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM companies.createemployeetoken WHERE token = '%s';", token));

        String tempToken = "";
        String dataTime = "";
        while (resultSet.next()) {
            tempToken = resultSet.getString(2);
            dataTime = resultSet.getString(3);
        }
        if (!token.equals(tempToken)) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime tokenTime = LocalDateTime.parse(dataTime, dateTimeFormatter);

        return (tokenTime.isAfter(currentTime));
    }

    /**
     * Gets the company name from create employee token.
     * @param token token.
     * @return String company name.
     * @throws SQLException
     */
    public synchronized static String getCompanyNameFromCreateEmployeeToken(String token) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT companyName FROM companies.createemployeetoken WHERE token = '%s';", token));

        String compName = "";
        while (resultSet.next()) {
            compName = resultSet.getString(1);
        }
        return compName;
    }

    /**
     * Checks if the employees token is unique.
     * @param token token.
     * @return boolean true if the token is unique.
     * @throws SQLException
     */
    public synchronized static boolean isCreateEmployeeTokenUnique(String token) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT token FROM companies.createemployeetoken WHERE token = '%s';", token));

        String temp = "";
        while (resultSet.next()) {
            temp = resultSet.getString(1);
        }

        return !(temp.equals(token));
    }

    /**
     * Checks if an email is in the employees table.
     * @param email employees email.
     * @return boolean true if the email is in the table.
     * @throws SQLException
     */
    public synchronized static boolean isEmailInDatabase(String email) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT email FROM companies.employees WHERE email = '%s';", email));

        String temp;
        for(temp = ""; resultSet.next(); temp = resultSet.getString(1)) {
        }

        return temp.equals(email);
    }

    /**
     * Checks if the employee has a valid token.
     * @param token token.
     * @return boolean true if the employee has a valid token.
     * @throws SQLException
     */
    public synchronized static boolean doesEmployeeHaveValidToken(String token) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM companies.token WHERE token = '%s';", token));

        String tempToken = "";
        String tempDateTime = "";
        while (resultSet.next()) {
            tempToken = resultSet.getString(2);
            tempDateTime = resultSet.getString(3);
        }

        if (!token.equals(tempToken)) {
            return false;
        }
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime tokenTime = LocalDateTime.parse(tempDateTime, dateTimeFormatter);
        return (tokenTime.isAfter(currentTime));
    }

    /**
     * Gets the employees emailf from the token table.
     * @param token token.
     * @return String the employees email.
     * @throws SQLException
     */
    public synchronized static String getEmployeeEmailFromToken(String token) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT email FROM companies.token WHERE token = '%s';", token));
        String temp = "";
        while (resultSet.next()) {
            temp = resultSet.getString(1);
        }
        return temp;
    }

    /**
     * Checks if a time stamp Id exists in the timestamps table.
     * @param timeStampId time stamp Id.
     * @return boolean true if the Id exists in the timestamps table.
     * @throws SQLException
     */
    public synchronized static boolean doesTimeStampIdExist(String timeStampId) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT timestampID FROM companies.timestamps WHERE timestampID = '%s';", timeStampId));

        String temp;
        for(temp = ""; resultSet.next(); temp = resultSet.getString(1)) {
        }

        return temp.equals(timeStampId);
    }

    /**
     * Checks if there is a clock in timestamp without a clock out timestamp.
     * @param email employee email.
     * @return boolean true if the employee can add a clock out timestamp.
     * @throws SQLException
     */
    public synchronized static boolean isThereClockOutTSOfNull(String email) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM companies.timestamps WHERE email = '%s' AND clockOut IS NULL;", email));

        String tempClockOut = "";
        String tempEmail = "";
        while (resultSet.next()) {
            tempClockOut = resultSet.getString(4);
            tempEmail = resultSet.getString(2);
        }
        return (tempClockOut == null && !tempEmail.equals(""));
    }
}
