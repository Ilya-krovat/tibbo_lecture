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
  private static final Server INSTANCE = new Server();
  private ServerSocket serverSocket;
  private static int messageCounter = 0;
  private List<Thread> pool = new ArrayList<>();
  private Thread thread;

  public static void main(String[] args) throws Exception
  {
    INSTANCE.launch(args);
  }

  static void increase()
  {
    messageCounter++;
  }
  
  public void launch(String[] args) throws Exception
  {
    serverSocket = new ServerSocket();
    serverSocket.bind(new InetSocketAddress(5555));
    thread = new Thread(){
      public void run()
      {
        //у тебя этот поток, который обрабатывает  клиентские подключение не зациклен, он один ращз отработает и все
        try
        {
          while(!isInterrupted()) {
            Socket socket;
            try { socket = serverSocket.accept(); }
            catch (SocketException e) { break; }
            SocketHz test = new SocketHz(socket, INSTANCE);
            test.start();
            pool.add(test);
          }
        }
        catch (IOException e) {
          e.printStackTrace();
        }
      }
    };
    thread.start();
  }
  
  public void close() throws IOException
  {
    thread.interrupt();
    serverSocket.close();
    for(Thread i : pool)
        i.interrupt();
  }
  
  public int getMessageCounter()
  {
    return messageCounter;
  }
}