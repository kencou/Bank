import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Survey implements Serializable {
    private long serialVersionUID;
    protected String name;
    protected String responseName;
    protected ArrayList<Question> questions = new ArrayList<>();
    protected ArrayList<Object> responses = new ArrayList<>();

    Survey() {}

    protected void addQuestion(Question question) {
        questions.add(question);
    }
    
    protected void addResponse(ArrayList response) {
        responses.add(response);
    }

    protected void displayOptions() {
        System.out.println("-----------------------------------\n" +
                "1) Add a new T/F question\n" +
                "2) Add a new multiple-choice question\n" +
                "3) Add a new short answer question\n" +
                "4) Add a new essay question\n" +
                "5) Add a new date question\n" +
                "6) Add a new matching question\n" +
                "7) Return to previous menu\n" +
                "\nChoose option 1-7: ");
    }

    public void createSurvey(Survey survey) {
        System.out.println("Enter the name of your Survey: ");
        name = Input.readString();
        while (true) {
            String prompt;
            displayOptions();
            int userInput = Input.readInt();
            switch (userInput) {
                case 1:
                    System.out.println("\nEnter a prompt for the T/F question:");
                    prompt = Input.readString();
                    TrueFalse trueFalse = new TrueFalse(prompt);
                    survey.addQuestion(trueFalse);
                    break;
                case 2:
                    System.out.println("\nEnter a prompt for the Multiple-Choice question: ");
                    prompt = Input.readString();
                    MultipleChoice multipleChoice = new MultipleChoice(prompt);
                    multipleChoice.addChoices();
                    survey.addQuestion(multipleChoice);
                    break;
                case 3:
                    System.out.println("\nEnter a prompt for the Short Answer question: ");
                    prompt = Input.readString();
                    ShortAnswer shortAnswer = new ShortAnswer(prompt);
                    survey.addQuestion(shortAnswer);
                    break;
                case 4:
                    System.out.println("\nEnter a prompt for the Essay question: ");
                    prompt = Input.readString();
                    Essay essay = new Essay(prompt);
                    survey.addQuestion(essay);
                    break;
                case 5:
                    System.out.println("\nEnter a prompt for the Date question: ");
                    prompt = Input.readString();
                    ValidDate date = new ValidDate(prompt);
                    survey.addQuestion(date);
                    break;
                case 6:
                    System.out.println("\nEnter a prompt for the Matching question: ");
                    prompt = Input.readString();
                    Matching matching = new Matching(prompt);
                    matching.addPairs();
                    survey.addQuestion(matching);
                    break;
                case 7:
                    name += ".ser";
                    SerializationHelper.serialize(Survey.class, survey, "Surveys" + File.separator, name);
                    return;
                default:
                    System.out.println("Invalid input. Try again.");
                    break;
            }
        }
    }

    public void take() {
        System.out.println("Enter the name of your survey/test response:");
        responseName = Input.readString();
        for (int i = 0; i < questions.size(); i++) {
            questions.get(i).display();
            ArrayList newResponses = questions.get(i).take();
            questions.get(i).addNewResponse(i, newResponses);
            addResponse(newResponses);
        }
        Response newResponse = new Response(responses);
        responseName+=".ser";
        SerializationHelper.serialize(Response.class, newResponse, "Responses" + File.separator, responseName);
        System.out.println("Thank you for taking the survey/test! Your response was recorded.\n");
    }

    public void display() {
        for(int i = 0; i < questions.size(); i++){
            System.out.print((i+1 + ") "));
            questions.get(i).display();
        }
    }

    public void modify(){
        display();
        int chosenQuestion = Input.readInt();
        while (chosenQuestion<0 || chosenQuestion>questions.size()){
            System.out.println("Invalid chosen question to modify. Try again.");
            chosenQuestion = Input.readInt();
        }
        questions.get(chosenQuestion-1).modify();
    }

    public void tabulate() {
        for(int i = 0; i < questions.size(); i++){
            System.out.print((i+1 + ") "));
            questions.get(i).display();
            questions.get(i).tabulate();
            System.out.println();
        }
    }
}
