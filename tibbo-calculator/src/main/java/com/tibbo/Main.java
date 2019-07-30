package com.tibbo;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main
{
    public static void main(String[] args) {
        int port = 5555;
        try {
            port = Integer.parseInt(args[0]);
        }catch (NullPointerException e) {
            System.out.println("port is default (NullPointerException)");
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("port is default (ArrayIndexOutOfBoundsException)");
        }catch (NumberFormatException e) {
            System.out.println("port is default (NumberFormatException)");
        }

        try {
            TelegramBot bot = new TelegramBot();
            try {
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
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}
