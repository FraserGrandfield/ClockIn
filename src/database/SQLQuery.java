package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Stores all SQL queries.
 *
 * @author Fraser Grandfield
 * @version 1.0
 * @since 18/06/20
 */
public class SQLQuery {
    //TODO split into classes for delete, insert and select and inherit from a abtract class.
    private static DataBase dataBase = DataBase.getDataBaseInstance();

    /**
     * Check if company name is in database.
     * @param compName name of the company.
     * @return boolean true if company exists.
     * @throws SQLException
     */
    public static boolean doesCompanyNameExist(String compName) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT companieName FROM companies.companies WHERE companieName = '%s';", compName));
        String names = "";

        while (resultSet.next()) {
            names = resultSet.getString(1);
        }
        return names.equals(compName);
    }

    /**
     * Add a company to the database.
     * @param compName name of company
     * @param compPassword password for company account
     * @throws SQLException
     */
    public static void addCompany(String compName, String compPassword) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO companies.companies VALUES ('%s','%s');", compName, compPassword));
    }

    /**
     * Checks if an employee already has a token.
     * @param employeeId
     * @return boolean true if they already have a token.
     * @throws SQLException
     */
    public static boolean doesEmployeeHaveToken(int employeeId) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT employeeId FROM companies.token WHERE employeeId = '%s';", employeeId));

        int name = 0;
        while (resultSet.next()) {
            name = resultSet.getInt(1);
        }

        return (name == employeeId);
    }

    /**
     * Delete an employee token
     * @param employeeId employees Id.
     * @throws SQLException
     */
    public static void deleteOldEmployeeToken(int employeeId) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        statement.execute(String.format("DELETE FROM companies.token WHERE employeeId = '%s';", employeeId));
    }

    /**
     * Add an employee token field to the database.
     * @param employeeId employees Id.
     * @param token the token.
     * @param timeStamp current timestamp.
     * @throws SQLException
     */
    public static void addEmployeeToken(int employeeId, String token, String timeStamp) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO companies.token VALUES ('%s','%s', '%s');", employeeId, token, timeStamp));
    }

    /**
     * Checks if there are any fields in the employee table.
     * @return boolean true if there are fields in the employee table.
     * @throws SQLException
     */
    public static boolean doesEmployeeInTableExist() throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT employeeId FROM companies.employees WHERE employeeID = '0';");

        int id = -1;
        while (resultSet.next()) {
            id = resultSet.getInt(1);
        }

        return (id == 0);
    }

    /**
     * Get the max employee Id in the employee table.
     * @return int the max employee Id.
     * @throws SQLException
     */
    public static int getMaxEmployeeId() throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT MAX(employeeID) FROM companies.employees;");

        //-2 incase something goes wrong so the id will be -1
        int id = -2;
        while (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;
    }

    /**
     * Add an employee to the employee table.
     * @param employeeId employees Id.
     * @param name employees name.
     * @param email employees email.
     * @param password password.
     * @param companyName company name that the employee is with.
     * @throws SQLException
     */
    public static void addEmployee(int employeeId, String name, String email, String password, String companyName) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO companies.employees VALUES ('%s', '%s', '%s', '%s', '%s');", employeeId, name, email, password, companyName));
    }

    /**
     * Checks if an employee is associated with a company by their email.
     * @param email employee email
     * @param companyName company name.
     * @return boolean true if the employee email is associated with the company.
     * @throws SQLException
     */
    public static boolean isEmployeeInCompany(String email, String companyName) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT email FROM companies.employees WHERE email = '%s' AND companieName = '%s';", email, companyName));

        String temp = "";
        while (resultSet.next()) {
            temp = resultSet.getString(1);
        }

        return temp.equals(email);
    }

    /**
     * Checks if the company logged in has a valid token
     * @param token token.
     * @param companyName company name.
     * @return boolean true if company has a valid token.
     * @throws SQLException
     */
    public static boolean doesCompanyHaveValidToken(String token, String companyName) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
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
     * Adds a company token
     * @param companyName company name.
     * @param token token.
     * @param timeStamp timestamp.
     * @throws SQLException
     */
    public static void addCompanyToken(String companyName, String token, String timeStamp) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO companies.tokencompany VALUES ('%s','%s', '%s');", companyName, token, timeStamp));
    }

    /**
     * Checks if the company has a token in the database.
     * @param companyName company name.
     * @return boolean true if the company has a token.
     * @throws SQLException
     */
    public static boolean doesCompanyHaveToken(String companyName) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT companyname FROM companies.tokencompany WHERE companyname = '%s';", companyName));

        String tempName = "";
        while (resultSet.next()) {
            tempName = resultSet.getString(1);
        }

        return tempName.equals(companyName);
    }

    /**
     * Deletes a companies token from the database.
     * @param companyName company name.
     * @throws SQLException
     */
    public static void deleteOldCompanyToken(String companyName) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        statement.execute(String.format("DELETE FROM companies.tokencompany WHERE companyname = '%s';", companyName));
    }

    /**
     * Gets the encrypted password of the company.
     * @param companyName company name.
     * @return String encrypted password.
     * @throws SQLException
     */
    public static String getCompanyPassword(String companyName) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT companiePassword FROM companies.companies WHERE companieName = '%s';", companyName));

        String temp = "";
        while (resultSet.next()) {
            temp = resultSet.getString(1);
        }
        return temp;
    }

    /**
     * Gets the encrypted password of the employee.
     * @param companyName company name.
     * @param email employee email.
     * @return String encrypted password of employee.
     * @throws SQLException
     */
    public static String getEmployeePassword(String companyName, String email) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT password FROM companies.employees WHERE companieName = '%s' AND email = '%s';", companyName, email));

        String temp = "";
        while (resultSet.next()) {
            temp = resultSet.getString(1);
        }
        return temp;
    }

    public static String getEmployeeId(String companyName, String email) throws  SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT employeeID FROM companies.employees WHERE companieName = '%s' AND email = '%s';", companyName, email));

        String temp = "";
        while (resultSet.next()) {
            temp = resultSet.getString(1);
        }
        return temp;
    }

    public static boolean doesCompanyHaveCreateEmployeeToken(String companyName) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT companyName FROM companies.createemployeetoken WHERE companyName = '%s';", companyName));

        String tempName = "";
        while (resultSet.next()) {
            tempName = resultSet.getString(1);
        }

        return tempName.equals(companyName);
    }

    public static void deleteOldCompanyCreateEmployeeToken(String companyName) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        statement.execute(String.format("DELETE FROM companies.createemployeetoken WHERE companyName = '%s';", companyName));
    }

    public static void addCompanyCreateEmployeeToken(String companyName, String token, String timeStamp) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO companies.createemployeetoken VALUES ('%s','%s', '%s');", companyName, token, timeStamp));
    }

    public static boolean doesEmployeeHaveCreateAccountValidToken(String token) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
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
        if (currentTime.isAfter(tokenTime)) {
            return false;
        }
        return true;
    }

    public static String getCompanyNameFromCreateEmployeeToken(String token) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT companyName FROM companies.createemployeetoken WHERE token = '%s';", token));

        String compName = "";
        while (resultSet.next()) {
            compName = resultSet.getString(1);
        }
        return compName;
    }

    public static boolean isCreateEmployeeTokenUnique(String token) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT token FROM companies.createemployeetoken WHERE token = '%s';", token));

        String temp = "";
        while (resultSet.next()) {
            temp = resultSet.getString(1);
        }

        return !(temp.equals(token));
    }
}
