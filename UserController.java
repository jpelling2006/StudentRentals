public class UserController {
    private User model;
    private UserView view;

    public UserController(User model, UserView view) {
        this.model = model;
        this.view = view;
    }

    public Integer getUserID() { return model.getID(); }
    public void setUserID(Integer ID) { model.setID(ID); }

    public String getUserType() { return model.getType(); }
    public void setUserType(String type) { model.setType(type); }

    public String getUserName() { return model.getName(); }
    public void setUserName(String name) { model.setName(name); }

    public String getUserEmail() { return model.getEmail(); }
    public void setUserEmail(String email) { model.setEmail(email); }

    public String getUserPhone() { return model.getPhone(); }
    public void setUserPhone(String phone) { model.setPhone(phone); }

    public String getUserPassword() { return model.getPassword(); }
    public void setUserPassword(String password) { model.setPassword(password); } // PLEASE DO SMTHN WITH THIS I AM BEGGING YOU RAHHH
}
