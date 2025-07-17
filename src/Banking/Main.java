package Banking;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) throws SQLException {
        Scanner sc = new Scanner(System.in);
        BankingService bs = new BankingService();
        int i = 0;
        do {
            System.out.println("Welcome to Bank of Ash ");
            System.out.println("-*-*-*-*-*-*--*-*-*-*-*-*--*-*-*-*-*-*--*-*-*-*-*-*--*-*-*-*-*-*--*-*-*-*-*-*--*-*-*-*-*-*--*-*-*-*-*-*--*-*-*-*-*-*--*-*-*-*-*-*--*-*-*-*-*-*--*-*-*-*-*-*--*-*-*-*-*-*--*-*-*-*-*-*-");
            System.out.println("Menu : ");
            System.out.println("1. Create Account \n 2. Deposit Money \n 3. Withdraw Money \n 4. View Balance \n 5. View Transactions \n 6. View Account Details \n 7. Close Account \n 8. Exit");
            System.out.println("Please enter the Option : ");
            try {
                i = sc.nextInt();
                switch (i) {
                    case 1:
                        bs.createAccount();
                        break;
                    case 2:
                       bs.depositMoney();
                        break;
                    case 3:
                        bs.withdrawMoney();
                        break;
                    case 4:
                         bs.viewBalance();
                        break;
                    case 5:
                         bs.viewTransaction();
                        break;
                    case 6:
                        bs.viewAccount();
                        break;
                    case 7:
                         bs.closeAccount();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                sc.nextLine();
            }
        }
            while (i != 8) ;
            sc.close();
            System.out.println("Thank You !");
    }
}
