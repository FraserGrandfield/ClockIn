package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Stores database instance.
 *
 * @author Fraser Grandfield
 * @version  1.0
 * @since 18/06/2020
 */
public class DataBase {

    private Connection connection;
    private final String CONNECTION_URL = "jdbc:mysql://localhost:3306/clockin?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String CONNECTION_USERNAME = "root";
    //Move off source code
    private final String CONNECTION_PASSWORD = "Deadisland12";
    private final String CONNECTION_DRIVER = "com.mysql.cj.jdbc.Driver";

    private DataBase() throws ClassNotFoundException, SQLException {
        Class.forName(CONNECTION_DRIVER);
        connection = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
    }

    private static DataBase database;
    static {
        try {
            database = new DataBase();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return database instance.
     */
    public static DataBase getDataBaseInstance() {
        return database;
    }
}
