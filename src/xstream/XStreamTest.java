package xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinle on 2/23/17.
 */
public class XStreamTest {

    public static void main(String[] args) {
        //javabean 转 xml
        List<User> users = new ArrayList<User>();
        users.add(new User("123", "爱边程", "23"));
        users.add(new User("456", "刘大拿", "24"));
        users.add(new User("456", "刘大拿", "24"));
        users.add(new User("456", "刘大拿", "24"));
        users.add(new User("456", "刘大拿", "24"));
        BaseBean base = new BaseBean();
        base.setUserList(users);
        XStream xs = new XStream();
        xs.useAttributeFor(User.class, "name");
        xs.alias("root", BaseBean.class);
        xs.alias("user", User.class);
        xs.aliasField("list", BaseBean.class, "userList");
        xs.aliasField("hello", User.class, "listBean");
        String xml=xs.toXML(base);
        System.out.println("javabean转成xml为:\n"+xml);

        //xml转javabean
        XStream xs1 = new XStream(new DomDriver());
        xs1.alias("root", BaseBean.class);
        xs1.alias("user", User.class);
        xs1.useAttributeFor(User.class, "name");
        xs1.aliasField("list", BaseBean.class, "userList");
        xs1.aliasField("hello", User.class, "listBean");
        base=(BaseBean)xs1.fromXML(xml);
        users=base.getUserList();
        System.out.println("xml转成javabean为:");
        for(User u:users){
            System.out.println("id="+u.getId()+",name="+u.getName()+",age="+u.getAge() +"type=" + base.getType()+ u.listBean.get(0).name);
        }
    }

}
