public class Main {
    public static void main(String[] args) {
        start();
    }

    public static void start() {
        System.out.println(
                "Choose an option\n"+
                        "1) Survey\n"+
                        "2) Test");
        int userChoice = Input.readInt();
        if(userChoice==1){
            surveyMenu();
        }
        else if(userChoice==2){
            testMenu();
        }
        else {
            System.out.println("Please choose 1 or 2.\n");
        }
    }

    public static void surveyMenu() {
        SurveyMenu menu = new SurveyMenu();
        while (true) {
            int userInput = menu.startMenu();
            switch (userInput){
                case 1:
                    menu.create();
                    break;
                case 2:
                    menu.display();
                    break;
                case 3:
                    menu.load();
                    break;
                case 4:
                    menu.save();
                    break;
                case 5:
                    menu.take();
                    break;
                case 6:
                    menu.modify();
                    break;
                case 7:
                    menu.tabulate();
                case 8:
                    start();
                default:
                    System.out.println("Choose an option 1-7. Try again.");
                    break;
            }
        }
    }

    public static void testMenu() {
        TestMenu menu = new TestMenu();
        while (true) {
            int userInput = menu.startMenu();
            switch (userInput){
                case 1:
                    menu.create();
                    break;
                case 2:
                    menu.display();
                    break;
                case 3:
                    menu.displayWithAnswers();
                    break;
                case 4:
                    menu.load();
                    break;
                case 5:
                    menu.save();
                    break;
                case 6:
                    menu.take();
                    break;
                case 7:
                    menu.modify();
                    break;
                case 8:
                    menu.tabulate();
                    break;
                case 9:
                    menu.grade();
                    break;
                case 10:
                    menu.loadedTest = null;
                    start();
                default:
                    System.out.println("Choose an option 1-10. Try again.");
                    break;
            }
        }
    }
}
