package attendance;

import org.junit.Test;

import static org.junit.Assert.*;

public class AttendanceTest {
    Member member = new Member("Dawood", "ws23r");

    @Test
    public void isPresentTrue() {
        Attendance attendance = new Attendance(member, true);

        assertEquals(true, attendance.isHasAttended());
    }

    @Test
    public void isPresentFalse() {
        Attendance attendance = new Attendance(member, true);

        assertNotEquals(false, attendance.isHasAttended());
    }

    @Test
    public void presentToStringTrue() {
        Attendance attendance = new Attendance(member, true);

        assertEquals("Attended", attendance.hasAttendedToString());
    }

    @Test
    public void notPresentToStringTrue() {
        Attendance attendance = new Attendance(member, false);

        assertEquals("Not Attended", attendance.hasAttendedToString());
    }

    @Test
    public void presentToStringFalse() {
        Attendance attendance = new Attendance(member, true);

        assertNotEquals("Not Attended", attendance.hasAttendedToString());
    }
}