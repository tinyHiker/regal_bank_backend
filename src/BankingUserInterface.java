import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BankingUserInterface {
    public static void main(String[] args) throws Exception{


        Scanner scanner = new Scanner(System.in);
        BankingApp system = null;

        
        while (true) {

            //ie. USE CASE 1: Login
            //Use Cases Done So Far: 1
            //This segment/function in the Interface prompts the User for their Login Credentials 
            String username = "";
            String password = "";

            System.out.println("__LOGIN PAGE__");
            System.out.print("Username: ");
            username = scanner.nextLine();
            System.out.print("Password: ");
            password = scanner.nextLine();


            try{
                system = new BankingApp(username, password); //The BankingApp will try to create a User with the given credentials. 

            } catch (UserDoesNotExistException u){
                System.out.println("The login credentials were incorrect.\nPlease retry entering you username and password");
                continue; //This statement will cause the interface to re-ask for the login-credentials if the wrong username/password were entered.
            } 

            //This section of code is responsible for the intor message when a user eneters a program.
            String name = system.getUserFullName(); 
            System.out.print("Welcome "+name+"!"+" You now have access to online banking at Regal Bank");
            System.out.print("\nYou will now have access to an interactive shell.\nHere is a list of all the commands:");
            
            System.out.print("\n\t- FAQ: View commonly asked questions");
            System.out.print("\n\t- VIEWLOAN: View any outstanding loans");
            System.out.print("\n\t- CHANGEPIN: Change yout ATM and Card Pin");
            System.out.print("\n\t- VIEWINSURANCE: View your current CDIC insurance plan");
            System.out.print("\n\t- GETINSURANCE: Change your CDIC insurance plan");
            System.out.print("\n\t- ASKQUESTION: Send a question to Regal Bank.");
            System.out.print("\n\t- VIEWREQUESTS: View al your previous questions and the answers from Regal Bank");
            System.out.print("\n\t- VIEWINTEREST: View the interest on your savings account.");
            System.out.print("\n\t- VIEWSAVINGS: View balance on savings account.");
            System.out.print("\n\t- VIEWCHEQUEING: View balance on chequeings account.");
            System.out.print("\n\t- VIEWTRANSFERS: View all your past initiated transactions");
            System.out.print("\n\t- MAKETRANSFER: Send money to another user from your chequenings accounts");
            System.out.print("\n\t- PAYBILL: Pay an outstanding bill");

            System.out.print("\n\nTo exit this program, type 'quit' into the shell.");

            
            

            //COMMAND LOOP: This is the scanner loop that reads commands. Each command matches a Use Case
            while (scanner.hasNextLine()) {
                System.out.print(">");
                String action = scanner.nextLine();


                //EXIT PROGRAM
                if (action.equalsIgnoreCase("exit") || action.equalsIgnoreCase("close") || action.equalsIgnoreCase("quit")){ 
                    break; //This line will break out of the command-loop when user enters 'exit' or 'close'

                } else if (action.equalsIgnoreCase("INFO")) { 
                    System.out.print("\n");


                }



                //USE CASE 9: Check Interest and USE CASE 11: View Savings
                //Use Cases Done So Far: 1 9 11
                else if (action.equalsIgnoreCase("VIEWSAVINGS") || action.equalsIgnoreCase("VIEWINTEREST")) { 
                    double savingsBalance= system.getSavingsBalance(); 
                    double savingsInterest= system.getSavingsInterest();

                    System.out.print("\n__SAVINGS ACCOUNT DATA__");
                    System.out.print("\nBalance: "+savingsBalance);
                    System.out.print("\nMonthly Interest: "+savingsInterest);




                //USE CASE 11: View Savings Account   
                //Use Cases Done So Far: 1 9 11 
                }else if (action.equalsIgnoreCase("VIEWSAVINGSBALANCE")) { 


                    double savingsBalance= system.getSavingsBalance();
                    System.out.print("\n__SAVINGS ACCOUNT BALANCE__");
                    System.out.print("\nBalance: "+savingsBalance);




                //USE CASE 11: View Chequeing Account
                //Use Cases Done So Far: 1 9 11 
                } else if (action.equalsIgnoreCase("VIEWCHEQUEING") || action.equalsIgnoreCase("VIEWCHECKING")){ 



                    double checkingBalance= system.getChequeingsBalance();
                    System.out.print("\n__CHEQUEING ACCOUNT DATA__");
                    System.out.print("\nBalance: "+checkingBalance);





                //USE CASE 15: SEE FORMER QUESTION THAT YOU SENT TO REGAL BANK
                ////Use Cases Done So Far: 1 9 11 15 
                } else if (action.equalsIgnoreCase("VIEWREQUESTS")){ 
                    ArrayList<String> questionAnswers = system.getQuestionAnswers();

                    System.out.print("\n__YOUR REQUEST-ANSWER HISTORY__");

                    for (int i = 0; i < questionAnswers.size(); i++) {
                        System.out.print("\n"+questionAnswers.get(i));
                        if (i!=(questionAnswers.size()-1)){
                            System.out.print("\n");
                        }
                        

                    }
                




                    //USE CASE 5: View CDIC Insurance Plan
                    //////Use Cases Done So Far: 1 5 9 11 15 
                } else if (action.equalsIgnoreCase("VIEWINSURANCE")) { 
                    String insuranceDescription = system.getInsuranceDescription();
                    if (insuranceDescription==null){
                        System.out.print("\nYou do not have a CDIC insurance plan");
                    } else {
                        System.out.print("\n__YOUR CDIC INSURANCE PLAN__");
                        System.out.print("\nYour Insurance Plan: "+insuranceDescription);
                        
   
                    }


                
                //USE CASE 16: Get Insurance Plan
                ////Use Cases Done So Far: 1 9 11 15 16
                } else if (action.equalsIgnoreCase("GETINSURANCE")) { 
                    String earlyInsuranceDescription = system.getInsuranceDescription();
                    

                    String choice = null;
                    boolean insuranceSet= false;
                    boolean validChoice=false;
                    System.out.print("\nThere are 3 CDIC Insurance Plan.\n\t1. Standard CDIC Insurance \n\t2. CDIC Insurance Pro\n\t3. CDIC Insurance Premium");
                    System.out.print("\nEnter the number of the insurance plan you desire:");

                    if (scanner.hasNextLine()){
					    choice = scanner.nextLine();
                    }

                    if (  !(choice.trim().equalsIgnoreCase("1")  || choice.trim().equalsIgnoreCase("2")  || choice.trim().equalsIgnoreCase("3")) ){
                        System.out.print("\nYou did not enter a valid insurance plan");
                    } else if (choice.trim().equalsIgnoreCase("1")){
                        insuranceSet= system.setUserInsurance(1);
                        validChoice= true;

                    } else if (choice.trim().equalsIgnoreCase("2")){
                        insuranceSet= system.setUserInsurance(2);
                        validChoice= true;
                    } else if (choice.trim().equalsIgnoreCase("3")){
                        insuranceSet = system.setUserInsurance(3);
                        validChoice= true;
                    }

                    String lateInsuranceDescription = system.getInsuranceDescription();
                    if (earlyInsuranceDescription.equals(lateInsuranceDescription) && validChoice){
                        System.out.print("\nYou set your insurance plan to the same plan you already had. No changes were made");
                    } else{
                        if (insuranceSet==true && validChoice==true){

                            if (lateInsuranceDescription==null){
                                System.out.print("\nYou do not have a CDIC insurance plan");
                            } else {
                                System.out.print("\n__YOUR NEW CDIC INSURANCE PLAN__");
                                System.out.print("\nYour New Insurance Plan: "+lateInsuranceDescription);
                                    
                            }
    
                        }
                    }


                    
                    
                    
                //USE CASE 14: Send Request/Question
                ////Use Cases Done So Far: 1 9 11 14 15 16
                } else if (action.equalsIgnoreCase("SENDREQUEST")  || action.equalsIgnoreCase("ASKQUESTION") ) { 
                    String question = null;
                    System.out.print("Type the question that you want Regal Bank to answer: ");

                    if (scanner.hasNextLine()){
					    question= scanner.nextLine();
                    }

                    if (system.createNewRequest(question)){
                        System.out.print("\nYour question has been sent successfully.\nTo view the answer, type 'VIEWREQUESTS' into the terminal.");

                    } else{
                        System.out.print("\nFailed to send your request to Regal Bank");
                    }




                    
                //USE CASE 3: Change Pin
                ////Use Cases Done So Far: 1 3 9 11 14 15 16
                } else if (action.equalsIgnoreCase("CHANGEPIN")) { 
                    String currentPinEntrance= null;
                    System.out.print("To change your pin, you must enter your current pin.");
                    System.out.print("\nEnter current pin: ");

                    if (scanner.hasNextLine()){
					    currentPinEntrance= scanner.nextLine();
                    }



                    if(system.validatePinEntry(currentPinEntrance)){
                        String firstNewPin=null;
                        String secondNewPin= null;

                        System.out.print("\nEnter new pin: ");
                        if (scanner.hasNextLine()){
                            firstNewPin= scanner.nextLine();
                        } 

                        System.out.print("\nRetype new pin: ");
                        if (scanner.hasNextLine()){
                            secondNewPin= scanner.nextLine();
                        } 

                        if (firstNewPin.equals(secondNewPin)){
                            if (system.validateNewPin(firstNewPin)){
                                if (system.changePin(firstNewPin)){
                                    System.out.print("\nPin has been successfully changed");
                                }

                            } else{
                                System.out.print("The entererd pin is not valid. It must be a 4 consecutive numeric digits");
                            }
                            
                        } else{
                            System.out.print("\nThe 2 entries do not match. The pin stays unchanged.");
                        }


                    } else{
                        System.out.print("\nYou entered the incorrect pin.");
                    }



                
                //USE CASE 4: View Loan
                ////Use Cases Done So Far: 1 3 4 9 11 14 15 16
                } else if (action.equalsIgnoreCase("VIEWLOAN")){
                    System.out.print("\nEach user can only have one loan.");
                    String loanString= system.getLoanData();

                    if (loanString==null){
                        System.out.print("\nYou do not have an active loan");
                    } else{
                        System.out.print("\nYou have an active loan.");
                        System.out.print("\n"+loanString);
                    }


                //USE CASE 6: FAQ Page
                ////Use Cases Done So Far: 1 3 4 6 9 11 14 15 16
                } else if (action.equalsIgnoreCase("FAQ")){

                    System.out.print("\n__FREQUENTLY ASKED QUESTIONS__");
                    System.out.print("\nQuestion: How do I view the principal on my current loan?");
                    System.out.print("\nAnswer: Type 'viewloan' into the interactive shell.");
                    System.out.print("\n");
                    System.out.print("\nQuestion: How do I change my pin?");
                    System.out.print("\nAnswer: Type the 'changepin' command into the interactive shell.");
                    System.out.print("\n");
                    System.out.print("\nQuestion: How do I ask a custom question to be answered by Regal Bank?");
                    System.out.print("\nAnswer: Type the 'sendrequest' command into the interactive shell.");
                    System.out.print("\nType the 'sendrequest' command into the interactive shell.");


                //USE CASE 10: View Transactions
                //USE CASE 6: FAQ Page
                ////Use Cases Done So Far: 1 3 4 6 9 10 11 14 15 16
                } else if (action.equalsIgnoreCase("VIEWTRANSFERS")){

                    //ArrayList<String> transactions = new system.getTransacHisoroy();

                    

                    ArrayList<String> transactionStrings = system.getTransactions();

                    System.out.print("\nHere is your transaction history:");

                    for (int i = 0; i < transactionStrings.size(); i++) {
                        System.out.print("\n"+transactionStrings.get(i));
                        if (i!=(transactionStrings.size()-1)){
                            System.out.print("\n");
                        }
                    }
                

                }
                //USE CASE 7: Make Transfer
                ////Use Cases Done So Far: 1 3 4 6 7 9 10 11 14 15 16
                else if (action.equalsIgnoreCase("MAKETRANSFER")){


                    String amount=null;
                    String recieverID= null;

                    System.out.print("\nEnter the amount you want transferred from your chequeing account: ");
                    if (scanner.hasNextLine()){
                        amount= scanner.nextLine().trim();
                    } 

                    System.out.print("\nEnter the ID of the reciever: ");
                    if (scanner.hasNextLine()){
                        recieverID= scanner.nextLine().trim();
                    } 

                    system.validateTransferData(amount, recieverID);
                    if (system.validateTransferData(amount, recieverID)){
                        system.makeTransfer(amount, recieverID);
                        System.out.println("The transfer was completed successfully");
                        double checkingBalance= system.getChequeingsBalance();
                        System.out.print("\nYour New Chequeing-Account Balance: "+checkingBalance );

                    }else {
                        System.out.print("\nThe data you entered was not valid.");
                    }
                

                } else if (action.equalsIgnoreCase("INFO")){

                    System.out.print("\nFAQ: View commonly asked questions");
                    //System.out.print("\nVIEWLOAN: View any outstanding loans");
                    //System.out.print("\nCHANGEPIN: Change yout ATM and Card Pin");
                    //System.out.print("\nVIEWINSURANCE: View your current CDIC insurance plan");
                    //System.out.print("\nGETINSURANCE: Change your CDIC insurance plan");
                    //System.out.print("\nASKQUESTION: Send a question to Regal Bank.");
                    //System.out.print("\nVIEWREQUESTS: View al your previous questions and the answers from Regal Bank");
                    //System.out.print("\nVIEWINTEREST: View the interest on your savings account.");
                    //System.out.print("\nVIEWSAVINGS: View balance on savings account.");
                    //System.out.print("\nVIEWCHEQUEINGS: View balance on chequeings account.");
                    //System.out.print("\nVIEWTRANSFERS: View all your past initiated transactions");
                    //System.out.print("\nMAKETRANSFER: Send money to another user from your chequenings accounts");
                    //System.out.print("\nPAYBILL: Pay an outstanding bill");


                
                }

                //USE CASE 8: View Bills
                ////Use Cases Done So Far: 1 3 4 6 7 8 9 10 11 14 15 16
                else if (action.equalsIgnoreCase("VIEWBILLS")){


                    ArrayList<String> billStrings = system.getBillStrings();

                    System.out.print("\nHere are your unpaid bills:");

                    for (int i = 0; i < billStrings.size(); i++) {
                        System.out.print("\n"+billStrings.get(i));
                        if (i!=(billStrings.size()-1)){
                            System.out.print("\n");
                        }
                    }

                
                
                //USE CASE 8: Paybill
                ////Use Cases Done So Far: 1 3 4 6 7 8 9 10 11 14 15 16
                } else if (action.equalsIgnoreCase("PAYBILL")){


                    ArrayList<String> billStrings = system.getBillStrings();
                    

                    System.out.print("\nHere are the bills you need to pay:");

                    for (int i = 0; i < billStrings.size(); i++) {
                        System.out.print("\n"+billStrings.get(i));
                        if (i!=(billStrings.size()-1)){
                            System.out.print("\n");
                        }
                    }


                    System.out.print("\n");
                    String billID= null;



                    System.out.print("\nEnter the billID of the bill you would like to pay: ");
                    if (scanner.hasNextLine()){
                        billID= scanner.nextLine().trim();
                    } 

                    if (system.verifyBillData(billID)){
                        System.out.print("\nBill #"+ billID +" has been paid");


                    } else{
                        System.out.print("\nBill payment failed");
                    }






                    
                }  
                
                
                
                
            }

            //closes the scanner and leaves the program loop; caused by the user typing 'exit' into the command loop
            scanner.close();
            break;

        }
        
    }
}

