
import java.sql.*;

public class Bill {

    public int billID;
    public double billAmount;
    public String biller; 
    public User billee;
    public boolean paid = false;

    public Bill(int billID, double billAmount, String biller, User billee, int paid){
        this.billID = billID;
        this.billAmount = billAmount;
        this.biller = biller;
        this.billee = billee;

        if (paid == 0){
            this.paid = false;
        } else if (paid == 1){
            this.paid = true;
        }

    }

    public void payBill(){
        this.billAmount = 0;
        this.paid = true;

    }

    public boolean isPaid(){
        return this.paid;

    }

    public int getBillID(){

        return this.billID;

    }

    public String getBillString(){
        return "BillID: "+this.billID +"     Biller: "+this.biller+"     Amount Owed: "+this.billAmount;

    }

    public double getBillAmount(){
        return this.billAmount;
    }

    public void setPaid(){
        this.paid = true;
    }

    

    
    

    
}
