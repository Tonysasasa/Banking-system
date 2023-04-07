package BankAccount;

import java.util.Scanner;

public class AccountBalance {
    static double deposit = 0;
    static double withdraw = 0;
    static double currentMoney = 0;
    static double money = 0;
    static String toCardID = "";
    static double amountMoney = 0;
   
    Account account = new Account();
    
    public static void mainFunc(Account acc, Scanner sc){
        while (true){
            System.out.println("=================================================");
            System.out.println("Please enter 1/5 for the following option: ");
            System.out.println("Press 1 to display the current balance of the account ");
            System.out.println("Press 2 to deposit the quote money into current account ");
            System.out.println("Press 3 to withdraw the quote money into current account ");
            System.out.println("Press 4 to transfer money into another account ");
            System.out.println("Press 5 to go back to login menu ");
            int select = sc.nextInt();
            switch (select){
                case 1:
                    System.out.println("=================================================");
                    System.out.println(" The current balance of the account is " + acc.getMoney());
                    break;
                case 2:
                    System.out.println("=================================================");
                    System.out.println("Please enter the amount of deposit ");
                    System.out.println("Deposited! The current amount of your account is " + depositMoney(acc, sc));
                    break;
                case 3:
                    System.out.println("=================================================");
                    System.out.println("Please enter the amount to withdraw: ");
                    withdrawMoney(acc, sc);
                    break;                
                case 4:
                    System.out.println("=================================================");
                    System.out.println("Please enter the cardID to transfer ");
                    transferMoney(acc,sc);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Wrong input, please re-enter");  
            }
        }            
    }

    private static double depositMoney(Account acc,Scanner sc){
        deposit = sc.nextDouble();
        acc.setMoney(acc.getMoney() + deposit);
        currentMoney = acc.getMoney();
        DBAccounts.updateMoneytoAccount(acc.getCardID(), currentMoney);
        return currentMoney;
    }

    private static double withdrawMoney(Account acc,Scanner sc){   
        money = acc.getMoney();
        withdraw = sc.nextDouble();

        if (money <= 0 || money - withdraw < 0){
            System.out.println("Insufficient fund to withdraw. Current balance: " + money);
            return -1;
        }

        acc.setMoney(acc.getMoney() - withdraw);
        currentMoney = acc.getMoney();
        DBAccounts.updateMoneytoAccount(acc.getCardID(), currentMoney);
        System.out.println("Withdrawed! The current amount of your account is " + currentMoney);
        return currentMoney;
    }

    private static void transferMoney(Account acc, Scanner sc){       
        double money = acc.getMoney();
        
        while (true){
            toCardID = sc.next();
            if (toCardID.equals(acc.getCardID()) ){
                System.out.println("The entered cardID is the current account. Please re-enter: ");
            }else{
                if (toCardID != null){                  
                    System.out.println("The entered cardID is " + toCardID + " Are you sure to transfer? [Y/N] ");
                    while (true){
                        switch (sc.next()){
                            case "Y":
                                System.out.println("Please enter the amount of money to transfer ");
                                amountMoney = sc.nextDouble();
                                if (money <= 0 || money - amountMoney < 0){
                                    System.out.println("Insufficient fund to withdraw. Current balance: " + money);
                                    return;
                                }
                                DBAccounts.addMoneytoAccount(toCardID, amountMoney);

                                acc.setMoney(acc.getMoney() - amountMoney);                           
                                DBAccounts.updateMoneytoAccount(acc.getCardID(), acc.getMoney());

                                System.out.println(" Money ( " + amountMoney + " ) transfered successfully from " + acc.getCardID()+ " to " + toCardID);  
                                return;
                            case "N":
                                return;
                            default:
                                System.out.println("Invalid input! Please re-enter ");               
                            }
                    }
  
                }else{
                    System.out.println("cardID didn't found, please retry: ");
                }
            }
        }
    }
}
