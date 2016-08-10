package com.uumai.crawer.util.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by rock on 4/14/15.
 */
public class SerializeUtil {
    public static String serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
//序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            //byte[] bytes = baos.toByteArray();
            //return bytes;
            String msg1 = baos.toString("ISO-8859-1");//指定字符集将字节流解码成字符串，否则在订阅时，转换会有问题。
            // msg1 = URLEncoder.encode(msg1, "UTF-8")
            return msg1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object unserialize(String msg) {
        ByteArrayInputStream bais = null;
        try {
//反序列化
            bais = new ByteArrayInputStream(msg.getBytes("ISO-8859-1"));
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
