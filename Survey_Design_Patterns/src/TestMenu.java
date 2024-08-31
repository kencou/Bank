import java.io.File;
import java.util.regex.Pattern;

public class TestMenu {
    Test test = new Test();
    static Test loadedTest;
    static String loadedTestName;
    static Response loadedAnswer;
    static Response loadedResponse;
    static String loadedAnswerName;

    TestMenu() {}

    public int startMenu() {
        displayMenu();
        int userInput = Input.readInt();
        return userInput;
    }

    public void displayMenu() {
        System.out.println("-----------------------------------\n" +
                "1) Create a new Test\n" +
                "2) Display an existing Test without correct answers \n" +
                "3) Display an existing Test with correct answers \n" +
                "4) Load an existing Test\n" +
                "5) Save the current Test\n" +
                "6) Take the current Test\n" +
                "7) Modify the current Test\n" +
                "8) Tabulate a Test\n" +
                "9) Grade a Test\n" +
                "10) Return to the previous menu\n" +
                "\nChoose option 1-10: ");
    }

    public void create() {
        test.createTest(test);
    }

    public void display() {
        if(loadedTest == null){
            System.out.println("There is no current loaded Test to display.");
            return;
        }
        loadedTest.display();
    }

    public void load() {
        File directory = new File("Tests");
        File[] files = directory.listFiles();
        if(files == null || files.length == 0){
            System.out.println("Test is empty, Unable to load!");
            return;
        }
        System.out.println("Test list");
        for(int i =0; i < files.length; i++){
            System.out.println((i+1)+ ") " + files[i].getName());
        }
        System.out.println("Select an option: ");
        int chosenTest = Input.readInt();
        while (chosenTest > files.length || chosenTest < 1){
            System.out.println("Invalid option, try again: ");
            chosenTest = Input.readInt();
        }
        System.out.println("Loading a test");
        if(files[chosenTest-1].isFile()){
            loadedTest = SerializationHelper.deserialize(Test.class,files[(chosenTest-1)].getPath());
            loadedTestName = files[(chosenTest-1)].getPath().split(Pattern.quote(File.separator))[1];
        }
        else{
            System.out.println("The loaded test is not a file. Try again.");
        }

        System.out.println("Loading a correct answer");
        File directoryCorrectAnswers = new File("Correct Answers");
        File[] filesCorrectAnswers = directoryCorrectAnswers.listFiles();
        boolean success = false;
        for(File myFile : filesCorrectAnswers){
            if(myFile.getName().startsWith(loadedTestName)) {
                loadedAnswer = SerializationHelper.deserialize(Response.class, myFile.getPath());
                loadedAnswerName = myFile.getPath().split(Pattern.quote(File.separator))[1];
                success = true;
            }
        }
        if (!success){
            System.out.println("Error loading a correct answer for the loaded survey");
        }
    }

    public void loadResponse() {
        File directory = new File("Responses");
        File[] files = directory.listFiles();
        if(files == null || files.length == 0){
            System.out.println("Response is empty, Unable to load!");
            return;
        }
        System.out.println("Response list");
        int counter = 0;
        for(File file : files){
            System.out.println(file.getName());
            if((file.getName()).startsWith(loadedTestName)){
                System.out.println((counter + 1) + ")" + loadedTestName + " - " + file.getName());
                counter++;
            }
        }
        if(counter == 0){
            System.out.println("No responses were found associated with the chosen test.");
            System.out.println("Take a test first before grading.\n");
            return;
        }
        System.out.println("Select an option: ");
        int chosenResponse = Input.readInt();
        while (chosenResponse > files.length || chosenResponse < 1){
            System.out.println("Invalid option, try again: ");
            chosenResponse = Input.readInt();
        }
        boolean fileFound = false;
        for(File file : files){
            if (file.getName().startsWith(loadedTestName)){
                chosenResponse--;
                if (chosenResponse == 0) {
                    loadedResponse = SerializationHelper.deserialize(Response.class, file.getPath());
                    fileFound = true;
                    break;
                }
            }
        }
        if (!fileFound) {
            System.out.println("File is not found.");
        }
    }

    public void save() {
        if(loadedTest == null){
            System.out.println("There is no current loaded Test to save.");
            return;
        }
        loadedTest.saveCorrectResponses(loadedAnswer);
        SerializationHelper.serialize(Test.class, loadedTest, "Tests" + File.separator, loadedTestName);
        System.out.println("Test is saved in the path: Tests" + File.separator + loadedTestName);
    }

    public void take() {
        if(loadedTest == null){
            System.out.println("There is no current loaded Test to take.");
            return;
        }
        loadedTest.take();
    }

    public void modify() {
        if(loadedTest == null){
            System.out.println("There is no current loaded Test to modify.");
            return;
        }
        loadedTest.modify(loadedAnswer);
    }

    public void tabulate() {
        if(loadedTest == null){
            System.out.println("There is no current loaded Test to tabulate.");
            return;
        }
        loadedTest.tabulate();
    }

    public void grade() {
        System.out.println("Select an existing test to grade:");
        load();
        loadResponse();
        System.out.println("Select an existing response set:");
        try {
            loadedTest.grade(loadedAnswer, loadedResponse);
        } catch (Exception e){
            System.out.println("Add a response to the chosen test before grading.");
        }
    }

    public static void displayWithAnswers() {
        if (loadedTest == null) {
            System.out.println("Load a test first in order to display it.");
        } else if (loadedAnswer == null) {
            System.out.println("Load an answer first in order to display it.");
        } else {
            loadedTest.displayWithAnswers(loadedAnswer);
        }
    }
}
