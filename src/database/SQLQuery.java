package database;

/**
 * Stores all SQL queries.
 *
 * @author fraser
 * @version 1.0
 * @since 18/06/20
 */
public class SQLQuery {

    private static DataBase dataBase = DataBase.getDataBaseInstance();

    private static SQLQuery sqlQuery = new SQLQuery();

    /**
     * @return SQLQuery instance.
     */
    public SQLQuery getSqlQueryInstance() {
        return sqlQuery;
    }



}
