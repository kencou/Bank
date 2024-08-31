import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Question implements Serializable {
    private static final long serialVersionUID = 1L;
    public String prompt;
    protected int numOfResponses = 1;
    protected Response response = new Response(new ArrayList<>());
    protected ArrayList<ArrayList> allResponses = new ArrayList<ArrayList>();

    Question(String prompt) {
        this.prompt = prompt;
    }
    public abstract void display();
    public abstract void modify();

    public void tabulate() {
        ArrayList<String> allElements = new ArrayList<>();
        System.out.println("Responses:");
        for (int i = 0; i < allResponses.size(); i++) {
            int count = 0;
            while(count < allResponses.get(i).size()){
                allElements.add(String.valueOf(allResponses.get(i).get(count)));
                ++count;
            }
        }
        Map<String, Integer> myMap = new HashMap<>();
        for (String e: allElements) {
            Integer count = myMap.get(e);
            if (count == null) {
                count = 0;
            }
            myMap.put(e, count + 1);
        }
        for (Map.Entry<String, Integer> entry: myMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void addNewResponse(int index, ArrayList newResponse){
        if(allResponses.size() <= index){
            allResponses.add(newResponse);
        } else {
            for (int counter=0; counter < newResponse.size(); ++counter){
                allResponses.get(index).add(newResponse.get(counter));
            }
        }
    }

    public ArrayList<Object> take() {
        ArrayList<Object> userInput = new ArrayList<>();
        String input = Input.readString();
        userInput.add(input);
        response = new Response(userInput);
        return userInput;
    }
}
