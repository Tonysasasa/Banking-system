package BankAccount;
import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;

public class DBAccounts {
    
    static Connection c = Database.c;
    static Statement stmt = Database.stmt;


    public static void updateMoneytoAccount(String toCardID, double accountMoney){
        try {

            PreparedStatement st = c.prepareStatement("UPDATE account set accountbalance = ? where cardid = ?");
            st.setDouble(1, accountMoney);
            st.setString(2, toCardID);
            st.executeUpdate();

        }catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public static void addMoneytoAccount(String toCardID, double amountMoney){
        try {
            double money = 0;

            PreparedStatement stmt = c.prepareStatement("SELECT accountbalance FROM account where cardid = ?");
            stmt.setString(1, toCardID);
            ResultSet rs = stmt.executeQuery();
            if (rs.isBeforeFirst()){
                while ( rs.next() ){
                    money = rs.getDouble("accountbalance");
                }
            }
            System.out.println(money);

            PreparedStatement st = c.prepareStatement("UPDATE account set accountbalance = ? where cardid = ?");
            st.setDouble(1, money + amountMoney);
            st.setString(2, toCardID);
            st.executeUpdate();

                
        }catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

   
}
