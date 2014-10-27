package cn.com.lifeng.protocal;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by admaster on 14-10-20.
 */
public class ByteBufferStudy {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.println(byteBuffer.hasRemaining());
        for(int i=0;i<5;i++){
            byteBuffer.put((byte) i);
            System.out.println(byteBuffer.position());
        }
        System.out.println("----------");
        byteBuffer.flip();
        System.out.println("llll");
        System.out.println(byteBuffer.position());
        while (byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
            System.out.println(byteBuffer.position());
        }
        System.out.println("rem");
        System.out.println(byteBuffer.hasRemaining());
        byteBuffer.compact();
        byteBuffer.put((byte) 6);
        System.out.println(byteBuffer.position());

    }
}
