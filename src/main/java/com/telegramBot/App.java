package com.telegramBot;


import com.telegramBot.bot.Bot;
import com.telegramBot.threadsWork.MessageReciever;
import com.telegramBot.threadsWork.MessageSender;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

//import com.crypto.telegrambot.threadsWork.transService.RefreshStatus;


public class App {
    private static final int PRIORITY_FOR_SENDER = 1;
    private static final int PRIORITY_FOR_RECEIVER = 3;
    private static final String BOT_ADMIN = "687187915";



    public static void main(String[] args) {
        ApiContextInitializer.init();
        Bot bot = new Bot("financeTOGUbot", "958126512:AAGvd4troi3MmLlnUOReujPOKJAs8NgMPlM");

        MessageReciever messageReciever = new MessageReciever(bot);
        MessageSender messageSender = new MessageSender(bot);

        bot.botConnect();

        Thread receiver = new Thread(messageReciever);
        receiver.setDaemon(true);
        receiver.setName("MsgReciever");
        receiver.setPriority(PRIORITY_FOR_RECEIVER);
        receiver.start();

       Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("MsgSender");
        sender.setPriority(PRIORITY_FOR_SENDER);
        sender.start();


        sendStartReport(bot);
    }

    private static void sendStartReport(Bot bot) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(BOT_ADMIN);
        sendMessage.setText("\uD83C\uDF4C");
        bot.sendQueue.add(sendMessage);
    }

}
