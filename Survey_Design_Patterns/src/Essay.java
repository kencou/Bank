import java.util.ArrayList;

public class Essay extends Question{
    private static final long serialVersionUID = 1L;

    Essay(String prompt){
        super(prompt);
    }

    @Override
    public ArrayList take() {
        ArrayList<Object> userInput = new ArrayList<>();
        String userResponse;
        for (int i=0; i < numOfResponses; i++) {
            userResponse = Input.readString();
            userInput.add(userResponse);
        }
        return userInput;
    }

    public void modify(){
        System.out.println("Enter your new prompt:");
        String new_prompt = Input.readString();
        prompt = new_prompt;
    }

    public void display() {
        System.out.print(prompt);
        System.out.println();
    }

    public void getNumberOfExpectedResponses() {
        System.out.println("How many responses should the user answer?");
        numOfResponses = Input.readInt();
    }

    public void tabulate(){
        for (int i = 0; i < allResponses.size(); i++) {
            for (int count=0; count < allResponses.get(i).size(); ++count){
                System.out.println(allResponses.get(i).get(count));
            }
        }
    }
}