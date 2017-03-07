package xstream;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinle on 2/23/17.
 */
public class BaseBean {

    private List<User> userList;

    private String type= "test" ;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    private String property ;

    public BaseBean(){
        userList = new ArrayList<User>();
    }
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    public void addUser(User user) {
        userList.add(user);
    }
}
