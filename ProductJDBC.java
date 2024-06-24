import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class ProductJDBC {

    static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    static final String USER = "your_username";
    static final String PASS = "your_password";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute a query to create the table
            System.out.println("Creating table in the given database...");
            stmt = conn.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS Product ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(255), "
                    + "price_per_unit DOUBLE, "
                    + "active_for_sell BOOLEAN)";
            stmt.executeUpdate(createTableSQL);
            System.out.println("Created table in the given database...");

            String insertSampleDataSQL = "INSERT INTO Product (name, price_per_unit, active_for_sell) VALUES "
                    + "('Product 1', 10.5, true), "
                    + "('Product 2', 20.0, false), "
                    + "('Product 3', 15.75, true)";
            stmt.executeUpdate(insertSampleDataSQL);
            System.out.println("Inserted sample data into the table...");

            System.out.println("Retrieving all products...");
            String selectSQL = "SELECT id, name, price_per_unit, active_for_sell FROM Product";
            ResultSet rs = stmt.executeQuery(selectSQL);

            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                double pricePerUnit = rs.getDouble("price_per_unit");
                boolean activeForSell = rs.getBoolean("active_for_sell");

                System.out.print("ID: " + id);
                System.out.print(", Name: " + name);
                System.out.print(", Price Per Unit: " + pricePerUnit);
                System.out.println(", Active For Sell: " + activeForSell);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {

            se.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
