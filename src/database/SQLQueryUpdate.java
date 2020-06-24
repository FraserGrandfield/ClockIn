package database;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQL query class for updating the database.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 24/06/20
 */
public class SQLQueryUpdate {

    /**
     * update the clock out field in the timestamps table.
     * @param email employee email.
     * @param timeStamp timestamp.
     * @throws SQLException
     */
    public synchronized static void updateClockOutTimeStamp(String email, String timeStamp) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("UPDATE companies.timestamps SET clockOut = '%s' WHERE email = '%s' AND clockOut IS NULL;", timeStamp, email));
    }
}
