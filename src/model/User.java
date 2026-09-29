package  model;

public class User {
    private String userId;    
    private String name;
    private String email;
    private String phoneNumber;
    private String password;

    //Constructor
    public User(String userId, String name, String email, String phoneNumber, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
    //---------------
    
    
    
    //GET SET GO
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    //-----------------
    
    
    
    public boolean checkPassword(String attempt) {
        return this.password != null && this.password.equals(attempt);
    }

    
    //FILE IO
    public static User fromFileString(String line) {
        String[] p = line.split("_", -1);
        return new User(p[0], p[1], p[2], p[3], p[4]);
    }

 
    public String toFileString() {
        return userId + "_" + name + "_" + email + "_" + phoneNumber + "_" + password;
    }
    //--------------------------
    
    
    
    
    @Override
    public String toString() {
        return "Name: "+name + " User Id: " + userId ;
    }
}
