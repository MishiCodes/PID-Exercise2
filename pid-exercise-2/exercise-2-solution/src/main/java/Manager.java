import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import attendance.*;
import utility.Utility;

/**
 * A class that reads and writes JSON files.
 */
public class Manager {
    Message message;
    /**
     * Constructor of the FileManager.
     */
    public Manager(){
        message = new Message();
    }

    /**
     * Reads the JSON members file and retrieves a list.
     * @param membersFile the path and name of file.
     * @return a list of Members.
     */
    public ArrayList<Member> readFileMembers(String membersFile){
        ArrayList<Member> jsonMembers = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(membersFile)) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            // loop members array
            JSONArray members = (JSONArray) jsonObject.get("members");
            Iterator<String> iterator = members.iterator();
            while (iterator.hasNext()) {
                Object slide = iterator.next();
                JSONObject jsonObject2 = (JSONObject) slide;
                String name = (String) jsonObject2.get("name");
                String id = (String) jsonObject2.get("id");
                Member member = new Member(name, id);
                jsonMembers.add(member);
            }

        } catch (IOException e) {
            message.printFileNotFound();
        } catch (ParseException e) {
            message.printEmptyFile();
        }

        return jsonMembers;
    }

    /**
     * Converts the members list into a JSON object.
     * @param members a list with members.
     * @return a json object.
     */
    public JSONObject convertMembersToJson(ArrayList<Member> members){
        //Main JSON object
        JSONObject object = new JSONObject();

        //Create a JSON array
        JSONArray array = new JSONArray();

        for(int index = 0; index < members.size(); index++)
        {
            //Second JSON object with an array
            JSONObject arrayElement = new JSONObject();
            arrayElement.put("name", members.get(index).getMemberName());
            arrayElement.put("id", members.get(index).getMemberID());

            //insert in the array
            array.add(arrayElement);
        }

        //put everything inside the object
        object.put("members", array);

        return object;
    }

    /**
     * Write a JSON object to a file.
     * @param object the json object to be written on the file.
     * @param fileName the name of file to be written.
     * @param filePath the name of the directory where the file should be written.
     */
    public void writeJsonToFile(JSONObject object, String filePath, String fileName){
        //Check if directory exists, otherwise create it
        File directory = new File(filePath);
        if(!directory.isDirectory()){
            boolean createDirectory = directory.mkdir();
            if(!createDirectory){
                message.printNoDirectory();
                return;
            }
        }

        //Write on the file
        try (FileWriter file = new FileWriter(filePath + fileName)) {
            file.write(object.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the JSON attendance file and retrieves a Map.
     * @param attendanceFile the path and name of file.
     * @return a Map of Attendances.
     */
    public HashMap<LocalDate, ArrayList<Attendance>> readFileAttendance(String attendanceFile){
        HashMap<LocalDate, ArrayList<Attendance>> attendancesMap = new HashMap<>();

        //Parse the json file
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(attendanceFile)) {

            JSONObject mainJsonObject = (JSONObject) parser.parse(reader);

            JSONArray attendances = (JSONArray) mainJsonObject.get("attendances");

            //Iterate the main object to retrieve the date and attendance list
            Iterator mainIterator = attendances.iterator();
            while (mainIterator.hasNext()){
                Object mainSlide = mainIterator.next();
                JSONObject jsonObject = (JSONObject) mainSlide;

                String date = (String) jsonObject.get("date");

                JSONArray attendance = (JSONArray) jsonObject.get("attendance");

                //Iterate through the attendance list to retrieve the member
                //and attendance status
                ArrayList<Attendance> jsonAttendance = new ArrayList<>();
                Iterator iterator = attendance.iterator();
                while (iterator.hasNext()) {
                    Object slide = iterator.next();
                    JSONObject jsonObject2 = (JSONObject) slide;

                    String name = (String) jsonObject2.get("name");

                    String id = (String) jsonObject2.get("id");

                    boolean attended = (boolean) jsonObject2.get("attended");

                    //Create a new member
                    Member member = new Member(name, id);

                    //create an attendance
                    Attendance at = new Attendance(member, attended);
                    jsonAttendance.add(at);
                }

                attendancesMap.put(Utility.convertDate(date), jsonAttendance);
            }

        } catch (IOException e) {
            message.printFileNotFound();
        } catch (ParseException e) {
            message.printEmptyFile();
        }

        return attendancesMap;
    }

    /**
     * Write the list of attendances in the JSON file.
     * @param attendances a map with members and attendance date.
     * @return a json object.
     */
    public JSONObject convertAttendanceToJson(HashMap<LocalDate, ArrayList<Attendance>> attendances){
        //Main JSON object
        JSONObject mainObject = new JSONObject();

        //Create a JSON array
        JSONArray mainArray = new JSONArray();

        // iterating over a map
        for(HashMap.Entry<LocalDate, ArrayList<Attendance>> attendanceByDate : attendances.entrySet()){
            //JSON object inside the main an array
            JSONObject arrayElement = new JSONObject();

            //Add date and another array to arrayElement
            arrayElement.put("date", attendanceByDate.getKey().toString());

            //Create a JSON array to insert inside the arrayElement
            JSONArray array = new JSONArray();

            // iterating over the array list
            int index = 0;
            for(Attendance attendance : attendanceByDate.getValue()){
                JSONObject attendanceElement = new JSONObject();
                attendanceElement.put("name", attendance.getMember().getMemberName());
                attendanceElement.put("id", attendance.getMember().getMemberID());
                attendanceElement.put("attended", attendance.isHasAttended());
                array.add(attendanceElement);
                index++;
            }
            //insert in the main array
            arrayElement.put("attendance", array);
            mainArray.add(arrayElement);

        }

        //put everything inside the object
        mainObject.put("attendances", mainArray);

        return mainObject;
    }
}