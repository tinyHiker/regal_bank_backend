import java.sql.*;

public class SavingsAccount {
    
    private int savingsAccountID;
    private double balance;
    private double interestRate;
    private User user;

    public SavingsAccount(int id, double balance, User user, double interestRate){
        this.savingsAccountID = id;
        this.balance= balance;
        this.user = user;
        this.interestRate= interestRate;

    }

    public double getBalance(){
        return this.balance;
    }

    public double getInterestRate(){
        return this.interestRate;
    }
    
}
