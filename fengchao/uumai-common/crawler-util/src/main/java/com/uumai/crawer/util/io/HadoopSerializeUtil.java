package com.uumai.crawer.util.io;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.StringUtils;

import java.io.*;

/**
 * Created by rock on 1/1/16.
 */
public class HadoopSerializeUtil {
    /**
     * 将Writable对象转换成字节流
     */
    public static byte[] serialize(Writable writable) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(out);
        writable.write(dataOut);
        dataOut.close();
        return out.toByteArray();
    }

    /**
     * 将字节流转换成Writable对象
     */
    public static void deserialize(Writable writable, byte[] bytes)
            throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        DataInputStream dataIn = new DataInputStream(in);
        writable.readFields(dataIn);
        dataIn.close();
    }


    /**
     * 打印字节流
     */
    public static void printBytesHex(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(StringUtils.byteToHexString(bytes, i, i + 1)
                    .toUpperCase());
            if (i % 16 == 15)
                System.out.print('\n');
            else if (i % 1 == 0)
                System.out.print(' ');
        }
    }

    public static void main(String[] args) throws IOException {
        IntWritable intWritable = new IntWritable(99999);
        // 序列化
        byte[] bytes = serialize(intWritable);
        printBytesHex(bytes);

        IntWritable intWritable2 = new IntWritable();
        // 反序列化
        deserialize(intWritable2, bytes);
        System.out.println(intWritable2);
    }
}
