package xstream;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyFactory;
import java.security.Provider;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by xinle on 2/28/17.
 */
public class Test {


    public static String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwrIT2EwJoDHxL3+PTa9AEW7osBqGURV9eaf7vrY9ugqMpwAtUo6NttK57VGPddav5SehzbO9Tb84v+eKSjztNqo5unL8PLIO02pCUKOTR7P0/8hnQ8bO7yjJ1GFOaZDC8+YFuQOzghl5VHWtxj8VjhYmbxvMq8rzWsD/++uerjcXEORmFP5hbe1ViGLIUSO0bKmDjfPBFr6AoKPGUL0yJZp7p3oJml2h/dFSAyML5ydN4qoFJz9eBB/zFUd400Lc8QkSnPrOSI971uXw7LqzVH1VrSj2n+F6h8qBw/ZkQY/t0y5spBEkw/SGdf7/uMI305yKQGvZrFgNd/AO++hxIwIDAQAB";
    public static void main(String[] args) {

        byte[] test =  java.util.Base64.getDecoder().decode(testString);

        System.out.print(decryptByPublicKey(test));
//        ListSupportedAlgorithms();

    }


    public static String testString = "eBKiWQB+4UYZrFGTqLeS1XewHZEU/Hc4etgkDfiteciI6z3FbqUmCpGZDDLrkm5+QQe7r0R1KfugCxiWBIxtJCh14+IGwR1ZR1V1hZE1fdpX2j0VNoDTMCutHGp/nrysdiKiK5G+o89IjOw2acK8T50vF9sbPtWejm7o0/kL0yYyR0BEkJPgWXTTLBRkPctT+l9xD66LeHlUaiyZtF05u/QVLiJZDmgYHhOE6Uo7u9mZAXdiFDqmH02bVuh5wO5JZrAGbTSskGvYQvjnmSbTykQ4d6Mz7jaHG1Pe3kZHnqllmCu7cVzp8v5szff9RrqikL6VOri5Sq2auM57WYQUBg==";

    public static final String KEY_ALGORITHM = "RSA";
    private static final int MAX_DECRYPT_BLOCK = 256;


    public static String decryptByPublicKey(byte[] encryptedData) {
        try {

            InputStream inputStream = new FileInputStream("/Users/xinle/Desktop/pub.key");
            byte[] buftter = new byte[inputStream.available()];
            inputStream.read(buftter);
            byte[] bytes  =java.util.Base64.getDecoder().decode(buftter);
            byte[] keyBytes = Base64.decode(pubKey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, publicK);
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
//
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData);


//            return Base64.encode(decryptedData,Base64.DEFAULT);
        } catch (Exception e) {

            e.getMessage();
            e.printStackTrace();
        }
        return null;

    }





    public static void ListSupportedAlgorithms() {
        String result = "";

        // get all the providers
        Provider[] providers = Security.getProviders();

        for (int p = 0; p < providers.length; p++) {
            // get all service types for a specific provider
            Set<Object> ks = providers[p].keySet();
            Set<String> servicetypes = new TreeSet<String>();
            for (Iterator<Object> it = ks.iterator(); it.hasNext(); ) {
                String k = it.next().toString();
                k = k.split(" ")[0];
                if (k.startsWith("Alg.Alias."))
                    k = k.substring(10);

                servicetypes.add(k.substring(0, k.indexOf('.')));
            }

            // get all algorithms for a specific service type
            int s = 1;
            for (Iterator<String> its = servicetypes.iterator(); its.hasNext(); ) {
                String stype = its.next();
                Set<String> algorithms = new TreeSet<String>();
                for (Iterator<Object> it = ks.iterator(); it.hasNext(); ) {
                    String k = it.next().toString();
                    k = k.split(" ")[0];
                    if (k.startsWith(stype + "."))
                        algorithms.add(k.substring(stype.length() + 1));
                    else if (k.startsWith("Alg.Alias." + stype + "."))
                        algorithms.add(k.substring(stype.length() + 11));
                }

                int a = 1;
                for (Iterator<String> ita = algorithms.iterator(); ita.hasNext(); ) {
                    result += ("[P#" + (p + 1) + ":" + providers[p].getName() + "]" +
                            "[S#" + s + ":" + stype + "]" +
                            "[A#" + a + ":" + ita.next() + "]\n");
                    a++;
                }

                s++;
            }
        }

        System.out.println(result);
//        Log.e("Gank", "Res: " + result);
    }



}
