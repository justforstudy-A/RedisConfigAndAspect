package com.springboot.demo.exer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class MyChannel {



    public static void main(String[] args) throws IOException {
        copyFile("C:\\Users\\周升升\\Desktop\\新建文本文档.txt", "C:\\Users\\周升升\\Desktop\\test.txt");
    }

    public synchronized static void copyFile(String srcPath, String desPath) throws IOException {
        FileInputStream fi = new FileInputStream(srcPath);
        FileOutputStream fo = new FileOutputStream(desPath);
        FileChannel inChannel = fi.getChannel();
        FileChannel ouChannel = fo.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        while (true){
            int eof = 0;
            eof = inChannel.read(buffer);

            if(eof == -1) break;
            buffer.flip();
            ouChannel.write(buffer);
            buffer.clear();

        }
        inChannel.close();
        ouChannel.close();
        fi.close();
        fo.close();
    }
}
