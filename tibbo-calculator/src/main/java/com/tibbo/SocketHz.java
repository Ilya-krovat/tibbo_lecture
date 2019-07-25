package com.tibbo;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.math.Pow;

import java.io.*;
import java.net.Socket;

public class SocketHz extends Thread {
    private Socket socket;
    private Server INSTANCE;
    public SocketHz(Socket socket, Server INSTANCE)
    {
        this.INSTANCE = INSTANCE;
        this.socket = socket;//примитивные типы не копируются, толку передвать его сюда нету
    }// у нас создается только один поток

    private String getResult(String string) {
        Evaluator evaluator = new Evaluator();
        try {
            return evaluator.evaluate(string);
        } catch (EvaluationException ee) {
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
                if(string.equals("--help")) {
                    outputStream.writeUTF("Привет, я - калькулятор\nЯ умею:\n   -считать математические выражения\nP.s.\nДля извлечения корня вводить sqrt(выражение)\nДля возвеения A в степень B водить pow(A,B)");
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
