package cn.com.lifeng.protocal;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by admaster on 14-10-20.
 */
public class EchoSelectorProtocal implements TcpProtocal {
    private int capcity;
    public EchoSelectorProtocal(int bufferSize){
        this.capcity = bufferSize;
    }
    @Override
    public void handleAccept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        System.out.println(socketChannel.toString());
        socketChannel.configureBlocking(false);
        socketChannel.register(selectionKey.selector(),SelectionKey.OP_READ, ByteBuffer.allocate(capcity));

    }

    @Override
    public void handleRead(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer bf = (ByteBuffer) selectionKey.attachment();

        long readLength = socketChannel.read(bf);
        if(readLength==-1){
            socketChannel.close();
        }else if(readLength>0){
            selectionKey.interestOps(SelectionKey.OP_READ| SelectionKey.OP_WRITE);
        }
    }

    @Override
    public void handleWrite(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer bf = (ByteBuffer) selectionKey.attachment();
        bf.flip();
        socketChannel.write(bf);
        if(!bf.hasRemaining()){
            selectionKey.interestOps(SelectionKey.OP_READ);
        }
        bf.compact();
    }
}
