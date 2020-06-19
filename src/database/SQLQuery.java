package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        ResultSet resultSet = statement.executeQuery(String.format("SELECT companieName FROM companies.companies WHERE companieName = '%S';", compName));
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
        ResultSet resultSet = statement.executeQuery(String.format("SELECT employeeId FROM companies.token WHERE employeeId = '%S';", employeeId));

        int name = 0;
        while (resultSet.next()) {
            name = resultSet.getInt(1);
        }
        if (name == employeeId) {
            return true;
        }
        return false;
    }

    /**
     * Delete a token
     * @param employeeId employees Id.
     * @throws SQLException
     */
    public static void deleteOldToken(int employeeId) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        statement.execute(String.format("DELETE FROM companies.token WHERE employeeId` = '%s';", employeeId));
    }

    /**
     * Add a token field to the database.
     * @param employeeId employees Id.
     * @param token the token.
     * @param timeStamp current timestamp.
     * @throws SQLException
     */
    public static void addToken(int employeeId, String token, String timeStamp) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO companies.token VALUES ('%s','%s', '%s');", employeeId, token, timeStamp));
    }

    /**
     * Checks if there are any fields in the employee table.
     * @return true if there are fields in the employee table.
     * @throws SQLException
     */
    public static boolean doesEmployeeInTableExist() throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT employeeId FROM companies.employees WHERE employeeID = '0';");

        int id = -1;
        while (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        if (id == 0) {
            return true;
        }
        return false;
    }

    /**
     * Get the max employee Id in the employee table.
     * @return the max employee Id.
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
     * @return true if the employee email is associated with the company.
     * @throws SQLException
     */
    public static boolean isEmployeeInCompany(String email, String companyName) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SELECT email FROM companies.employees WHERE email = '%S' AND companieName = '%s';", email, companyName));

        String temp = "";
        while (resultSet.next()) {
            temp = resultSet.getString(1);
        }
        if (temp.equals(email)) {
            return true;
        }
        return false;
    }
}
