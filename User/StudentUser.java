package user;

import access.BookingAccess;
import access.ReviewAccess;
import booking.BookingHandler;
import review.ReviewHandler;

public class StudentUser extends User implements BookingAccess, ReviewAccess {
    private String university;
    private String studentNumber;

    public StudentUser(
        String username,
        String email,
        String phone,
        String passwordHash,
        String university,
        String studentNumber
    ) throws Exception {
        super(username, email, phone, passwordHash);
        setUniversity(university);
        setStudentNumber(studentNumber);
    }

    public String getUniversity() { return university; }
    public void setUniversity(String university) {
        if (university != null && university.length() > 128) {
            throw new IllegalArgumentException(
                "University name must be up to 128 characters long."
            );
        }
        this.university = university;
    }

    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) {
        if (studentNumber == null || !studentNumber.matches("^\\d{1,32}$")) {
            throw new IllegalArgumentException(
                "Student number must be up to 32 digits long."
            );
        }
        this.studentNumber = studentNumber;
    }

    @Override
    public BookingHandler getBookingHandler() { return BookingHandler; }

    @Override
    public ReviewHandler getReviewHandler() { return ReviewHandler; }
}