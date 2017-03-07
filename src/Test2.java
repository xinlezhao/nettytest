import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by xinle on 2/24/17.
 */
public class Test2 {


    public static void main(String[] args) {
        try {
            File file = new File("/Users/xinle/Desktop/test/lakala.zip");
            FileOutputStream fileInputStream = new FileOutputStream(file.getPath());


            fileInputStream.equals("");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
