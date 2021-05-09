package io;


import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class DirectBufferTest {

    public void testDirectBuffer() throws FileNotFoundException, IOException {
        File file = new File("D:\\迅雷下载\\Win10_20H2_v2_Chinese(Simplified)_x64.iso");
        int n = 0;
        //1. direct
        long start = System.currentTimeMillis();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024 * 10);
        try (FileChannel fileChannel = new FileInputStream(file).getChannel()) {
            while ((n = fileChannel.read(byteBuffer)) > 0) {
                byteBuffer.clear();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format("directBuffer use time %d", end - start));

        //2. normal buffer
        start = System.currentTimeMillis();
        byteBuffer = ByteBuffer.allocate(1024 * 1024 * 10);
        try (FileChannel fileChannel = new FileInputStream(file).getChannel()) {
            while ((n = fileChannel.read(byteBuffer)) > 0) {
                byteBuffer.clear();
            }
        }
        end = System.currentTimeMillis();
        System.out.println(String.format("normalBuffer use time %d", end - start));

    }


}
