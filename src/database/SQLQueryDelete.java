package database;

import java.sql.SQLException;
import java.sql.Statement;

public class SQLQueryDelete {

    /**
     * Delete an employee token
     * @param employeeId employees Id.
     * @throws SQLException
     */
    public static void deleteOldEmployeeToken(int employeeId) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("DELETE FROM companies.token WHERE employeeId = '%s';", employeeId));
    }

    /**
     * Deletes a companies token from the database.
     * @param companyName company name.
     * @throws SQLException
     */
    public static void deleteOldCompanyToken(String companyName) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("DELETE FROM companies.tokencompany WHERE companyname = '%s';", companyName));
    }

    public static void deleteOldCompanyCreateEmployeeToken(String companyName) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("DELETE FROM companies.createemployeetoken WHERE companyName = '%s';", companyName));
    }


}
