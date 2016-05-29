package demo;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

/**
 * stas
 * 12/10/15.
 */
public class    TestTelegramBot extends BotHandler {

    TelegramBot bot = TelegramBotAdapter.buildDebug("198783527:AAGzDjSgE1it7lH24CFrDtXfUsidPeaZW90");

    @Override
    protected boolean onStart(Message message) {
        Long chatId = message.chat().id();
        bot.sendMessage(chatId, "Welcome!");
        return true;
    }

    @Override
    void onWebhookUpdate(Update update) {
        Message message = update.message();
        bot.sendMessage(message.chat().id(), "Ok, I hear you!");
    }
}
