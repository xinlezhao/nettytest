import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Created by xinle on 2/23/17.
 */
public class Xstream {


    XStream stream = new XStream(new DomDriver());

}
