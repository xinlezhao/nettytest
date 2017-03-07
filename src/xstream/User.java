package xstream;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.LineNumberReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xinle on 2/23/17.
 */
public class User {




    public TestBean bean = new TestBean();
    public List<TestBean> listBean = new ArrayList<>();
    private String id;
    @XStreamAsAttribute
    private String name;
    private String age;
    public User(){

    }
    public User(String id,String name,String age){
        this.id=id;
        this.name=name;
        this.age=age;
                for(int i = 0; i < 10; i++){
                    TestBean bean = new TestBean();
                    listBean.add(bean);
                }



    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }



}
