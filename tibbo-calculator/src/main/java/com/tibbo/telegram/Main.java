package com.tibbo.telegram;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main
{
    public static void main(String[] args) {
        Integer port = ServerMessagesHelper.parse(args);

        try
        {
            TelegramBot bot = new TelegramBot();

            bot.connect(new InetSocketAddress(ServerMessagesHelper.NAME_OF_HOST,port));

            ApiContextInitializer.init();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            try {
                telegramBotsApi.registerBot(bot);
            } catch (TelegramApiRequestException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
