import java.sql.*;

public class Insurance {

    private int policyID;
    private String description = null;

    public Insurance(int ID, String description){
        this.policyID= ID;
        this.description = description;
        
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description= description;
        
    }

    public int getPolicyID(){
        return policyID;
    }
}
