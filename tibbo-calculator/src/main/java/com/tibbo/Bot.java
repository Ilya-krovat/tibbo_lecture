package com.tibbo;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


public class Bot extends TelegramLongPollingBot
{
    private Socket socket;

    public Bot()
    {
        super(new DefaultBotOptions()
        {
            @Override
            public String getBaseUrl() {
                return ServerMessagesHelper.BASE_URL;
            }
        });

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
        try {
            DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

            outStream.writeUTF(s);
            outStream.flush();

            String result = inputStream.readUTF();

            sendMessage.setText(result);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                System.out.println(e);
            }
        }catch (IOException e) {
            System.out.println(e);
        }
    }

    public void connect(InetSocketAddress inetSocketAddress) throws IOException {
        socket.connect(inetSocketAddress);
    }
}
