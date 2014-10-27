package cn.com.lifeng;

import cn.com.lifeng.protocal.EchoSelectorProtocal;
import cn.com.lifeng.protocal.TcpProtocal;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App

{
    final static int CAPACITY = 256;

   static public int codeIntBigEndian(byte[] dst,long val, int offset, int size){
        for(int i=0;i< size; i++){
            dst[offset++] = (byte)(val>>((size-1-i)* Byte.SIZE));
        }

        return offset;
    }
    static public long decodeIntBigEndian(byte[]dst, int offset,int size){
        long rtn=0;
        for(int i=0;i<size;i++){
            rtn = rtn << Byte.SIZE | (long)(dst[offset+i]&0xFF);
        }
        return rtn;
    }
    public static void main( String[] args ){
        int port = 3333;

        Executor executor = Executors.newCachedThreadPool();
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            TcpProtocal tcpProtocal = new EchoSelectorProtocal(CAPACITY);
            while (true){
                selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()){
                    SelectionKey selectionKey = keyIterator.next();
                    if(selectionKey.isAcceptable()){
                        tcpProtocal.handleAccept(selectionKey);
                    }else if(selectionKey.isReadable()){
                        tcpProtocal.handleRead(selectionKey);
                    }else if(selectionKey.isWritable()){
                        tcpProtocal.handleWrite(selectionKey);
                    }
                    keyIterator.remove();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
