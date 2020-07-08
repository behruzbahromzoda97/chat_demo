package entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Behruz Bahromzoda
 * at 11:00
 */
@Entity
@Table(name = "user_tbl")
@NamedQuery(name = User.GET_ALL_USERS, query = "SELECT u from  User  u")
@NamedQuery(name = User.GET_USER_BY_USERNAME, query = "select  u from User u where u.username=:username")
@NamedQuery(name = User.GET_USER_BY_ID, query = "select  u from User u  where u.id=:userId")
public class User extends AbstractEntity implements Serializable {
    public static final String GET_ALL_USERS = "GET_ALL_USERS";
    public static final String GET_USER_BY_USERNAME = "GET_USER_BY_USERNAME";
    public static final String GET_USER_BY_ID = "GET_USER_BY_ID";
    @Column(name = "fullname", nullable = false)
    private String fullname;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;


    //-----------------------------------Getter and Setter------------------------------------
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
