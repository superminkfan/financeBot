package com.telegramBot.handler;


import com.telegramBot.bot.Bot;
import com.telegramBot.bot.Buttons;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ChooseLanguage extends AbstractHandler {
    private static final Logger log = Logger.getLogger(ChooseLanguage.class);
    private final String END_LINE = "\n";

    public ChooseLanguage(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(Long chatId, Update update) {

        bot.sendQueue.add(getMessageChoose(chatId));
        return "";
    }

    private SendMessage getMessageChoose(Long chatId) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdown(true);
        StringBuilder text = new StringBuilder();
        text.append("Выбери язык // Choose language!").append(END_LINE);
        Buttons.setInlineKeyBoardLang(sendMessage);
        sendMessage.setText(text.toString());
        return sendMessage;
    }

}
