package cn.com.lifeng.protocal;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * Created by admaster on 14-10-20.
 */
public interface TcpProtocal {
    public void handleAccept(SelectionKey selectionKey) throws IOException;
    public void handleRead(SelectionKey selectionKey) throws IOException;
    public void handleWrite(SelectionKey selectionKey) throws IOException;
}
