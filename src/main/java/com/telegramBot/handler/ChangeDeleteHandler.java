package com.telegramBot.handler;

import com.telegramBot.bot.Bot;
import com.telegramBot.bot.Buttons;
import com.telegramBot.dbWork.categories.InCategory;
import com.telegramBot.dbWork.categories.OutCategory;
import com.telegramBot.dbWork.users.User;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.management.AttributeList;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChangeDeleteHandler extends AbstractHandler  {
    private static final Logger log = Logger.getLogger(ChangeDeleteHandler.class);
    private final String END_LINE = "\n";

    public ChangeDeleteHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(Long chatId, Update update) {

        SendMessage sendMessage = Bot.doSendMsg(chatId,"ChangeDeleteHandler");
        Buttons.setInlineKeyBoardChangeDelete(sendMessage, chatId);
        bot.sendQueue.add(sendMessage);
        return "";
    }

    public  String operateDelete(Long chatId, int service)
    {

        User.setServiceDelete(chatId,1);

        SendMessage sendMessage = Bot.doSendMsg(chatId , "operateOnDelete");

        ArrayList list = new ArrayList();
        try {
            if (service == 2)
                list = OutCategory.getAllOutCats(chatId , 2);
            else if (service == 1)
                list = InCategory.getAllInCats(chatId , 2);
        } catch (SQLException e) {
            log.error("SQL error in getAllInCats !!! "+ e.getLocalizedMessage());
            return "";
        }

try {


    Buttons.setKeyBoardCategoriesNewTest(sendMessage, chatId, list, bot);
    bot.sendQueue.add(sendMessage);
}
catch (NullPointerException e)
{
    log.error("pustoy spisok");
    SendMessage sendMessage1 = new SendMessage();
    sendMessage1.setChatId(chatId);
    String lang = User.getLanguage(chatId);
    if (lang.equals("russian"))
    sendMessage1.setText("Список категорий для удаления пуст!");
    else
        sendMessage1.setText("Category list for delete is empty!");
Buttons.setButtonsMain(sendMessage1,chatId);
    bot.sendQueue.add(sendMessage1);

    User.setServiceDelete(chatId , 0);
}

        return "";
    }

    public  String operateChange(Long chatId, int service)
    {
        User.setServiceChange(chatId,1);

        SendMessage sendMessage = Bot.doSendMsg(chatId , "operateOnChange");

        ArrayList list = new ArrayList();
        try {
            if (service == 2)
                list = OutCategory.getAllOutCats(chatId , 2);
            else if (service == 1)
                list = InCategory.getAllInCats(chatId , 2);
        } catch (SQLException e) {
            log.error("SQL error in getAllInCats !!! "+ e.getLocalizedMessage());
            return "";
        }

try {
    Buttons.setKeyBoardCategoriesNewTest(sendMessage, chatId, list, bot);
    bot.sendQueue.add(sendMessage);
}
catch (NullPointerException e)
{
    log.error("pustoy spisok");
    SendMessage sendMessage1 = new SendMessage();
    sendMessage1.setChatId(chatId);
    String lang = User.getLanguage(chatId);
    if (lang.equals("russian"))
        sendMessage1.setText("Список категорий для изменения пуст!");
    else
        sendMessage1.setText("Category list for change is empty!");
    Buttons.setButtonsMain(sendMessage1,chatId);

    bot.sendQueue.add(sendMessage1);

    User.setServiceChange(chatId , 0);
}
        return "";

    }
}
