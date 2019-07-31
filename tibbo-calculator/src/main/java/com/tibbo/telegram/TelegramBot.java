package com.tibbo.telegram;

import com.tibbo.telegram.MyDefaultBotOptions;
import com.tibbo.telegram.ServerMessagesHelper;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


public class TelegramBot extends TelegramLongPollingBot
{
    private InetSocketAddress inetSocketAddress;

    private Socket socket;

    private DataOutputStream outStream;
    private DataInputStream inputStream;

    public TelegramBot()
    {
        super(new MyDefaultBotOptions());
        socket = new Socket();
    }

    @Override
    public String getBotToken() {
        return  ServerMessagesHelper.BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return ServerMessagesHelper.BOT_USERNAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(), message);
    }

    private synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        String result;
        try {
            if(!socket.isConnected())
                connect(inetSocketAddress);

            outStream.writeUTF(s);
            outStream.flush();
            result = inputStream.readUTF();
        }catch (IOException a){
            result = ServerMessagesHelper.SOCKET_ERROR_MESSAGE;
        }

        sendMessage.setText(result);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(e);
        }
    }

    public void connect(InetSocketAddress inetSocketAddress) throws IOException {
        this.inetSocketAddress=inetSocketAddress;
        socket.connect(inetSocketAddress);
        try {
            outStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
        }catch (IOException e) {
            System.out.println(ServerMessagesHelper.CONNECTION_ERROR_MESSAGE);
        }
    }
}