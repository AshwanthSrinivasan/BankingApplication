package Banking;
import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class BankingService {

    Scanner sc=new Scanner(System.in);
    public void createAccount() throws SQLException {
        System.out.println("Enter the Name : ");
        String name = sc.nextLine();
        System.out.println("Enter the PIN : ");
        int Pin = sc.nextInt();
        Connection conn = DBConnection.getConnection();
        String sql = "INSERT INTO accounts (name, pin, balance, status) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1,name);
        pstmt.setInt(2,Pin);
        sc.nextLine();
        pstmt.setDouble(3,0.0);
        pstmt.setString(4,"ACTIVE");
        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            int generatedId = rs.getInt(1);
            System.out.println("Account created successfully! Your Account ID is: " + generatedId);
        }
        pstmt.close();
        conn.close();
    }

    public void depositMoney() throws SQLException {
        System.out.println("Enter the Account ID : ");
        int id=sc.nextInt();
        System.out.println("Enter the PIN : ");
        int pin = sc.nextInt();

        Connection conn = DBConnection.getConnection();
        String sql = "select * from accounts where id = ? and pin = ? and status ='ACTIVE' ";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,id);
        pstmt.setInt(2,pin);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next())
        {
            double current_bal = rs.getDouble("balance");
            System.out.println("Enter the Amount to Deposit : ");
            double dep_amt = sc.nextDouble();
            if(dep_amt<0)
            {
                System.out.println("Enter amount is invalid");
            }
            double new_bal = current_bal+dep_amt;
            String new_sql = "Update accounts set balance = ? where id = ? ";
            PreparedStatement newpstmt = conn.prepareStatement(new_sql);
            newpstmt.setDouble(1,new_bal);
            newpstmt.setInt(2,id);
            newpstmt.executeUpdate();
            newpstmt.close();

            String insertTxnSql = "INSERT INTO transactions (account_id, type, amount) VALUES (?, ?, ?)";
            PreparedStatement txnPstmt = conn.prepareStatement(insertTxnSql);
            txnPstmt.setInt(1, id);
            txnPstmt.setString(2, "DEPOSIT");
            txnPstmt.setDouble(3, dep_amt);
            txnPstmt.executeUpdate();
            txnPstmt.close();
        }
        else{
            System.out.println("Entered Credentials are Wrong! ");
        }
        rs.close();

    }


    public void withdrawMoney() throws SQLException {
        System.out.println("Enter the Account ID : ");
        int id = sc.nextInt();
        System.out.println("Enter the PIN : ");
        int pin = sc.nextInt();
        Connection conn = DBConnection.getConnection();
        String sql = "Select * from accounts where id = ? and pin = ? and status ='ACTIVE'";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,id);
        pstmt.setInt(2,pin);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next())
        {
            double cur_bal = rs.getDouble("balance");
            System.out.println("Enter the amount to withdraw : ");
           double withdraw_amt =sc.nextDouble();
            if (withdraw_amt <= 0) {
                System.out.println("Invalid withdrawal amount!");

            }
            if(withdraw_amt>cur_bal)
           {
               System.out.println("Insufficient Balance! Your current balance is: ₹" + cur_bal);
           }
           else{
               double new_bal= cur_bal - withdraw_amt;
               String new_sql = "Update accounts set balance = ? where id = ? ";
               PreparedStatement newpstmt = conn.prepareStatement(new_sql);
               newpstmt.setDouble(1,new_bal);
               newpstmt.setInt(2,id);
               newpstmt.executeUpdate();
               newpstmt.close();

               String insertTxnSql = "INSERT INTO transactions (account_id, type, amount) VALUES (?, ?, ?)";
               PreparedStatement txnPstmt = conn.prepareStatement(insertTxnSql);
               txnPstmt.setInt(1, id);
               txnPstmt.setString(2, "WITHDRAW");
               txnPstmt.setDouble(3,withdraw_amt);
               txnPstmt.executeUpdate();
               txnPstmt.close();

               System.out.println("Withdraw Successfull ! \n Current Balance : "+new_bal);

           }
        }
        else {
            System.out.println("Invalid Credentials ! ");
            rs.close();
            conn.close();
        }
    }


    public void viewBalance() throws SQLException {

        System.out.println("Enter the Account ID : ");
        int id = sc.nextInt();
        System.out.println("Enter the PIN : ");
        int pin = sc.nextInt();
        Connection conn = DBConnection.getConnection();
        String sql = "Select * from accounts where id = ? and pin = ? and status ='ACTIVE'";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,id);
        pstmt.setInt(2,pin);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next())
        {
            double bal = rs.getDouble("balance");
            String name = rs.getString("name");
            System.out.println("Hello "+name +"! Your Current Balance is  "+bal);
        }
        else{
            System.out.println("Invalid Credentials ! ");
        }
        rs.close();
        pstmt.close();
        conn.close();
    }

    public void viewTransaction() throws SQLException {
        System.out.println("Enter the Account ID : ");
        int id = sc.nextInt();
        System.out.println("Enter the PIN : ");
        int pin = sc.nextInt();
        Connection conn = DBConnection.getConnection();
        String sql = "Select * from accounts where id = ? and pin = ? and status ='ACTIVE'";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,id);
        pstmt.setInt(2,pin);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next())
        {
            String new_sql="SELECT * FROM transactions WHERE account_id = ? ORDER BY timestamp DESC";
            PreparedStatement nPstmt = conn.prepareStatement(new_sql);
            nPstmt.setInt(1,id);
            ResultSet new_rs=nPstmt.executeQuery();
            if (!new_rs.isBeforeFirst())
            {
                System.out.println("No Transactions Available ");
            }
            else {
                System.out.println("Transaction ID \t| Transaction Type \t| Amount \t| Date \t");
                while (new_rs.next()) {
                    int t_id = new_rs.getInt("id");
                    String type = new_rs.getString("type");
                    double amount = new_rs.getDouble("amount");
                    Timestamp txnTime = new_rs.getTimestamp("timestamp");

                    System.out.println(t_id + "\t" + type + "\t" + amount + "\t" + txnTime);
                }
            }
            new_rs.close();
            nPstmt.close();
        }
        else {
            System.out.println("Invalid Credentials! ");
        }
        rs.close();
        pstmt.close();
        conn.close();
    }


    public void viewAccount() throws SQLException {
        System.out.println("Enter the Account ID : ");
        int id = sc.nextInt();
        System.out.println("Enter the PIN : ");
        int pin = sc.nextInt();
        Connection conn = DBConnection.getConnection();
        String sql = "Select * from accounts where id = ? and pin = ? and status ='ACTIVE'";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,id);
        pstmt.setInt(2,pin);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
        String name = rs.getString("name");
        int a_id = rs.getInt("id");
        double bal = rs.getDouble("balance");
        String status = rs.getString("status");
            System.out.println("--- Account Details ---");
            System.out.println("Name : "+name);
            System.out.println("Account ID : "+a_id);
            System.out.printf("Balance : ₹%.2f\n", bal);
            System.out.println("Status : "+status);
        }
        else {
            System.out.println("Invalid Credentials");
        }
        rs.close();
        pstmt.close();
        conn.close();

    }

    public void closeAccount() throws SQLException {
        System.out.println("Enter the Account ID : ");
        int id = sc.nextInt();
        System.out.println("Enter the PIN : ");
        int pin = sc.nextInt();
        Connection conn = DBConnection.getConnection();
        String sql = "Select * from accounts where id = ? and pin = ? and status ='ACTIVE'";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,id);
        pstmt.setInt(2,pin);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            System.out.println("Are you sure you want to close your account? (yes/no)\n ");
            sc.nextLine();
            String option =sc.nextLine().toLowerCase();
            if (option.equals("yes"))
            {
                String new_sql = "UPDATE accounts SET status = 'CLOSED' WHERE id = ?";
                PreparedStatement npstmt = conn.prepareStatement(new_sql);
                npstmt.setInt(1, id);
                npstmt.executeUpdate();
                System.out.println("Your account has been successfully closed.\n");

                String insertTxnSql = "INSERT INTO transactions (account_id, type, amount) VALUES (?, 'CLOSE', 0.0)";
                PreparedStatement txnPstmt = conn.prepareStatement(insertTxnSql);
                txnPstmt.setInt(1, id);
                txnPstmt.executeUpdate();
                txnPstmt.close();
            }
            else{
                    System.out.println("Account closure cancelled.");
            }
        }
        else {
            System.out.println("Invalid Credentials !");
        }
        rs.close();
        pstmt.close();
        conn.close();
    }
}
