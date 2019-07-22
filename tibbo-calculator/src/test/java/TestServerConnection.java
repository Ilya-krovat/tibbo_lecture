
import com.tibbo.Server;
import com.tibbo.ServerMessagesHelper;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TestServerConnection extends TestCase
{
  private Server server;
  @Test
  public void testServerConnection() throws Exception
  {
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress("localhost", 5555));
    DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
    
    stream.writeUTF(ServerMessagesHelper.FIRST_MESSAGE);
    stream.flush();
    
    Socket socket1 = new Socket();
    socket1.connect(new InetSocketAddress("localhost", 5555));
    stream = new DataOutputStream(socket1.getOutputStream());
    stream.writeUTF(ServerMessagesHelper.SECOND_MESSAGE);
    stream.flush();
    
    Socket socket2 = new Socket();
    socket2.connect(new InetSocketAddress("localhost", 5555));
    
    stream = new DataOutputStream(socket2.getOutputStream());
    stream.writeUTF(ServerMessagesHelper.THIRD_MESSAGE);
    stream.flush();
    
    assertTrue(socket.isConnected());
    assertTrue(socket1.isConnected());
    assertTrue(socket2.isConnected());
    
    Thread.sleep(10000);
    assertEquals(3, server.getMessageCounter());
    
    socket.close();
    socket1.close();
    socket2.close();
  }
  
  @Override
  protected void setUp() throws Exception
  {
    server = new Server();
    server.launch(null);
  }
  
  @Override
  protected void tearDown() throws Exception
  {
    server.close();
  }
}
