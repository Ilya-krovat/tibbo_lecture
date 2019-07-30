
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
  private static Integer port = 5556;
  private Server server;
  @Test
  public void testServerConnection() throws Exception
  {
    //server.dropMessageCounter();
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress(ServerMessagesHelper.NAME_OF_HOST, server.getPort()));
    DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
    
    stream.writeUTF(ServerMessagesHelper.FIRST_MESSAGE);
    stream.flush();
    
    Socket socket1 = new Socket();
    socket1.connect(new InetSocketAddress(ServerMessagesHelper.NAME_OF_HOST, server.getPort()));
    stream = new DataOutputStream(socket1.getOutputStream());
    stream.writeUTF(ServerMessagesHelper.SECOND_MESSAGE);
    stream.flush();
    
    Socket socket2 = new Socket();
    socket2.connect(new InetSocketAddress(ServerMessagesHelper.NAME_OF_HOST, server.getPort()));
    
    stream = new DataOutputStream(socket2.getOutputStream());
    stream.writeUTF(ServerMessagesHelper.THIRD_MESSAGE);
    stream.flush();
    
    assertTrue(socket.isConnected());
    assertTrue(socket1.isConnected());
    assertTrue(socket2.isConnected());
    
    Thread.sleep(3000);
    assertEquals(3, server.getMessageCounter());


    socket.close();
    socket1.close();
    socket2.close();
  }
  
  @Test
  public void testCalculator() throws Exception
  {
    //server.dropMessageCounter();
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress(ServerMessagesHelper.NAME_OF_HOST, server.getPort()));
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
    assertEquals("8.0", result);
    
    outStream.writeUTF("(3 + 4) * 5");
    outStream.flush();
    
    result = inputStream.readUTF();
    assertEquals("35.0", result);
    
    outStream.writeUTF("pow(pow(pow(2 ,2), 2),2)");
    outStream.flush();
    result = inputStream.readUTF();
    
    assertEquals("256.0", result);
    
    assertEquals(4, server.getMessageCounter());
    
    socket.close();
  }
  
  @Test
  public void testSimpleExpression() throws Exception
  {
    //server.dropMessageCounter();
    String result;
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress(ServerMessagesHelper.NAME_OF_HOST, server.getPort()));
    DataOutputStream outStream= new DataOutputStream(socket.getOutputStream());
    DataInputStream inputStream = new DataInputStream(socket.getInputStream());

    assertTrue(socket.isConnected());

    outStream.writeUTF("sqrt(144)");
    outStream.flush();

    result = inputStream.readUTF();
    assertEquals("12.0", result);


    outStream.writeUTF("sqrt(144)*200");
    outStream.flush();

    result = inputStream.readUTF();
    assertEquals("2400.0", result);

    outStream.writeUTF("sqrt(4096)/8");
    outStream.flush();

    result = inputStream.readUTF();
    assertEquals("8.0", result);

    assertEquals(3, server.getMessageCounter());

    socket.close();
  }

  @Test
  public void testComplexExpression() throws Exception
  {
    //server.dropMessageCounter();
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress(ServerMessagesHelper.NAME_OF_HOST,server.getPort()));
    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

    outputStream.writeUTF("round(1000*sqrt(9000))/1000");
    outputStream.flush();

    String string = inputStream.readUTF();
    assertEquals("94.868",string);

    outputStream.writeUTF("round((round(1000*sqrt(9000))/1000)*55.386/exp(1))");
    outputStream.flush();

    string = inputStream.readUTF();
    assertEquals("1933.0",string);

    socket.close();
  }

  @Test
  public void testCoupleUsers() throws Exception
  {
    String result;
    Socket socket1 = new Socket();
    socket1.connect(new InetSocketAddress(ServerMessagesHelper.NAME_OF_HOST, server.getPort()));
    DataOutputStream outputStream = new DataOutputStream(socket1.getOutputStream());
    DataInputStream inputStream = new DataInputStream(socket1.getInputStream());

    assertTrue(socket1.isConnected());

    outputStream.writeUTF("250+1400-600");
    outputStream.flush();

    result = inputStream.readUTF();
    assertEquals("1050.0",result);

    outputStream.writeUTF("Okey Google, where is Moscow?");
    outputStream.flush();

    result=inputStream.readUTF();
    assertEquals(ServerMessagesHelper.MESSAGE_ERROR,result);

    Socket socket2 = new Socket();
    socket2.connect(new InetSocketAddress(ServerMessagesHelper.NAME_OF_HOST,server.getPort()));

    outputStream = new DataOutputStream(socket2.getOutputStream());
    inputStream = new DataInputStream(socket2.getInputStream());

    assertTrue(socket2.isConnected());

    outputStream.writeUTF("2+2+2*2");
    outputStream.flush();

    result = inputStream.readUTF();
    assertEquals("8.0",result);

    outputStream.writeUTF("1-1+1-1");
    outputStream.flush();

    result=inputStream.readUTF();
    assertEquals("0.0",result);

    assertEquals(4,server.getMessageCounter());

    outputStream.writeUTF("--help");
    outputStream.flush();

    result=inputStream.readUTF();
    System.out.println(result);

    socket1.close();
    socket2.close();
  }
  
  @Override
  protected void setUp() throws Exception
  {
    server = new Server();
    server.launch(port);
    port++;
  }

  @Override
  protected void tearDown() throws Exception
  {
    server.close();
  }
}
