package User;

public class StudentUser extends User {
    @Override
    public String getUserType() { return "student"; }

    private String university;
    private String studentNumber;

    public String getUniversity() { return university; }
    public void setUniversity(String university) {
        if (!"student".equals(this.getUserType())) {
            throw new IllegalStateException("Only student can input a university.");
        }
        if (university != null && university.length() > 128) {
            throw new IllegalArgumentException(
                "University name must be up to 128 characters long."
            );
        }
        this.university = university;
    }

    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) {
        if (!"student".equals(this.getUserType())) {
            throw new IllegalStateException(
                "Only students can have a student number."
            );
        }

        if (studentNumber == null || !studentNumber.matches("^\\d{1,32}$")) {
            throw new IllegalArgumentException(
                "Student number must be up to 32 digits long."
            );
        }

        this.studentNumber = studentNumber;
    }
}
