import java.io.File;
import java.util.regex.Pattern;

public class SurveyMenu {
    Survey survey = new Survey();
    static Survey loadedSurvey;
    static String loadedSurveyName;

    SurveyMenu() {}

    public int startMenu() {
        displayMenu();
        int userInput = Input.readInt();
        return userInput;
    }

    public void displayMenu() {
        System.out.println("-----------------------------------\n" +
                "1) Create a new Survey\n" +
                "2) Display an existing Survey\n" +
                "3) Load an existing Survey\n" +
                "4) Save the current Survey\n" +
                "5) Take the current Survey\n" +
                "6) Modifying the current Survey\n" +
                "7) Quit\n" +
                "\nChoose option 1-7: ");
    }

    public void create() {
        survey.createSurvey(survey);
    }

    public void display() {
        if(loadedSurvey == null){
            System.out.println("There is no current loaded Survey to display.");
            return;
        }
        loadedSurvey.display();
    }

    public void load() {
        File directory = new File("Surveys");
        File[] files = directory.listFiles();
        if(files == null || files.length == 0){
            System.out.println("Survey is empty, Unable to load!");
            return;
        }
        System.out.println("Survey list");
        for(int i =0; i < files.length; i++){
            System.out.println((i+1)+ ") " + files[i].getName());
        }
        System.out.println("Select an option: ");
        int chosenSurvey = Input.readInt();
        while (chosenSurvey > files.length || chosenSurvey < 1){
            System.out.println("Invalid option, try again: ");
            chosenSurvey = Input.readInt();
        }
        if(files[chosenSurvey-1].isFile()){
            loadedSurvey = SerializationHelper.deserialize(Survey.class,files[(chosenSurvey-1)].getPath());
            loadedSurveyName = files[(chosenSurvey-1)].getPath().split(Pattern.quote(File.separator))[1];
        }
        else{
            System.out.println("The loaded survey is not a file. Try again.");
        }
    }

    public void save() {
        if(loadedSurvey == null){
            System.out.println("There is no current loaded Survey to save.");
            return;
        }
        SerializationHelper.serialize(Survey.class, loadedSurvey, "Surveys" + File.separator, loadedSurveyName);
        System.out.println("Survey is saved in the path: Surveys" + File.separator + loadedSurveyName);
    }

    public void take() {
        if(loadedSurvey == null){
            System.out.println("There is no current loaded Survey to take.");
            return;
        }
        loadedSurvey.take();
    }

    public void modify() {
        if(loadedSurvey == null){
            System.out.println("There is no current loaded Survey to modify.");
            return;
        }
        loadedSurvey.modify();
    }

    public void tabulate() {
        if(loadedSurvey == null){
            System.out.println("There is no current loaded Survey to tabulate.");
            return;
        }
        loadedSurvey.tabulate();
    }
}
