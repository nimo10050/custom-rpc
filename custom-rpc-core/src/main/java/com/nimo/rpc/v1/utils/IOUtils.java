package com.nimo.rpc.v1.utils;

import java.io.*;

public class IOUtils {

    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeObject(String path, Object object) {
        FileOutputStream fos= null;
        ObjectOutputStream oos= null;
        try {
            fos = new FileOutputStream(new File(Base64Utils.encode(path,"utf-8")));
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeQuietly(oos);
            closeQuietly(fos);
        }

    }

    public static Object readObject(String file){
        File serviceFile = new File(Base64Utils.decode(file, "utf-8"));
        if (!serviceFile.exists())
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(serviceFile);
            ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            return o;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(ois);
            closeQuietly(fis);
        }
        return null;
    }

}
