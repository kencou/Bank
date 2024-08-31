import java.util.Scanner;

public class Input {
    public static String readString() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (input.length() == 0) {
            System.out.println("Input cannot be empty!");
            scanner = new Scanner(System.in);
            input = scanner.nextLine();
        }
        return input;
    }

    public static int readInt() {
        String input = readString();
        int inputInt = -1;
        try{
            inputInt = Integer.parseInt(input);
        } catch (NumberFormatException e){
            System.out.println("Invalid input: not an integer.");
            inputInt = readInt();
        }
        return inputInt;
    }
}
