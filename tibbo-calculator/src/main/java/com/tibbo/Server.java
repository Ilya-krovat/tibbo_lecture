package com.tibbo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server
{
  private ServerSocket serverSocket;
  private int messageCounter = 0;
  private List<Thread> threadPool = new ArrayList<>();
  private Thread mainThread;
  private int port = 5555;

  public static void main(String[] args) throws Exception
  {
    new Server().launch(null);
  }

  public int getPort()
  {
    return port;
  }

  void increase()
  {
    messageCounter++;
  }

  public void launch(String port) throws Exception
  {
    try {
      this.port = Integer.parseInt(port);
    }catch (NullPointerException e) {
      System.out.println("port is default (NullPointerException)");
    }catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("port is default1 (ArrayIndexOutOfBoundsException)");
    }catch (NumberFormatException e) {
      System.out.println("port is default (NumberFormatException)");
    }

    serverSocket = new ServerSocket();
    serverSocket.bind(new InetSocketAddress(getPort()));
    mainThread = new Thread(){
      public void run()
      {
        try
        {
          while(!isInterrupted()) {
            Socket socket;
            try { socket = serverSocket.accept(); }
            catch (SocketException e) { break; }
            MessageTread newSocket = new MessageTread(socket,Server.this);
            newSocket.start();
            threadPool.add(newSocket);
          }
        }
        catch (IOException e) {
          e.printStackTrace();
        }
      }
    };
    mainThread.start();
  }
  
  public void close() throws IOException
  {
    mainThread.interrupt();
    serverSocket.close();
    for(Thread i : threadPool)
        i.interrupt();
  }
  
  public int getMessageCounter()
  {
    return messageCounter;
  }
}