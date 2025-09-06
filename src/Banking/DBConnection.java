package Banking;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
public class DBConnection {

    private static final String url="jdbc:mysql://localhost/Banking_Management_System";
    private static final String user ="root";
    private static final String pass ="*************";


    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user, pass);
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return conn;
    }
}
