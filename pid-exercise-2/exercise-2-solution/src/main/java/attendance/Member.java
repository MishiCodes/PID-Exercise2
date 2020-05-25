package attendance;

/**
 * A class representing a Member object.
 */
public class Member {
    private String memberName;
    private String memberID;

    /**
     * Constructor of Member class.
     * @param memberName name of the member.
     * @param memberID ID of the member.
     */
    public Member(String memberName, String memberID) {
        this.memberName = memberName;
        this.memberID = memberID;
    }

    /**
     * Get the member name
     * @return String member name
     */
    public String getMemberName() {
        return memberName;
    }

    /**
     * Set member name
     * @param memberName
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /**
     * Get the ID of the member
     * @return
     */
    public String getMemberID() {
        return memberID;
    }

    /**
     * Set the ID of the member
     * @param memberID
     */
    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }


    @Override
    public String toString() {
        return "memberName = '" + memberName + '\'' +
                ", memberID = '" + memberID + '\'';
    }
}