import java.util.ArrayList;

public class MultipleChoice extends Question{
    private static final long serialVersionUID = 1L;
    private final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private ArrayList<String> choices = new ArrayList<>();

    MultipleChoice(String prompt){super(prompt);}

    public void addChoice(String choice){
        choices.add(choice);
    }

    @Override
    public void display(){
        System.out.println(prompt);
        for(int i = 0; i < choices.size(); i++){
            System.out.print(letters.charAt(i) + ") " + choices.get(i) + "\t");
        }
        System.out.println();
    }

    public void addChoices() {
        System.out.println("Enter the number of choices for your multiple-choice question.");
        int amount = Input.readInt();
        while(amount <= 0){
            System.out.println("Enter a valid integer greater than 0. Try again:");
            amount = Input.readInt();
        }
        String choice;
        for(int i = 1; i <= amount; i++){
            System.out.println("Enter choice #" + (i));
            choice = Input.readString();
            addChoice(choice);
        }
    }

    @Override
    public void modify(){
        System.out.println("Do you wish to modify the prompt?(yes/no)");
        String promptModify = Input.readString();
        while (InputValidator.validateYesNo(promptModify)){
            promptModify = Input.readString();
        }
        if (promptModify.equalsIgnoreCase("Yes")) {
            System.out.println("Enter a new prompt:");
            String new_prompt = Input.readString();
            prompt = new_prompt;
        }
        System.out.println("Do you also wish to modify choices?(yes/no)");
        String option = Input.readString();
        while (InputValidator.validateYesNo(option)){
            option = Input.readString();
        }
        if (option.equalsIgnoreCase("yes")) {
            System.out.println("Please enter the choice to modify.");
            display();
            String input = Input.readString();
            while (!choices.contains(input)) {
                System.out.println("Enter a valid choice, the whole sentence");
                input = Input.readString();
            }
            System.out.println("Enter the new choice: ");
            String newChoice = Input.readString();
            for (int i = 0; i < choices.size(); i++) {
                if(choices.get(i).equalsIgnoreCase(input)){
                    choices.set(i, newChoice);
                }
            }
        }
    }

    @Override
    public ArrayList take() {
        ArrayList<String> userInput = new ArrayList<>();
        for (int i=0; i < numOfResponses; i++) {
            String input = Input.readString();
            while (InputValidator.validateMultipleChoice(input)) {
                System.out.println("Invalid input, Example: A");
                input = Input.readString();
            }
            userInput.add(input);
        }
        return userInput;
    }
}