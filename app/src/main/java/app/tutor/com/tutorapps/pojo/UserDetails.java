package app.tutor.com.tutorapps.pojo;

/**
 * Created by Saikat's Mac on 06/05/16.
 */
public class UserDetails {

    public UserDetails() {
    }

    private String userName = "", userID = "", userEmail = "";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
