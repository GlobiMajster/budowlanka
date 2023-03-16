import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnect {
    private static final String URL = "jdbc:mysql://localhost:3306/budowlanka";
    private static final String user = "root";
    private static final String pass = "";

    public static Connection connect(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, user, pass);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}