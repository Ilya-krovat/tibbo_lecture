package com.tibbo.telegram;

import org.telegram.telegrambots.bots.DefaultBotOptions;

public class MyDefaultBotOptions extends DefaultBotOptions
{
    @Override
    public String getBaseUrl() {
        return ServerMessagesHelper.BASE_URL;
    }
}