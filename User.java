public class User {
    private Integer userID;
    private String userType; // student or homeowner
    private String name; // legal name, could probably expand this
    private String email;
    private String phone;
    private String password; // this doesnt feel right but shhhhh thats a future jess issue!

    public Integer getUserID() { return userID; }
    public void setUserID(Integer userID) { this.userID = userID; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    // this STILL feels wrong but shhhhhhhhhhhhhhhhh its ok present jess future jess will fix it all for you <3
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}