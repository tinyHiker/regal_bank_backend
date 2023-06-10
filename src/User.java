import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class User {

    private int userID;
    private String nameOfUser;
    private String pin; //must be four digits
    private String username;
    private String userPassword; //outside methods will make sure it is a "STRONG" password
    private SavingsAccount savings;
    private ChequeingAccount chequeing;
    private Insurance policy = null;
    private int insuranceID;
    private Loan loan = null;

    private boolean takenLoan= false;

    private ArrayList<Bill> bills = new ArrayList<Bill>();
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private ArrayList<Request> requests = new ArrayList<Request>();

    //MySQL Connection Stuff
    Connection con;
    Statement stmt;
    ResultSet rs;


    public User(String username, String userPassword) throws Exception{
        boolean userCreation = this.createUser(username, userPassword);
        
        if (userCreation){
            this.loadUserData();
        }

    }

    //This function loads all User-Data and Associated-System-Data into the program clases.
    public void loadUserData() throws Exception{
        this.loadInsurancePolicy();
        this.loadSavings();
        this.loadChequeings();
        this.loadBills();
        this.loadLoan();
        this.loadTransactions();
        this.loadRequests();
    }

    //This method instantiates all the SQL objects needed to interact with our SQL database.
    public void loadSQLObjects() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Notice how we're connecting to a database on my (Taha iqbal 501112475)'s local machine.
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsystemdatabase", "root", "123456789"); 
        stmt= con.createStatement();
    }

    //This method closes all the SQL objects when they are no longer needed.
    public void closeSQLObjects() throws Exception{
        con.close();
    }



    public boolean createUser(String username, String userPassword) throws Exception{
        //use SQL to check if a userExists
        //returns false if the user does not exist, returns true if the user exists

        this.loadSQLObjects();
        rs = stmt.executeQuery("SELECT * FROM USERS WHERE username=\'"+username+"\' AND userPassword=\'"+userPassword+"\';");

        /*The following conditional will raise a custom UserDoesNotExistException if the user is not found. 
        This will cuase the BankingUserInterface class to reprompt the User for their username and password.
        If the User is found, the appropriate data is loaded,from the associated record in the Users table, into the Useri-instances local variables. */
        if (!rs.isBeforeFirst()) {    
            throw new UserDoesNotExistException();
        } else {
            while(rs.next()){
                this.userID= rs.getInt(1);
                this.nameOfUser= rs.getString(2);
                this.pin = rs.getString(3);
                this.username = rs.getString(4);
                this.userPassword = rs.getString(5);
                this.insuranceID = rs.getInt(6);
            }
        }
        
        this.closeSQLObjects();
        return true;
    }

    public boolean validateRecieverExistence(String recieverID) throws Exception{

        boolean returnBoolean= false;

        this.loadSQLObjects();
        rs = stmt.executeQuery("SELECT * FROM USERS WHERE userID="+Integer.parseInt(recieverID)+";");
        

        if (!rs.isBeforeFirst()) {    
            returnBoolean= false;
        }

        this.closeSQLObjects();

        returnBoolean = true;

        return returnBoolean;
    }


    public boolean validateEnoughMoney(String amount) throws Exception{

        if (this.chequeing.getBalance() >= Double.parseDouble(amount)){
            return true;
        }

        return false;
    }

    public void makeTransfer(double amount, int recieverID) throws Exception{

        this.loadSQLObjects();
        rs = stmt.executeQuery("SELECT * FROM chequeingaccounts WHERE userID="+ recieverID +";");

        double currentCheckingBalance= 0.0;
        while(rs.next()){
            currentCheckingBalance= rs.getDouble(2);
        }

        double newBalance = currentCheckingBalance + amount;
        stmt.executeUpdate("UPDATE chequeingaccounts SET balance ="+ newBalance+" WHERE userID =" + recieverID+";");


        this.chequeing.reduceBalance(amount);
        stmt.executeUpdate("UPDATE chequeingaccounts SET balance ="+ this.chequeing.getBalance()+" WHERE userID =" + this.userID+";");



        stmt.executeUpdate("INSERT INTO transactions (amount, senderID, recieverID) VALUES ("+ amount +", "+ this.userID +", "+ recieverID+");");
        rs = stmt.executeQuery("SELECT * FROM transactions ORDER BY transactionID DESC LIMIT 1;");

        if (!rs.isBeforeFirst()) {    
            throw new Exception();
        } else{
            while(rs.next()){
                this.transactions.add(new Transaction(rs.getInt(1), rs.getDouble(2), this.userID, recieverID));

            }
        }

        
        this.closeSQLObjects();
        
    }
    




   
    //Instantiates this.policy using the Policies table in the database.
    public void loadInsurancePolicy() throws Exception
    {
        this.loadSQLObjects();
        if (insuranceID == 0){ //checking if the user does not have a CDIC insurance plan
            policy = null;
        }
        else{
            rs = stmt.executeQuery("SELECT * FROM POLICIES WHERE policyID="+insuranceID+";");
            while(rs.next()){
                this.policy = new Insurance(rs.getInt(1), rs.getString(2));
            }
        }
        this.closeSQLObjects();
    }



    //Instantiates this.chequeing using the chequeingaccounts table in the database.
    public void loadChequeings() throws Exception
    {
        this.loadSQLObjects();
        rs = stmt.executeQuery("SELECT * FROM chequeingaccounts WHERE userID="+this.getUserID()+";");
        while(rs.next()){
            this.chequeing = new ChequeingAccount(rs.getInt(1), rs.getDouble(2), this);
            
        }
        this.closeSQLObjects();
    }


    //Instantiates this.chequeing using the chequeingaccounts table in the database.
    public void loadSavings() throws Exception
    {

        this.loadSQLObjects();
        rs = stmt.executeQuery("SELECT * FROM savingsaccounts WHERE userID="+this.getUserID()+";");
        while(rs.next()){
            this.savings= new SavingsAccount(rs.getInt(1), rs.getDouble(2), this, rs.getDouble(4));
        } 
        this.closeSQLObjects();
    }



    public void loadBills() throws Exception 
    {
        this.loadSQLObjects();
        rs = stmt.executeQuery("SELECT * FROM bills WHERE billeeID="+this.getUserID()+";");
        while(rs.next()){
            this.bills.add(new Bill(rs.getInt(1), rs.getDouble(2), rs.getString(3), this, rs.getInt(5)));
        }
        this.closeSQLObjects();
    }


    public void loadLoan() throws Exception 
    {
        this.loadSQLObjects();
        rs = stmt.executeQuery("SELECT * FROM loans WHERE userID="+this.getUserID()+";");
        if (!rs.isBeforeFirst()) {    
            this.loan = null;
            this.takenLoan = false;
        } else{
            while(rs.next()){
                this.loan = new Loan(rs.getInt(1), rs.getDouble(2), this);
            }
            this.takenLoan = true;

        }

        this.closeSQLObjects();
    }



    public void loadTransactions() throws Exception 
    {
        this.loadSQLObjects();
        rs = stmt.executeQuery("SELECT * FROM transactions WHERE senderID="+this.getUserID()+" OR recieverID="+this.getUserID()+";");
        if (!rs.isBeforeFirst()) {    
            assert true;
        } else{
            while(rs.next()){
                this.transactions.add(new Transaction(rs.getInt(1), rs.getDouble(2), rs.getInt(3), rs.getInt(4)));
            }

        }
        this.closeSQLObjects();
    }



    public void loadRequests() throws Exception{
        this.loadSQLObjects();

        rs = stmt.executeQuery("SELECT * FROM requests WHERE userID="+this.getUserID()+";");
        if (!rs.isBeforeFirst()) {    
            assert true;
        } else{
            while(rs.next()){
                this.requests.add(new Request(rs.getInt(1), rs.getString(2), this));
            }

        }
        this.closeSQLObjects();
    }

    

    public int getUserID(){
        return this.userID;
    }


    public String getNameOfUser(){
        return this.nameOfUser;
    }



    public String getPin(){
        return this.pin;
    } 



    public String getUsername(){
        return this.username;
    }


    public String getUserPassword(){
        return this.userPassword;
    } 


    public SavingsAccount getSavingsAccount(){
        return this.savings;
    }


    public ChequeingAccount getChequeingsAccount(){
        return this.chequeing;
    }


    public String getInsuranceDescirption(){
        return this.policy.getDescription();
    }



    public ArrayList<Request> getRequests(){
        return this.requests;
    }



    public String getInsuranceDescription(){
        if (this.policy == null) {
            return null;
        }
        return this.policy.getDescription();
    }




    public boolean setUserInsurance(int planNumber) throws Exception{

        this.loadSQLObjects();
        int insuranceID=0;
        
        if (planNumber==1){
            insuranceID=1;
        } else if (planNumber==2){
            insuranceID=3;
        } else if (planNumber==3){
            insuranceID=4;
        }

        rs = stmt.executeQuery("SELECT * FROM POLICIES WHERE policyID="+insuranceID+";");
        while(rs.next()){
            this.policy = new Insurance(rs.getInt(1), rs.getString(2));
        }
        stmt.executeUpdate("UPDATE Users SET insuranceID ="+ insuranceID +" WHERE userID =" + this.getUserID()+";");
        this.closeSQLObjects();
        return true; 
    }



    public boolean addRequest(String question) throws Exception{

        this.loadSQLObjects();
        stmt.executeUpdate("INSERT INTO requests (question, userID) VALUES (\'"+ question +"\' , "+ this.getUserID()+");");
        rs = stmt.executeQuery("SELECT * FROM requests ORDER BY requestID DESC LIMIT 1;");

        if (!rs.isBeforeFirst()) {    
            return false;
        } else{
            while(rs.next()){
                this.requests.add(new Request(rs.getInt(1), rs.getString(2), this));
            }

        }

        this.closeSQLObjects();
        return true;
    }




    public boolean matchPin(String pinEntry){

        if (pinEntry.equals(this.pin)){
            return true;
        }
        return false;
    }




    public boolean changePin(String newPin) throws Exception{
        this.loadSQLObjects();
        this.pin= newPin;
        stmt.executeUpdate("UPDATE Users SET pin =\'"+newPin+"\' WHERE userID =" + this.getUserID()+";");
        this.closeSQLObjects();
        return true;
    }



    public String getLoanString(){
        if (this.loan == null){
            return null;
        }else {
            return this.loan.getLoanRepr();
        }
    }

    public ArrayList<Transaction> getTransactions(){

        ArrayList<Transaction> sentTransactions = new ArrayList<>();

        for (int i=0; i<this.transactions.size(); i++){
            if (transactions.get(i).getSenderID() == this.userID){
                sentTransactions.add(transactions.get(i));

            }
        }

        return sentTransactions;
    }

    public ArrayList<Bill> getBills(){

        ArrayList<Bill> unpaidBills = new ArrayList<>();
        
        for (int i=0; i<this.bills.size(); i++){
            if (bills.get(i).isPaid()==false){
                unpaidBills.add(bills.get(i));

            }
        }

        return unpaidBills;
    }

    public boolean validateBillExistence(int enteredID){


        for (int i=0; i<this.bills.size(); i++){
            if (bills.get(i).getBillID()== enteredID){
                return true;

            }
        }

        return false;

    }

    public void payBill(int enteredID) throws Exception{

        Bill billToPay = null;

        for (int i=0; i<this.bills.size(); i++){
            if (bills.get(i).getBillID()== enteredID){
                billToPay= bills.get(i);
                break;
                

            }
        }

        double paymentAmount= billToPay.getBillAmount();
        
        this.chequeing.reduceBalance(paymentAmount);
        billToPay.payBill();

        this.loadSQLObjects();

        stmt.executeUpdate("UPDATE chequeingaccounts SET balance ="+this.chequeing.getBalance()+" WHERE userID =" + this.getUserID()+";");
        stmt.executeUpdate("UPDATE bills SET paid ="+1+" WHERE billID =" + billToPay.getBillID()+";");
        this.closeSQLObjects();

        


        
        
    }



  


    






}
