import javax.xml.validation.Validator;
import java.util.ArrayList;

public class Matching extends Question{
    private static final long serialVersionUID = 1L;
    private final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private ArrayList<String> left = new ArrayList<>();
    private ArrayList<String> right = new ArrayList<>();

    Matching(String prompt){
        super(prompt);
    }

    @Override
    public void display(){
        System.out.print(prompt);
        System.out.println();
        for(int i = 0; i < left.size(); i++){
            System.out.print(letters.charAt(i) + ") " + left.get(i));
            System.out.print(String.format("\t" + String.valueOf(i+1) + ") " + right.get(i)));
            System.out.println();
        }
    }

    @Override
    public void modify(){
        System.out.println("Do you wish to modify the prompt?(yes/no)");
        String promptModify = Input.readString();
        while (InputValidator.validateYesNo(promptModify)){
            promptModify = Input.readString();
        }
        if(promptModify.equalsIgnoreCase("yes")) {
            System.out.println("Enter new prompt:");
            String new_prompt = Input.readString();
            prompt = new_prompt;
        }
        System.out.println("Do you wish to modify the left option?(Yes/No)");
        String modifyLeft = Input.readString();
        while (InputValidator.validateYesNo(modifyLeft)){
            modifyLeft = Input.readString();
        }
        if (modifyLeft.equalsIgnoreCase("yes")){
            System.out.println("Please enter the left pair to modify.");
            String userInput = Input.readString();
            while (!left.contains(userInput)) {
                System.out.println("Enter a valid choice, a whole sentence.");
                userInput = Input.readString();
            }
            System.out.println("Enter a new option:");
            String newOption = Input.readString();
            for (int i = 0; i < left.size(); i++) {
                if(left.get(i).equalsIgnoreCase(userInput)){
                    left.set(i,newOption);
                }
            }
        }
        System.out.println("Do you wish to modify the right option?(Yes/No)");
        String modifyRight = Input.readString();
        while (InputValidator.validateYesNo(modifyRight)){
            modifyRight = Input.readString();
        }
        if (modifyRight.equalsIgnoreCase("yes")){
            System.out.println("Please enter the Right pair to modify.");
            String userInput = Input.readString();
            while (!right.contains(userInput)) {
                System.out.println("Enter a valid choice, a whole sentence.");
                userInput = Input.readString();
            }
            System.out.println("Enter a new option:");
            String newOption = Input.readString();
            for (int i = 0; i < right.size(); i++) {
                if(right.get(i).equalsIgnoreCase(userInput)){
                    right.set(i,newOption);
                }
            }
        }
        numOfResponses = right.size();
    }

    @Override
    public ArrayList take() {
        ArrayList<Object> userInput = new ArrayList<>();
        System.out.println("Match left and right. example, A1");
        for (int i=0; i < numOfResponses; i++) {
            String input = Input.readString();
            while (InputValidator.validateMatching(input)) {
                System.out.println("Invalid response, Enter example: A1");
                input = Input.readString();
            }
            userInput.add(input);
        }
        return userInput;
    }

    public void addPairs() {
        System.out.println("Please enter the amount of pairs for your matching question.");
        int amount = Input.readInt();
        while(amount <= 0){
            System.out.println("Enter a valid integer greater than 0. Try again:");
            amount = Input.readInt();
        }
        numOfResponses = amount;
        for(int i = 0; i < amount; i++){
            System.out.println("Enter a left side: ");
            String leftOption = Input.readString();
            left.add(leftOption);
            System.out.println("Enter a right side: ");
            String rightOption = Input.readString();
            right.add(rightOption);
        }
    }
}
