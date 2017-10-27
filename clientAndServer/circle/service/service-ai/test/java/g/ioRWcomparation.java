package g;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
 
/*
 * 需求：把其他目录下的视频文件复制到指定目录中
 * 视频文件大小：49MB
 * 要求使用字节流的四种方式复制文件，比较四种方式的耗时：
 * 基本字节流一次读写一个字节
 * 基本字节流一次读写一个字节数组
 * 高效字节流一次读写一个字节
 * 高效字节流一次读写一个字节数组
 */

public class ioRWcomparation {

    public static void main(String[] args) throws Exception {
        byte[] from = "D:\\1oldBoy_4\\1216\\day12\\videos_day12\\第12天-12String类的转换功能.aviD:\\1oldBoy_4\\1216\\day12\\videos_day12\\第12天-12String类的转换功能.avi".getBytes();
//字节流：每次写入指定长度的字节数组的耗时
        long long2 = MultiWritingStream(from);
        System.out.println( "字节流：每次写入指定长度的字节数组的耗时(毫秒)"+long2);
//字节缓冲流：每次写入指定长度的字节数组的耗时
        long long4 = highEfficientMultiStream(from);
        System.out.println("字节缓冲流：每次写入指定长度的字节数组的耗时(毫秒)"+long4);
    }

    private static long highEfficientMultiStream(byte[] from) throws Exception {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("E:/File2.txt"));
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            bos.write(from);
            bos.flush();
            Thread.sleep(1000);
        }
        bos.close();
        return System.currentTimeMillis() - start;
    }


    private static long MultiWritingStream(byte[] from) throws IOException {
        FileOutputStream fos = new FileOutputStream("E:/File1.txt");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            fos.write(from);
            fos.flush();
        }
        fos.close();
        return System.currentTimeMillis() - start;
    }

}
