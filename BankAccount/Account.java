package BankAccount;

public class Account {
    private String CardID;
    private String UserName;
    private String Password;
    private double Money;

    Account(){}

    Account(String CardID, String UserName, String Password, double QuoteMoney){
        this.CardID = CardID;
        this.UserName = UserName;
        this.Password = Password;
    }
    
    public String getCardID(){
        return CardID;
    }
    public void setCardID(String CardID){
        this.CardID = CardID;
    }

    public String getUserName(){
        return UserName;
    }
    public void setUserName(String UserName){
        this.UserName = UserName;
    }

    public String getPassword(){
        return Password;
    }
    public void setPassword(String Password){
        this.Password = Password;
    }

    public double getMoney(){
        return Money;
    }
    public void setMoney(double Money){
        this.Money = Money;
    }
    
}
