import java.sql.*;

public class Transaction {

    private int transactionID;
    private int senderID;
    private int recieverID;
    private double amount = 0;

    public Transaction(int transactionID, double amount, int senderID, int recieverID){
        this.transactionID = transactionID;
        this.senderID= senderID;
        this.recieverID= recieverID;
        this.amount= amount; 

    }

    public int getSenderID(){
        return senderID;
    }

    public int getRecieverID(){
        return recieverID;
    }

    public double getAmountTransferred(){
        return amount;
    }


    public String getTransacString(){
        String returnString = "Amount You Transferred: " + this.getAmountTransferred() + " Reciever: "+ "User #"+this.getRecieverID();

        return returnString;
    }

}
