package database;

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
public class SQLQuerySelect extends SQLQuery{

    /**
     * Check if company email is in database.
     * @param companyEmail email of the company.
     * @return boolean true if company exists.
     * @throws SQLException
     */
    public synchronized static boolean doesCompanyEmailExist(String companyEmail) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT " + companyEmailPK + " FROM " + databaseName + "." + tableCompany + " WHERE " + companyEmailPK + " = '%s';", companyEmail));
        String names = "";

        while (resultSet.next()) {
            names = resultSet.getString(1);
        }
        return names.equals(companyEmail);
    }

    /**
     * Checks if an employee already has a token.
     * @param email
     * @return boolean true if they already have a token.
     * @throws SQLException
     */
    public synchronized static boolean doesEmployeeHaveToken(String email) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT " + tokenEmployeeEmailPKFK + " FROM " + databaseName + "." + tableToken + " WHERE " + tokenEmployeeEmailPKFK + " = '%s';", email));

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
        ResultSet resultSet = statement.executeQuery("SELECT " + employeeEmailPK + " FROM " + databaseName + "." + tableEmployees + ";");

        String temp = "";

        //TODO make code neater
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
     * @param companyEmail company email.
     * @return boolean true if company has a valid token.
     * @throws SQLException
     */
    public synchronized static boolean doesCompanyHaveValidToken(String token, String companyEmail) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM " + databaseName + "." + tableTokenCompany + " WHERE " + tokenCompanyEmailPKFK + " = '%s';", companyEmail));

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
        if (currentTime.isAfter(tokenTime)) {
            SQLQueryDelete.deleteOldCompanyToken(companyEmail);
            return false;
        }
        return true;
    }

    /**
     * Checks if the company has a token in the database.
     * @param companyEmail company email.
     * @return boolean true if the company has a token.
     * @throws SQLException
     */
    public synchronized static boolean doesCompanyHaveToken(String companyEmail) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT " + tokenCompanyEmailPKFK + " FROM " + databaseName + "." + tableTokenCompany + " WHERE " + tokenCompanyEmailPKFK + " = '%s';", companyEmail));

        String tempName = "";
        while (resultSet.next()) {
            tempName = resultSet.getString(1);
        }

        return tempName.equals(companyEmail);
    }

    /**
     * Gets the encrypted password of the company.
     * @param companyEmail company email.
     * @return String encrypted password.
     * @throws SQLException
     */
    public synchronized static String getCompanyPassword(String companyEmail) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT " + companyPassword + " FROM " + databaseName + "." + tableCompany + " WHERE " + companyEmailPK + " = '%s';", companyEmail));

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
        ResultSet resultSet = statement.executeQuery(String.format("SELECT " + employeePassword + " FROM " + databaseName + "." + tableEmployees + " WHERE " + employeeEmailPK + " = '%s';", email));

        String temp = "";
        while (resultSet.next()) {
            temp = resultSet.getString(1);
        }
        return temp;
    }

    /**
     * Checks if a company has a create employee token.
     * @param companyEmail company email.
     * @return boolean true if the company has a token.
     * @throws SQLException
     */
    public synchronized static boolean doesCompanyHaveCreateEmployeeToken(String companyEmail) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT " + createEmployeeCompanyEmailPKFK + " FROM " + databaseName + "." + tableCreateEmployeeToken + " WHERE " + createEmployeeCompanyEmailPKFK + " = '%s';", companyEmail));

        String tempName = "";
        while (resultSet.next()) {
            tempName = resultSet.getString(1);
        }
        return tempName.equals(companyEmail);
    }

    /**
     * Checks if an employee has a valid token to create an account.
     * @param token token.
     * @return boolean true if the employee has a valid token.
     * @throws SQLException
     */
    public synchronized static boolean doesEmployeeHaveCreateAccountValidToken(String token) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM " + databaseName + "." + tableCreateEmployeeToken + " WHERE " + createEmployeeToken + " = '%s';", token));

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
     * Gets the company email from create employee token.
     * @param token token.
     * @return String company email.
     * @throws SQLException
     */
    public synchronized static String getCompanyEmailFromCreateEmployeeToken(String token) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT " + createEmployeeCompanyEmailPKFK + " FROM " + databaseName + "." + tableCreateEmployeeToken + " WHERE " + createEmployeeToken + " = '%s';", token));

        String compEmail = "";
        while (resultSet.next()) {
            compEmail = resultSet.getString(1);
        }
        return compEmail;
    }

    /**
     * Checks if the employees token is unique.
     * @param token token.
     * @return boolean true if the token is unique.
     * @throws SQLException
     */
    //TODO as its a uuid no longer need to check if its unique
    public synchronized static boolean isCreateEmployeeTokenUnique(String token) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT " + createEmployeeToken + " FROM " + databaseName + "." + tableCreateEmployeeToken + " WHERE " + createEmployeeToken + " = '%s';", token));

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
        ResultSet resultSet = statement.executeQuery(String.format("SELECT " + employeeEmailPK + " FROM " + databaseName + "." + tableEmployees + " WHERE " + employeeEmailPK + " = '%s';", email));

        String temp;
        for(temp = ""; resultSet.next(); temp = resultSet.getString(1)) {
        }

        return temp.equals(email);
    }

    /**
     * Checks if the employee has a valid token and deletes the old one if not.
     * @param token token.
     * @return boolean true if the employee has a valid token.
     * @throws SQLException
     */
    public synchronized static boolean doesEmployeeHaveValidToken(String token, String email) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM " + databaseName + "." + tableToken + " WHERE " + employeeToken + " = '%s';", token));

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
        if (currentTime.isAfter(tokenTime)) {
            SQLQueryDelete.deleteOldEmployeeToken(email);
            return false;
        }
        return true;
    }

    /**
     * Gets the employees email from the token table.
     * @param token token.
     * @return String the employees email.
     * @throws SQLException
     */
    public synchronized static String getEmployeeEmailFromToken(String token) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT " + tokenEmployeeEmailPKFK + " FROM " + databaseName + "." + tableToken + " WHERE " + employeeToken + " = '%s';", token));
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
        ResultSet resultSet = statement.executeQuery(String.format("SELECT " + timestampIDPK + " FROM " + databaseName + "." + tableTimeStamps + " WHERE " + timestampIDPK + " = '%s';", timeStampId));

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
        ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM " + databaseName + "." + tableTimeStamps + " WHERE " + timeStampEmployeeEmailFK + " = '%s' AND " + clockOut + " IS NULL;", email));

        String tempClockOut = "";
        String tempEmail = "";
        while (resultSet.next()) {
            tempClockOut = resultSet.getString(4);
            tempEmail = resultSet.getString(2);
        }
        return (tempClockOut == null && !tempEmail.equals(""));
    }

    /**
     * Gets the company email from a company token.
     * @param token token.
     * @return String the company email.
     * @throws SQLException
     */
    public synchronized static String getCompanyEmailFromToken(String token) throws SQLException {
        Statement statement= DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT " + tokenCompanyEmailPKFK + " FROM " + databaseName + "." + tableTokenCompany + " WHERE " + companyToken + " = '%s';", token));

        String temp = "";
        while (resultSet.next()) {
            temp = resultSet.getString(1);
        }
        return temp;
    }

    /**
     * Checks if an employee is with a company.
     * @param email employee email.
     * @param companyEmail company name.
     * @return boolean true if the employee is with the company.
     * @throws SQLException
     */
    public synchronized static boolean isEmployeeWithCompany(String email, String companyEmail) throws SQLException {
        Statement statement= DataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT " + employeeEmailPK + " FROM " + databaseName + "." + tableEmployees + " WHERE " + employeeCompanyEmail + " = '%s' AND " + employeeEmailPK + " = '%s';", companyEmail, email));

        String temp = "";
        while (resultSet.next()) {
            temp = resultSet.getString(1);
        }
        return (temp.equals(email));
    }
}
