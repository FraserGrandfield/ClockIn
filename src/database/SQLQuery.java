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

    private static DataBase dataBase = DataBase.getDataBaseInstance();

    /**
     * Check if company name is in database.
     * @param compName name of the company.
     * @return true if company exists.
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

}
