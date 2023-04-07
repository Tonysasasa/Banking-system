package BankAccount;

import java.sql.Connection;
import java.util.Random;
import java.util.Scanner;
import java.sql.Statement;

//import javax.management.ConstructorParameters;

public class ATM {
    String password = "";
    static int cnt = 0;
    static Account account = new Account();

    private static void executeMain(Connection DBCoonection, Statement DBStatement) {
        System.out.println("=================================================");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Please enter 1/3 for the following options: ");
            System.out.println("Press 1 for Login ");
            System.out.println("Press 2 for Registering ");
            System.out.println("Press 3 for Returning card and Exit ");
            int select = sc.nextInt();
            switch (select) {
                case 1:
                    login(sc);
                    break;
                case 2:
                    register(sc);
                    break;
                case 3:
                    exitCard(sc);
                    break;
                default:
                    System.out.println("Wrong input. Please re-enter: ");
            }
        }
    }

    private static void login(Scanner sc) {

        if (DBFunctions.loginCheck() == false){
            System.out.println("No user account in the system, need to register first");
            return;
        }

        while (true) {
            System.out.println("Enter the CardID for login: ");
            String cardId = sc.next();

            Account acc = getAccount(cardId);
            if (acc != null) {
                System.out.println("Enter the login password: ");
                String password = sc.next();
                if (acc.getPassword().equals(password)) {
                    System.out.println("Password is Correct. Dear (" + acc.getUserName() + "), your card ID is "
                            + acc.getCardID());
                    while (true) {
                        System.out.println("=================================================");
                        System.out.println("Please enter 1/3 for the following option: ");
                        System.out.println("Press 1 to Reset password ");
                        System.out.println("Press 2 to De-register the account ");
                        System.out.println("Press 3 to enter into account balance panel ");
                        switch (sc.nextInt()) {
                            case 1:
                                acc.setPassword(resetPassword(acc, sc));
                                break;
                            case 2:
                                deRegister(acc, sc);
                                return;
                            case 3:
                                AccountBalance.mainFunc(acc, sc);
                                break;
                            default:
                                System.out.println("Wrong input, please re-enter");
                        }
                    }
                } else {
                    System.out.println("Entered password is incorrect/mismatched");
                }
            } else {
                System.out.println("No existing account on this CardID");
            }
        }
    }

    private static void register(Scanner sc) {

        System.out.println("=================================================");
        System.out.println("Enter an Registration username: ");
        String registerName = sc.next();
        String registerPassword = "";
        Account account;

        while (true) {
            System.out.println("Enter an Registration password: ");
            registerPassword = sc.next();

            System.out.println("Confirm the password: ");
            String registerPassword1 = sc.next();

            if (!registerPassword.equals(registerPassword1)) {
                System.out.println("Confirmed Passwor entered wrong!");
            } else {
                break;
            }
        }
        account = initialDepositAfterRegister(sc, registerName, registerPassword);
        cnt++;

        DBFunctions.registerUser(cnt, account);

    }

    private static Account initialDepositAfterRegister(Scanner sc, String registerName,String registerPassword) {
        double quota = 0;

        String CardID = createCardID();
        loop: while (true) {
            System.out.println("=================================================");
            System.out.println("Your account is registered. Would you like to deposit the first quota money [Y/N]? ");
            String name = sc.next();
            switch (name) {
                case "Y":
                    System.out.println("Enter the current of quota: ");
                    quota = sc.nextDouble();
                    break loop;
                case "N":
                    break loop;
                default:
                    System.out.println("Invalid syntax. Please re-enter");
            }
        }
        Account account = new Account(CardID, registerName, registerPassword, quota);
        account.setMoney(quota); // initial deposit = setMoney
        System.out.println("Congrats! The new CardID registered is: " + account.getCardID()
                + " Your current balance is: " + account.getMoney());
        return account;
    }

    private static void exitCard(Scanner sc) {
        System.out.println("Are you sure to Exit: [Y/N] ");
        while (true) {
            String confirm = sc.next();
            switch (confirm) {
                case "Y":
                    System.exit(1);
                case "N":
                    return;
                default:
                    System.out.println("Invalid enter. Please re-enter: ");
            }
        }
    }

    private static String resetPassword(Account account, Scanner sc) {
        System.out.println("=================================================");
        System.out.println("Please enter the new password for the account: ");
        String newPassword = sc.next();

        while (true) {
            if (newPassword.equals(account.getPassword())) {
                System.out.println("Duplicated password. Please re-enter: ");
                newPassword = sc.next();
            } else {
                System.out.println("New password been reset! ");
                DBFunctions.resetPassword(account,newPassword);
                return newPassword;
            }
        }
    }

    private static void deRegister(Account account, Scanner sc) {
        System.out.println("=================================================");
        System.out.println("Are you sure to de-register the account? [Y/N] ");
        String confirm = sc.next();
        switch (confirm) {
            case "Y":
                DBFunctions.deleteAccount(account);
                System.out.println("Your account is de-registered successfully");
                break;

            case "N":
                return;

            default:
                System.out.println("Wrong input, please re-enter ");
        }

    }

    private static String createCardID() {
        while (true) {
            String CardID = "";
            Random c = new Random();
            // 8 numbers of Card ID allowed
            for (int i = 0; i < 8; i++) {
                CardID += c.nextInt(10);
            }
            Account acc = getAccount(CardID);
            if (acc == null) {
                return CardID;
            }
            return CardID;
        }
    }

    public static Account getAccount(String CardID) {
        Account acc = DBFunctions.getAccountByCardID(CardID);
        if (acc != null){
            return acc;
        }
        return null; // CardID is the only ID
    }

    public static void main(String[] args) {

        Connection DBCoonection = Database.getConnection();
        Statement DBStatement = Database.getStatement(DBCoonection);

        Database.createTable(DBCoonection, DBStatement);
 
        executeMain(DBCoonection, DBStatement);

    }
}
