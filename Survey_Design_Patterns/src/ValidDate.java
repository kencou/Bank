import java.util.ArrayList;

public class ValidDate extends Question{
    private static final long serialVersionUID = 1L;

    ValidDate(String prompt){
        super(prompt);
    }

    @Override
    public void display(){
        System.out.println(prompt);
        System.out.println("A date should be entered in the following format: YYYY-MM-DD");
    }

    @Override
    public ArrayList take() {
        ArrayList<String> userInput = new ArrayList<>();
        String input = Input.readString();
        while(InputValidator.validateDate(input)) {
            System.out.println("Invalid date, try again - format YYYY-MM-DD!");
            input = Input.readString();
        }
        userInput.add(input);
        return userInput;
    }

    public void modify(){
        System.out.println("Enter your new prompt:");
        String newPrompt = Input.readString();
        prompt = newPrompt;
    }
}