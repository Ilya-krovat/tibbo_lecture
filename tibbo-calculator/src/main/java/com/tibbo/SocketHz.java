package com.tibbo;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

import java.io.*;
import java.net.Socket;

public class SocketHz extends Thread {
    private Socket socket;
    private Server INSTANCE;
    private Evaluator evaluator = new Evaluator();

    public SocketHz(Socket socket, Server INSTANCE)
    {
        this.INSTANCE = INSTANCE;
        this.socket = socket;//примитивные типы не копируются, толку передвать его сюда нету
    }// у нас создается только один поток

    private String getResult(String string) {
        try {
            return evaluator.evaluate(string);
        } catch (EvaluationException ee) {
            System.out.println("Проблемы с выражением "+string);
            return ServerMessagesHelper.MESSAGE_ERROR;
        }
    }

    @Override
    public void run()
    {
        try
        {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            while(!socket.isClosed()&&!isInterrupted()){
                String string=null;
                try {
                    string = inputStream.readUTF();
                }catch (EOFException e) {
                    continue;
                }
                if(string.equals(ServerMessagesHelper.COMMAND_HELP)) {
                    outputStream.writeUTF(ServerMessagesHelper.HELP_MESS);
                    outputStream.flush();
                }
                else
                {
                    String result = getResult(string);
                    if (result != null)
                        INSTANCE.increase();
                    System.out.println(string);
                    System.out.println(result);
                    outputStream.writeUTF(result);
                    outputStream.flush();
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
