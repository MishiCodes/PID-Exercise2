import org.junit.Test;
import attendance.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ManagerTest {
    @Test
    public void checkMemberListNotEmptyTrue() {
        Manager fm = new Manager();
        ArrayList<Member> listOfMembers = new ArrayList<>();
        listOfMembers.addAll(fm.readFileMembers("src/test/resources/member-list.json"));
        assertTrue(listOfMembers.size() >0);
    }

    @Test
    public void writeMemberFileNotEmpty(){
        Manager fm = new Manager();

        //Create a list with 2 members
        ArrayList<Member> listOfMembers = new ArrayList<>();
        listOfMembers.add(new Member("Name1", "id1"));
        listOfMembers.add(new Member("Name2", "id2"));

        //Write the file with the members
        fm.writeJsonToFile(fm.convertMembersToJson(listOfMembers), "src/test/resources/","member-list.json");

        //Check if file is empty
        int fileSize;
        try{
            fileSize = Files.readAllLines(Paths.get("src/test/resources/member-list.json")).size();
        }
        catch(IOException e){
            fileSize = -1;
        }

        assertTrue(fileSize > 0);
    }

    @Test
    public void writeAttendanceFileNotEmpty(){
        Manager fm = new Manager();

        //Create two dates
        LocalDate date1 = LocalDate.parse("2020-10-25");
        LocalDate date2 = LocalDate.parse("2020-10-19");

        //Create and populate two array lists
        ArrayList<Attendance> listOfAttendance1 = new ArrayList<>();
        listOfAttendance1.add(new Attendance(new Member("Name1", "id1"),false));
        listOfAttendance1.add(new Attendance(new Member("Name2", "id2"),true));

        ArrayList<Attendance> listOfAttendance2 = new ArrayList<>();
        listOfAttendance2.add(new Attendance(new Member("Name1", "id1"),true));
        listOfAttendance2.add(new Attendance(new Member("Name2", "id2"),true));

        //Create an HashMap
        HashMap<LocalDate, ArrayList<Attendance>> attendances = new HashMap<>();
        attendances.put(date1, listOfAttendance1);
        attendances.put(date2, listOfAttendance2);

        fm.writeJsonToFile(fm.convertAttendanceToJson(attendances), "src/test/resources/","attendance-list.json");

        //Check if file is empty
        int fileSize;
        try{
            fileSize = Files.readAllLines(Paths.get("src/test/resources/attendance-list.json")).size();
        }
        catch(IOException e){
            fileSize = -1;
        }

        assertTrue(fileSize > 0);
    }

    @Test
    public void readAttendanceFileNotEmpty(){
        Manager fm = new Manager();

        HashMap<LocalDate, ArrayList<Attendance>> attendances =
                fm.readFileAttendance("src/test/resources/attendance-list.json");

        Controller controller = new Controller();
    }
}