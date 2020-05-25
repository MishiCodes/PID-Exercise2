package attendance;

/**
 * This class represents an Attendance object.
 */
public class Attendance {
    private Member member;
    private boolean hasAttended;

    /**
     * Constructor of the Attendance class.
     * @param member represents a student
     * @param hasAttended boolean
     */
    public Attendance(Member member, boolean hasAttended) {
        this.member = member;
        this.hasAttended = hasAttended;
    }

    /**
     * Get the member
     * @return String member
     */
    public Member getMember() {
        return member;
    }

    /**
     * Get attendance
     * @return boolean hasAttended
     */
    public boolean isHasAttended() {
        return hasAttended;
    }

    /**
     * Get attendance
     * @return string hasAttended
     */
    public String hasAttendedToString() {

        return (hasAttended) ? "Attended" : "Not Attended";
    }

    /**
     * Concatenate all the fields.
     * @return
     */
    @Override
    public String toString() {
        return "{" +
                "ID = " + member.getMemberID() +
                ", Name = " + member.getMemberName() +
                ", Status = " + hasAttendedToString() +
                '}';
    }
}