import javax.xml.validation.Validator;
import java.util.ArrayList;

public class TrueFalse extends MultipleChoice{
    private static final long serialVersionUID = 1L;

    TrueFalse(String prompt){
        super(prompt);
    }

    @Override
    public void display(){
        System.out.print(prompt + "\n" + "T/F" + "\n");
    }

    @Override
    public ArrayList take() {
        ArrayList<String> userInputs = new ArrayList<>();
        System.out.println("Enter T or F");
        String input = Input.readString();
        while(InputValidator.validateTrueFalse(input)) {
            System.out.println("Invalid input, Enter T or F: ");
            input = Input.readString();
        }
        userInputs.add(input);
        return userInputs;
    }

    @Override
    public void modify(){
        System.out.println("Do you wish to modify the prompt?(yes/no)\n");
        String promptModify = Input.readString();
        while (InputValidator.validateYesNo(promptModify)){
            promptModify = Input.readString();
        }
        if(promptModify.equalsIgnoreCase("yes")) {
            System.out.println("Enter a new prompt:");
            String newPrompt = Input.readString();
            prompt = newPrompt;
        }
    }
}