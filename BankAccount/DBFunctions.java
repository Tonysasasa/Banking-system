package BankAccount;
import java.sql.*;

import java.sql.Connection;
import java.sql.Statement;



public class DBFunctions {
    static Connection c = Database.c;
    static Statement stmt = Database.stmt;
    static Account newaccount = new Account();

    public static boolean loginCheck(){
        try {
            String sql = "SELECT * FROM account";
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs != null){
                return true;
            }
              
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        } 
        return false;
    
    }

    public static Account getAccountByCardID(String CardID){     
 
        try {
            
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM account where cardid = ?");
            stmt.setString(1, CardID);
            ResultSet rs = stmt.executeQuery();

            if (rs.isBeforeFirst()){
                while ( rs.next() ){
                    newaccount.setCardID(rs.getString("cardiD"));
                    newaccount.setUserName(rs.getString("username"));
                    newaccount.setMoney(rs.getDouble("accountbalance"));
                    newaccount.setPassword(rs.getString("password"));
                }
                return newaccount;
            }

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        } 
        return null;
    }

    public static void registerUser(int cnt, Account account){

        try {
            PreparedStatement st = c.prepareStatement("INSERT INTO ACCOUNT (ID,CardID,Username,AccountBalance,Password) VALUES (?, ?, ?, ?,?)");
            st.setInt(1, cnt);
            st.setString(2, account.getCardID());
            st.setString(3, account.getUserName());
            st.setDouble(4, account.getMoney());
            st.setString(5, account.getPassword());
            st.executeUpdate();
            st.close();
            c.commit();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("User registered into DB successfully");
    }

    public static void resetPassword(Account account, String newPassword) {
        try {

            PreparedStatement stmt = c.prepareStatement("UPDATE account set PASSWORD = ? where CardID = ?");
            stmt.setString(1, newPassword);
            stmt.setString(2, account.getCardID());
            stmt.executeUpdate();

        }catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public static void deleteAccount (Account account){
        try {

            PreparedStatement stmt = c.prepareStatement("DELETE FROM account where CardID = ?");
            stmt.setString(1, account.getCardID());
            stmt.executeUpdate();

        }catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }
}
