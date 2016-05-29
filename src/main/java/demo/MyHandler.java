package demo;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description of demo
 *
 * @author evgen1000end
 * @since 29.05.2016
 */
public class MyHandler extends TelegramLongPollingBot {

    private ConcurrentHashMap<String, Long> chats = new ConcurrentHashMap<>();


    public MyHandler() {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {

            while (true){


                chats.entrySet().forEach(stringLongEntry ->
                {
                   Long chatId =  stringLongEntry.getValue();

                    Date date = new Date();

                    sendMessageToChat(prepareResponse(chatId,date.toString() ));

                });


                Thread.sleep(2000);
            }

        }
        );

    }

    @Override
    public String getBotToken() {
        return "198783527:AAGzDjSgE1it7lH24CFrDtXfUsidPeaZW90";
    }


    private SendMessage prepareResponse(Long id, String message) {
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(id.toString());
        sendMessageRequest.setText(message);
        return sendMessageRequest;
    }

    private void sendMessageToChat(SendMessage sendMessage) {
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();
        if (message.getText().equals("r")) {
            sendMessageToChat(prepareResponse(message.getChatId(), "Вы успешно зарегистрированы на уведомления!"));
            chats.put(message.getChatId().toString(), message.getChatId());
        }

        if (message.getText().equals("u")) {
            sendMessageToChat(prepareResponse(message.getChatId(), "Вы отписаны от увеломлений!! Жаль :("));
            chats.remove(message.getChatId().toString());
        }
    }

    @Override
    public String getBotUsername() {
        return "LogNotificationBot";
    }
}
