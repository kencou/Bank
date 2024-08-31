import java.io.File;
import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Test extends Survey {
    private static final long serialVersionUID = 1L;
    protected ArrayList<Object> correctResponses = new ArrayList<>();
    protected Response correctResponse = new Response(new ArrayList());

    public void createTest(Test test){
        System.out.println("Enter a name for your test: ");
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
                    test.addQuestion(trueFalse);
                    addAnswers(trueFalse);
                    break;
                case 2:
                    System.out.println("\nEnter a prompt for the Multiple-Choice question: ");
                    prompt = Input.readString();
                    MultipleChoice multipleChoice = new MultipleChoice(prompt);
                    multipleChoice.addChoices();
                    test.addQuestion(multipleChoice);
                    addAnswers(multipleChoice);
                    break;
                case 3:
                    System.out.println("\nEnter a prompt for the Short Answer question: ");
                    prompt = Input.readString();
                    ShortAnswer shortAnswer = new ShortAnswer(prompt);
                    test.addQuestion(shortAnswer);
                    addAnswers(shortAnswer);
                    break;
                case 4:
                    System.out.println("\nEnter a prompt for the Essay question: ");
                    prompt = Input.readString();
                    Essay essay = new Essay(prompt);
                    test.addQuestion(essay);
                    addAnswers(essay);
                    break;
                case 5:
                    System.out.println("\nEnter a prompt for the Date question: ");
                    prompt = Input.readString();
                    ValidDate date = new ValidDate(prompt);
                    test.addQuestion(date);
                    addAnswers(date);
                    break;
                case 6:
                    System.out.println("\nEnter a prompt for the Matching question: ");
                    prompt = Input.readString();
                    Matching matching = new Matching(prompt);
                    matching.addPairs();
                    test.addQuestion(matching);
                    addAnswers(matching);
                    break;
                case 7:
                    addCorrectResponses();
                    name += ".ser";
                    SerializationHelper.serialize(Test.class, test, "Tests" + File.separator, name);
                    return;
                default:
                    System.out.println("Please enter a valid number from 1-7. Try again.");
                    break;
            }
        }
    }

    public void addAnswers(Question questions) {
        if(questions instanceof Essay){
            ArrayList<Object> noRightAnswer = new ArrayList<>();
            noRightAnswer.add(" ");
            correctResponses.add(noRightAnswer);
        }
        else{
            System.out.println("Enter the correct answer(s) to the question.");
            ArrayList<Object> rightResponse = questions.take();
            correctResponses.add(rightResponse);
        }
    }

    public void addCorrectResponses(){
        correctResponse = new Response(correctResponses);
        responseName = name + ".ser" +" - Correct Answer";
        correctResponse.setName(responseName);
        SerializationHelper.serialize(Response.class, correctResponse, "Correct Answers" + File.separator, responseName);
        System.out.println("Correct Responses are added");
    }

    public void saveCorrectResponses(Response toSave){
        SerializationHelper.serialize(Response.class, toSave, "Correct Answers" + File.separator, responseName);
    }

    public void displayWithAnswers(Response loadedAnswer) {
        for(int i = 0; i < questions.size(); i++){
            System.out.print((i+1 + ") "));
            questions.get(i).display();
            if(!(questions.get(i) instanceof Essay)){
                System.out.println("Correct Answer: ");
                System.out.println(loadedAnswer.getResponseAt(i));
            }
        }
    }

    public void grade(Response loadedAnswer, Response loadedResponse) {
        double Essays = 0;
        double correctCount= 0;
        for (int i = 0; i < questions.size(); i++) {
            if(questions.get(i) instanceof Essay){
                ++Essays;
            } else {
                if((loadedAnswer.getResponseAt(i)).equalsIgnoreCase(loadedResponse.getResponseAt(i))){
                    ++correctCount;
                }
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        loadedResponse.setGrade(Double.parseDouble(decimalFormat.format((correctCount/questions.size()) * 100)));
        double actualGraded = (((questions.size()-Essays)*100)/ questions.size());
        actualGraded = Double.parseDouble(decimalFormat.format(actualGraded));
        System.out.println("You received an "+ loadedResponse.getGrade() + " on the test. The test was worth 100 points.");
        if(Essays>0){
            System.out.println("Only " + actualGraded + " of those points could be auto graded because there was " + (int)Essays + " essay question(s).\n");
        }
    }

    public void modify(Response loadedAnswer) {
        display();
        int chosenQuestion = Input.readInt();
        while (chosenQuestion<0 || chosenQuestion>questions.size()){
            System.out.println("Invalid chosen question to modify. Try again.");
            chosenQuestion = Input.readInt();
        }
        chosenQuestion--;
        questions.get(chosenQuestion).modify();
        if(!(questions.get(chosenQuestion) instanceof Essay)){
            System.out.println("Do you wish to modify the correct answer to the question? (Yes/No)");
            String userChoice = Input.readString();
            while (InputValidator.validateYesNo(userChoice)) {
                userChoice = Input.readString();
            }
            if(userChoice.equalsIgnoreCase("yes")){
                System.out.println("Please enter the new correct answer for the question:");
                ArrayList<Object> newAnswer = questions.get(chosenQuestion).take();
                loadedAnswer.modify(chosenQuestion, newAnswer);
            }
        }
    }
}
