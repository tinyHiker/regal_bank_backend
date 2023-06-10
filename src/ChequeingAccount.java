import java.sql.*;

public class ChequeingAccount{

    private int chequeingAccountID;
    private double balance;
    private User user;
    

    public ChequeingAccount(int id, double balance, User user){
        this.chequeingAccountID = id;
        this.balance= balance;
        this.user = user;
    }

    public double getBalance(){
        return this.balance;
    }

    public int getAccountID(){
        return chequeingAccountID;
    }

    public int getUserID(){
        return user.getUserID();
    }

    public void reduceBalance(double deduction){
        this.balance -= deduction;

    }

    
    
}
