package BankAccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class Database {
    static Connection c = null;
    static Statement stmt = null;

    public static void createTable(Connection c, Statement stmt){

        try {
            //Register JDBC Driver
            String sql = "CREATE TABLE IF NOT EXISTS Account " +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " CardID           TEXT    NOT NULL, " +
                " Username            TEXT     NOT NULL, " +
                " AccountBalance        REAL, " +
                " Password         TEXT)";
            stmt.executeUpdate(sql);
            //stmt.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }

    
   public static Connection getConnection(){
        if (c != null) {
            return c;
        }
        // User input 
        return getConnection("bank","postgres", "123456");
   }
        
   private static Connection getConnection(String db_name,String user_name,String password) {
        if (c != null) {
            return c;
        }
          
        try {
            //Register JDBC Driver
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/" + db_name,
                user_name, password);

            return c;

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return null;
    }

    public static Statement getStatement(Connection c) {
        if (stmt != null) {
            return stmt;
        }
          
        try {
            stmt = c.createStatement();
            return stmt;

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return null;
    }
}