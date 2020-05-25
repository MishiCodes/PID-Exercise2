import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import attendance.*;
import utility.Utility;

/**
 * This is the main class of the application.
 */
public class Controller {
    private HashMap<LocalDate, ArrayList<Attendance>> attendances;
    private ArrayList<Member> members;
    private Message message;
    private Manager manager;
    private String membersFileName;

    /**
     * Constructor of the Controller class.
     */
    public Controller() {
        attendances = new HashMap<>();
        members = new ArrayList<>();
        message = new Message();
        manager = new Manager();
        membersFileName = null;
    }

    /**
     * Method with the main routine that will keep program running
     * until user wants to quit.
     */
    private void start() {
        message.printWelcome();
        while (true) {
            message.printOptions();
            switch (userInput()) {
                case "1":
                    //Load members file
                    loadMemberList();
                    if(!Utility.checkDuplicateId(members)){
                        message.printDuplicateIdMessage();
                    }
                    break;
                case "2":
                    //Check attendance
                    checkAttendance();
                    break;
                case "3":
                    //Load attendance from file
                    loadAttendanceList();
                    // Display attendance
                    printAttendance();
                    break;
                case "4":
                    //Add or remove members from a member file
                    editMenu();
                    break;
                case "5":
                    //quit
                    return;
                default:
                    message.printInvalidInput();
            }
            message.printReturnMenu();
            userInput();
        }
    }

    /**
     * Shows the edit member file menu.
     */
    private void editMenu(){
        if(members.size() == 0){
            message.printNoMembersInList();
        }
        else{
            while (true) {
                message.printEditOptions();
                switch (userInput()) {
                    case "1":
                        addMember(membersFileName);
                        return;
                    case "2":
                        removeMember(printMembersAndSelectIndex(), membersFileName);
                        return;
                    default:
                        message.printInvalidInput();
                }
                message.printReturnMenu();
                userInput();
            }
        }
    }

    /**
     * Remove a member.
     * @param index index (+1) of the member in members list.
     * @param fileName the name of the members file in which the edited member should be saved.
     */
    private void removeMember(int index, String fileName){
        if(index != -1){
            //remove member
            members.remove(index-1);
            // Save members to file
            manager.writeJsonToFile(manager.convertMembersToJson(members),
                    "member-lists/",fileName);
            message.printMemberRemoved();
        }
        else{
            message.printInvalidInput();
        }
    }

    /**
     * Add a member.
     * @param fileName the name of the members file in which the edited member should be saved.
     */
    private void addMember(String fileName) {
        String quit = ""; //backup for user to be able to quit add mode

        //create an array with the fields to be inserted
        //and another to capture the data inserted
        String[] memberDetails = new String[2];
        String[] memberQuestions = new String[2];
        memberQuestions[0] = ">> Insert member name:";
        memberQuestions[1] = ">> Insert member ID:";

        //Print insert statements and collect user input
        for (int index = 0; index < memberQuestions.length; index++) {
            boolean fieldIsValid = false;
            while (!fieldIsValid) {
                System.out.println(memberQuestions[index]);

                String input = userInput();

                //Check if field is empty
                if (input.isEmpty()) {
                    message.printFieldEmpty();
                    quit = userInput();
                } else { //For not empty fields
                    memberDetails[index] = input;
                    fieldIsValid = true;
                }

                //backup for user to be able to quit add or edit mode menu
                if (quit.equals("0")) {
                    return;
                }
            }
        }

        //add to list
        members.add(new Member(memberDetails[0], memberDetails[1]));

        // Save members to file
        manager.writeJsonToFile(manager.convertMembersToJson(members),
                "member-lists/",fileName);
    }

    /**
     * Loads a specific file with members list.
     */
    private void loadMemberList(){
        members.clear();
        message.printFileNameRequest();
        String fileName = userInput();
        members.addAll(manager.readFileMembers("member-lists/" + fileName));

        if(members.size() > 0)
        {
            //update members file name
            membersFileName = fileName;
            //print success message
            message.printLoadMessage();
        }
    }

    /**
     * Loads the attendance list.
     */
    private void loadAttendanceList(){
        attendances.clear();
        attendances.putAll(manager.readFileAttendance("attendance-lists/attendance-list.json"));
    }

    /**
     * Method that allows user to check attendance for a specific day.
     */
    private void checkAttendance(){
        // if a member file was loaded check attendance
        if(members.size() == 0){
            message.printNoMembersInList();
        }
        else {
            message.printDateQuestion();
            LocalDate date = Utility.convertDate(userInput());
            if (date != null) {
                //Load attendances from file
                loadAttendanceList();
                //Check attendance
                loopAttendance(date);
                // Save attendance to file
                manager.writeJsonToFile(manager.convertAttendanceToJson(attendances),
                        "attendance-lists/","attendance-list.json");
                message.printSaveMessage();
            } else {
                message.printInvalidInput();
            }
        }
    }

    /**
     * Loops through members list and takes attendance.
     * @param date date of the day from which the user should check attendance.
     */
    private void loopAttendance(LocalDate date) {
        ArrayList<Attendance> attendance = new ArrayList<>();
        int present = 0;
        int absent = 0;
        for(Member member : members){
            boolean quit = false;
            while (!quit){
                //print the member
                System.out.println(member.getMemberID() + ", " + member.getMemberName());

                //print message
                message.printAttendanceMessage();

                switch (userInput().toLowerCase()){
                    case "y":
                        attendance.add(new Attendance(member, true));
                        quit = true;
                        present++;
                        break;
                    case "n":
                        attendance.add(new Attendance(member, false));
                        quit = true;
                        absent++;
                        break;
                    default:
                        message.printInvalidInput();
                        break;
                }
            }
        }

        attendances.put(date,attendance);

        message.printAttendanceComplete(present, absent);
    }

    /**
     * Collect user input.
     * @return user input as a String.
     */
    public String userInput() {
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    /**
     * Prints the attendance list for a given date.
     */
    public void printAttendance(){
        // if is there any data in the attendance file
        if(attendances.size() == 0){
            message.printNoAttendancesInList();
        }
        else {
            //show list of dates
            attendances.entrySet().stream()
                    .map(HashMap.Entry::getKey)
                    .forEach(System.out::println);

            //User chooses a date
            //If it is valid show attendance for that day
            message.printDateQuestion();
            LocalDate date = Utility.convertDate(userInput());
            if (date != null) {
                attendances.entrySet().stream()
                        .filter(map -> map.getKey().isEqual(date))
                        .map(HashMap.Entry::getValue)
                        .flatMap(ArrayList::stream)
                        .forEach(System.out::println);
            } else {
                message.printInvalidInput();
            }
        }
    }

    /**
     * Prints the members list and selects an index.
     * @return an int which is the index of the member in the list.
     */
    public int printMembersAndSelectIndex() {
        // if is there any data in the member file
        if (members.size() == 0) {
            message.printNoMembersInList();
        }
        else{
            members.stream()
                    .map(member -> "index = '" + (members.indexOf(member) + 1) + "', " + member.toString())
                    .forEach(System.out::println);

            message.printSelectIndex();
        }

        return Utility.convertIndexToInt(userInput());
    }

    /**
     * Start of the application.
     * @param args
     */
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.start();
    }
}