
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
    server.drop();
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
    
    Thread.sleep(3000);
    assertEquals(3, server.getMessageCounter());


    socket.close();
    socket1.close();
    socket2.close();
  }
  
  @Test
  public void testCalculator() throws Exception
  {
    server.drop();
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress("localhost", 5555));
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
  
  //тест 1
  //создать еще один тест, в котором будем вычислять
  //корень квадртный из 144
  //корень квадртный из 144 умножить на 200
  // корень квадрный из 4096 разделить на 8
  //
  
  @Test
  public void testMyTest1() throws Exception
  {
    server.drop();
    String result;
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress("localhost", 5555));
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
  //тест 2
  //посчитать корень квадртный из 9000(если не найдете корень, умножить PI на 200)
  //окргулить до 3 знака
  //результат умножить на 55.386
  //разделть на число 'e'
  //окргулить до целый часть(sign)

  @Test
  public void testMyTest2() throws Exception
  {
    server.drop();
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress("localhost",5555));
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

  //тест 3
  //нужно создать несколько сокетов для клиента
  //вычислить в кажом по два выржания

  @Test
  public void testMyTest3() throws Exception
  {
    server.drop();
    String result;
    Socket socket1 = new Socket();
    socket1.connect(new InetSocketAddress("localhost", 5555));
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
    socket2.connect(new InetSocketAddress("localhost",5555));

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

    socket1.close();
    socket2.close();
  }

  
  @Override
  protected void setUp() throws Exception
  {
    server = new Server();
    server.launch(new int[]{5555,5556,5557,5558,5559});
  }
  
  @Override
  protected void tearDown() throws Exception
  {
    server.close();
  }
}
