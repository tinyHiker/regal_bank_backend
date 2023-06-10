import java.sql.*;

public class Request{

    private int requestID;
    private String question;
    private String answer = "You inquiry has not yet been answered by our assocaites. Answers are usually given within 2 -3 bussiness days.";
    private User user;

    public Request(int requestID, String question, User user){

        this.question = question;
        this.user = user;

    }

    public void setAnswer(String answer){
        this.answer = answer;

    }

    public int getUserID(){
        return user.getUserID();
    }

    public String getAnswer(){
        return this.answer;
    }

    public String getQuestion(){
        return this.question;
    }

    public int getRequestID(){
        return this.requestID;
    }

    public String getStringRep(){
        return "Question: \'"+this.getQuestion() +"\'\nAnswer: \'"+this.getAnswer()+"\'";
    }
}
