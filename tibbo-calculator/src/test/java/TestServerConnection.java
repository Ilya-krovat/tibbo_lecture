
import com.tibbo.Server;
import com.tibbo.ServerMessagesHelper;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.DataInputStream;
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
  
  @Test
  public void testCalculator() throws Exception
  {
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress("locahost", 5555));
    DataOutputStream outStream= new DataOutputStream(socket.getOutputStream());
    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
    
    assertTrue(socket.isConnected());
    
    outStream.writeUTF(ServerMessagesHelper.FIRST_MESSAGE);
    outStream.flush();
    
    String result = inputStream.readUTF();
    assertEquals(ServerMessagesHelper.MESSAGE_ERROR, result);
    
    outStream.writeUTF("2 * (2 + 2)");
    outStream.flush();
    
    result = inputStream.readUTF();
    assertEquals("8", result);
    
    outStream.writeUTF("(3 + 4) * 5");
    outStream.flush();
    
    result = inputStream.readUTF();
    assertEquals("35", result);
    
    outStream.writeUTF("2 ^ 2 ^ 2 ^ 2");
    outStream.flush();
    result = inputStream.readUTF();
    
    assertEquals("256", result);
    
    assertEquals(3, server.getMessageCounter());
    
    socket.close();
  }
  
  //тест 1
  //создать еще один тест, в котором будем вычислять
  //корень квадртный из 144
  //корень квадртный из 144 умножить на 200
  // корень квадрный из 4096 разделить на 8
  //
  
  
  //тест 2
  //посчитать корень квадртный из 9000(если не найдете корень, умножить PI на 200)
  //окргулить до 3 знака
  //результат умножить на 55.386
  //разделть на число 'e'
  //окргулить до целый часть(sign)
  
  //тест 3
  //нужно создать несколько сокетов для клиента
  //вычислить в кажом по два выржания
  //
  
  
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
