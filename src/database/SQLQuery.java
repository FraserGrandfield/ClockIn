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
        ResultSet resultSet = statement.executeQuery(String.format("DELETE FROM companies.token WHERE employeeId` = '%s';", employeeId));
    }

    public static void addToken(int employeeId, String token, String timeStamp) throws SQLException {
        Statement statement = dataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO companies.token VALUES ('%s','%s', '%s');", employeeId, token, timeStamp));
    }

}
