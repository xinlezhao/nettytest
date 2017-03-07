import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.function.Function;
import java.util.zip.*;

/**
 * Created by xinle on 2/13/17.
 */
public class Test {

    static final int BUFFER = 2048;

    public static void main(String[] args) throws Exception {



        long startTime=System.currentTimeMillis();
        try {

            File file = new File("/Users/xinle/Desktop/test/test.zip");
            ZipInputStream Zin=new ZipInputStream(new FileInputStream(
                    file));//输入源zip路径


            BufferedInputStream Bin=new BufferedInputStream(Zin);
            String Parent="/Users/xinle/Desktop/test/"; //输出路径（文件夹目录）
            File Fout=null;
            ZipEntry entry;
            try {
                while((entry = Zin.getNextEntry())!=null ){
                    if (entry.isDirectory())continue;
                    Fout=new File(Parent,entry.getName());
                    if(!Fout.exists()){
                        (new File(Fout.getParent())).mkdirs();
                    }
                    FileOutputStream out=new FileOutputStream(Fout);
                    BufferedOutputStream Bout=new BufferedOutputStream(out);
                    int b;
                    while((b=Bin.read())!=-1){
                        Bout.write(b);
                    }
                    Bout.close();
                    out.close();
                    System.out.println(Fout+"解压成功");
                }
                Bin.close();
                Zin.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long endTime=System.currentTimeMillis();
        System.out.println("耗费时间： "+(endTime-startTime)+" ms");

//        unzip("/Users/xinle/Desktop/test/lakala.zip","/Users/xinle/Desktop/test",true);

    }





    /**
     * 解压缩zip包
     * @param zipFilePath zip文件的全路径
     * @param unzipFilePath 解压后的文件保存的路径
     * @param includeZipFileName 解压后的文件保存的路径是否包含压缩文件的文件名。true-包含；false-不包含
     */
    @SuppressWarnings("unchecked")
    public static void unzip(String zipFilePath, String unzipFilePath, boolean includeZipFileName) throws Exception
    {
        if ("".equals(zipFilePath) || "".equals(unzipFilePath))
        {
            throw new Exception("invalic filePath");
        }
        File zipFile = new File(zipFilePath);
        //如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径
        if (includeZipFileName)
        {
            String fileName = zipFile.getName();
            if (!"".equals(fileName))
            {
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
            }
            unzipFilePath = unzipFilePath + File.separator + fileName;
        }
        //创建解压缩文件保存的路径
        File unzipFileDir = new File(unzipFilePath);
        if (!unzipFileDir.exists() || !unzipFileDir.isDirectory())
        {
            unzipFileDir.mkdirs();
        }

        //开始解压
        ZipEntry entry = null;
        String entryFilePath = null, entryDirPath = null;
        File entryFile = null, entryDir = null;
        int index = 0, count = 0, bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        ZipFile zip = new ZipFile(zipFile);
        Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>)zip.entries();
        //循环对压缩包里的每一个文件进行解压
        while(entries.hasMoreElements())
        {
            entry = entries.nextElement();
            //构建压缩包中一个文件解压后保存的文件全路径
            entryFilePath = unzipFilePath + File.separator + entry.getName();
            //构建解压后保存的文件夹路径
            index = entryFilePath.lastIndexOf(File.separator);
            if (index != -1)
            {
                entryDirPath = entryFilePath.substring(0, index);
            }
            else
            {
                entryDirPath = "";
            }
            entryDir = new File(entryDirPath);
            //如果文件夹路径不存在，则创建文件夹
            if (!entryDir.exists() || !entryDir.isDirectory())
            {
                entryDir.mkdirs();
            }

            //创建解压文件
            entryFile = new File(entryFilePath);
            if (entryFile.exists())
            {
                //检测文件是否允许删除，如果不允许删除，将会抛出SecurityException
//                SecurityManager securityManager = new SecurityManager();
//                securityManager.checkDelete(entryFilePath);
//                //删除已存在的目标文件
//                entryFile.delete();
            }

            //写入文件
            bos = new BufferedOutputStream(new FileOutputStream(entryFile));
            bis = new BufferedInputStream(zip.getInputStream(entry));
            while ((count = bis.read(buffer, 0, bufferSize)) != -1)
            {
                bos.write(buffer, 0, count);
            }
            bos.flush();
            bos.close();
        }
    }


    private void test(int a, Function<Integer, Integer>  integerFunction){

    }








}
