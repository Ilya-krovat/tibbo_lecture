package com.tibbo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class Server
{
  private static final Server INSTANCE = new Server();
  private ServerSocket serverSocket;
  private int messageCounter = 0;
  
  public static void main(String[] args) throws Exception
  {
    INSTANCE.launch(args);
  }
  
  public void launch(String[] args) throws Exception
  {
    serverSocket = new ServerSocket();
    serverSocket.bind(new InetSocketAddress(5555));
    serverSocket.setSoTimeout(5000);
  }
  
  public void close() throws IOException
  {
    serverSocket.close();
  }
  
  public int getMessageCounter()
  {
    return messageCounter;
  }
}
