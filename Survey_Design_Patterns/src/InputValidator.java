public class InputValidator {

    public static boolean validateYesNo(String input) {
        if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("no")) {
            return false;
        }
        System.out.println("Enter Yes or No.\n");
        return true;
    }

    private static boolean isLeap (int year) {
        if (year % 400 == 0) {
            return true;
        } else if (year % 4 == 0 && year % 100 != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateDate(String date){
        if (date.length() != 10) {
            return true;
        }
        if ((date.charAt(4) != '-') || (date.charAt(7) != '-')){
            return true;
        }
        String[] dates = date.split("-");
        // Check Year, month, and day are integers
        for(int i = 0; i <= 3; i++){
            if (dates[0].charAt(i) < '0' || dates[0].charAt(i) > '9') {
                return true;
            }
        }
        for(int i = 0; i <= 1; i++){
            if (dates[1].charAt(i) < '0' || dates[1].charAt(i) > '9') {
                return true;
            }
        }
        for(int i = 0; i <= 1; i++){
            if (dates[2].charAt(i) < '0' || dates[2].charAt(i) > '9') {
                return true;
            }
        }
        // Validate year, month, and day
        int year = Integer.parseInt(dates[0]);
        int month = Integer.parseInt(dates[1]);
        int day = Integer.parseInt(dates[2]);
        if (month==0 || day==0 || month>12 || day>31) {
            return true;
        }
        if (isLeap(year) && month==2 && day>29) {
            return true;
        } else if(month==2 && day>28){
            return true;
        }
        if (month==4 || month==6 || month==9 || month==11){
            if (day>30){
                return true;
            }
        }
        return false;
    }

    public static boolean validateTrueFalse(String input){
        return !input.equalsIgnoreCase("T") && !input.equalsIgnoreCase("F");
    }

    public static boolean validateMatching(String input){
        String right = input.substring(1);
        if (input.charAt(0) < 'A' || input.charAt(0) > 'Z') {
            return true;
        }
        try{
            int inputInt = Integer.parseInt(right);
        } catch (NumberFormatException e){
            return true;
        }
        return false;
    }

    public static boolean validateMultipleChoice(String input){
        if (input.length() != 1){
            return true;
        }
        if (input.charAt(0) < 'A' || input.charAt(0) > 'Z') {
            return true;
        }
        return false;

    }
}
