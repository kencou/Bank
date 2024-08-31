import java.io.*;
import java.util.ArrayList;

public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<ArrayList> responses;
    private double grade = 0;
    private String responseName;

    Response(ArrayList userResponses){
        this.responses = userResponses;
    }

    public String getResponseAt(int index){
        String response = "";
        for (int i=0; i < responses.get(index).size(); ++i){
            response += responses.get(index).get(i) + "\n";
        }
        return response;
    }

    public double getGrade(){
        return grade;
    }

    public void setGrade(double newGrade){
        grade = newGrade;
    }

    public void modify(int toModify, ArrayList<Object> newAns){
        responses.set(toModify, newAns);
    }

    public void setName(String name){
        responseName = name;
    }

    public String getName(){
        return responseName;
    }
}