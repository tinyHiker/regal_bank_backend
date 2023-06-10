
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class BankingApp {
    private User currentUser;


    public BankingApp(String username, String password) throws Exception{
        this.currentUser= new User(username, password);
    }


    public String getUserFullName(){
        return currentUser.getNameOfUser();
    }


    public double getSavingsBalance(){
        return currentUser.getSavingsAccount().getBalance();
    }


    public double getSavingsInterest(){
        return currentUser.getSavingsAccount().getInterestRate();
    }


    public double getChequeingsBalance(){
        return currentUser.getChequeingsAccount().getBalance();
    }


    //Returns an ArrayList of Strings in the follwing format:"Question: x\tAnswer:y"
    public ArrayList<String> getQuestionAnswers(){
        ArrayList<Request> requests= currentUser.getRequests();
        ArrayList<String> lines = new ArrayList<>();

        for (int i = 0; i < requests.size(); i++) {
            lines.add(requests.get(i).getStringRep());    
        }

        return lines;
    }

    public ArrayList<String> getTransactions(){
        ArrayList<Transaction> transactions= currentUser.getTransactions();
        ArrayList<String> lines = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            lines.add(transactions.get(i).getTransacString());    
        }

        return lines;
    }

    public ArrayList<String> getBillStrings(){
        ArrayList<Bill> bills= currentUser.getBills();
        ArrayList<String> lines = new ArrayList<>();

        for (int i = 0; i < bills.size(); i++) {
            lines.add(bills.get(i).getBillString());    
        }

        return lines;
    }

    public boolean validateTransferData(String amount, String receiverID) throws Exception{
        if (amount == null) {
            return false;
        }
        try {
            double amountTransferred = Double.parseDouble(amount);
        } catch (NumberFormatException nfe) {
            System.out.print("\nYou did not enter a number for the amount you want to transfer");
            return false;
        }

        try {
            int transferRecieverID = Integer.parseInt(receiverID);
        } catch (NumberFormatException nfe) {
            System.out.print("\nYou did not enter a number for the reciever's ID.");
            return false;
        }

        /* 
        if (!(currentUser.validateRecieverExistence(receiverID))){
            return false;

        }

        if (!(currentUser.validateEnoughMoney(amount))){
            return false;
        }
        */

        return true;


    }


    public void makeTransfer(String amount, String recieverID) throws Exception{
        currentUser.makeTransfer(Double.parseDouble(amount), Integer.parseInt(recieverID));
    }

    
    //Returns a string in the following format: "x" Ex. "CDIC Insurance Pro"
    public String getInsuranceDescription(){
        return currentUser.getInsuranceDescription(); 
    }


    public boolean setUserInsurance(int planNumber) throws Exception{
        boolean insuranceSet= false;

        insuranceSet = currentUser.setUserInsurance(planNumber);

        return insuranceSet;
    }



    public boolean createNewRequest(String question) throws Exception{
        boolean requestCreated= false;
        requestCreated= currentUser.addRequest(question);
        return requestCreated;
    }



    public boolean validatePinEntry(String currentPinEntrance){
        return currentUser.matchPin(currentPinEntrance);
    }

    public boolean validateNewPin(String newPin){
        if (!(newPin != null && newPin.length()==4)) {
            return false;
        }
        try {
            int i = Integer.parseInt(newPin);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
        
    }



    public boolean changePin(String newPin) throws Exception{
        if (currentUser.changePin(newPin)){
            return true;
        } else{
            return false;
        }

        
    }

    

    public String getLoanData(){
        return currentUser.getLoanString();
    }

    public boolean verifyBillData(String billID) throws Exception{


        if (billID == null) {
            return false;
        }

        try {
            int billIDNum = Integer.parseInt(billID);
        } catch (NumberFormatException nfe) {
            System.out.print("\nYou did not enter a valid number for the billID");
            return false;
        }


        if (!(currentUser.validateBillExistence(Integer.parseInt(billID)))){
            System.out.print("\nThis bill does not exist");
            return false;

        }else{
            currentUser.payBill(Integer.parseInt(billID));
        }  

        return true;

    }



}
