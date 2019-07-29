package com.tibbo;

import com.tibbo.Server;
import com.tibbo.ServerMessagesHelper;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main
{
    public static void main(String[] args) {

        for (String str : args)
        {
            System.out.println(str);
        }


        try {
            Bot bot = new Bot();
            try {
                bot.connect(new InetSocketAddress(ServerMessagesHelper.NAME_OF_HOST,5555));

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
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}
