import java.sql.*;

public class Loan {
    private int loanID;
    private double principle;
    private User user;
    private double interest= 0.03;

    public Loan(int loanID, double principle, User user){
        this.loanID= loanID;
        this.principle = principle;
        this.user = user;
    }

    public int getLoanID(){
        return loanID;
    }

    public double getPrinciple(){
        return principle;
    }

    public int getUserID(){
        return user.getUserID();
    }

    public String getLoanRepr(){
        return "Loan ID: "+this.getLoanID()+" Principle: "+this.getPrinciple()+" Interest: "+this.interest;
    }
}
