package com.tibbo.telegram;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.logging.Level;

public class Bot extends TelegramLongPollingBot
{
  
  /**
   * Метод для приема сообщений.
   * @param update Содержит сообщение от пользователя.
   */
  @Override
  public void onUpdateReceived(Update update) {
    String message = update.getMessage().getText();
    sendMsg(update.getMessage().getChatId().toString(), message);
  }
  
  /**
   * Метод для настройки сообщения и его отправки.
   * @param chatId id чата
   * @param s Строка, которую необходимот отправить в качестве сообщения.
   */
  public synchronized void sendMsg(String chatId, String s) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdown(true);
    sendMessage.setChatId(chatId);
    sendMessage.setText(s);
    try {
      sendMessage(sendMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Метод возвращает имя бота, указанное при регистрации.
   * @return имя бота
   */
  @Override
  public String getBotUsername() {
    return "TibboAggreGateBot";
  }
  
  public Bot(DefaultBotOptions options)
  {
    super(options);
  }
  
  public static Bot getBot()
  {
    return new Bot(new DefaultBotOptions()
    {
      @Override
      public String getBaseUrl()
      {
        return "http://104.248.243.143:18012/";
      }
    });
  }
  
  /**
   * Метод возвращает token бота для связи с сервером Telegram
   * @return token для бота
   */
  @Override
  public String getBotToken() {
    return "924974295:AAH6WJOU2YrsD-aTJ-zlfEBheNi5GfdeP4o";
  }
  
  
  public static void main(String[] args) throws TelegramApiRequestException
  {
    ApiContextInitializer.init();
    try {
      TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
      telegramBotsApi.registerBot(Bot.getBot());
    } catch (TelegramApiRequestException e) {
      e.printStackTrace();
    }
  }
}
