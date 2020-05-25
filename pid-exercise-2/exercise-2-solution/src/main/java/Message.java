/**
 * This class represents an user interface.
 */
public class Message {

    /**
     * Constructor of View class.
     */
    public Message(){
    }

    /**
     * Print out a Welcome message to the user.
     */
    public void printWelcome() {
        System.out.println("Welcome to Attendance application!\n");
    }

    /**
     * Print main menu.
     */
    public void printOptions(){
        System.out.println("Pick an option:");
        String[] actions = {"(1) Load member file", "(2) Check attendance",
               "(3) Display attendance", "(4) Create new member file", "(5) Quit"};
        for (int i = 0; i < actions.length; i++) {
            System.out.println(actions[i]);
        }
    }

    /**
     * Print edit menu.
     */
    public void printEditOptions(){
        System.out.println("Pick an option:");
        String[] actions = {"(1) Add a member", "(2) Remove a member"};
        for (int i = 0; i < actions.length; i++) {
            System.out.println(actions[i]);
        }
    }

    /**
     * Print a message asking for any input to return to the main menu
     */
    public void printReturnMenu() {
        System.out.println("Please, press Enter to return to the menu.");
    }

    /**
     * Print a message stating that the list was loaded.
     */
    public void printLoadMessage() {
        System.out.println("List loaded into the application.");
    }

    /**
     * Print a message stating that the list was saved.
     */
    public void printSaveMessage() {
        System.out.println("List saved in the file.");
    }

    /**
     * Print a message asking the user to insert a date.
     */
    public void printDateQuestion() {
        System.out.println("Insert a date to check attendance:");
    }

    /**
     * Print a message stating that the input was invalid.
     */
    public void printInvalidInput() {
        System.out.println("Invalid input.");
    }

    /**
     * Print a message asking user to mark attendance
     */
    public void printAttendanceMessage() {
        System.out.println("Check attendance by pressing Y/N:");
    }

    /**
     * Print a message asking user to load members
     */
    public void printNoMembersInList() {
        System.out.println("You need to load members into the application first.");
    }

    /**
     * Print a message asking user to mark attendance
     */
    public void printNoAttendancesInList() {
        System.out.println("There are no attendance sheets to check.");
    }

    /**
     * Print a message showing that attendance is complete.
     */
    public void printAttendanceComplete(int present, int absent) {
        System.out.println("Attendance complete. From the " + (present + absent) + " people in the list, "
                                + present + " were present, while "
                                + absent + " were absent.");
    }

    /**
     * Print a message stating that are duplicate ids.
     */
    public void printDuplicateIdMessage() {
        System.out.println("WARNING: the list has members with duplicate ids!");
    }

    /**
     * Print a message requesting the name of a file with a members list.
     */
    public void printFileNameRequest() {
        System.out.println("Write the file name of the member list (json extension must be included. Enter 'example-member-list.json'):");
    }

    /**
     * Print a message stating that wasn't possible to create the directory.
     */
    public void printNoDirectory() {
        System.out.println("Could not create directory. File not saved.");
    }

    /**
     * Print a message stating that the file is empty.
     */
    public void printEmptyFile() {
        System.out.println("Could not retrieve data. File is empty.");
    }

    /**
     * Print a message stating that file not found.
     */
    public void printFileNotFound() {
        System.out.println("File not found.");
    }

    /**
     * Print a message requesting user to select the index of a member.
     */
    public void printSelectIndex() {
        System.out.println("Select the index of a member.");
    }

    /**
     * Print a message stating that the user was successfully removed.
     */
    public void printMemberRemoved() {
        System.out.println("Member was successfully removed.");
    }

    /**
     * Print a message stating that the field is empty.
     */
    public void printFieldEmpty() {
        System.out.println(">> Field cannot be empty.");
        System.out.println(">> Press (Enter) to continue or (0) to return to main menu.");
    }
}