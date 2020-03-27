package com.telegramBot.handler;


import com.telegramBot.bot.Bot;
import com.telegramBot.bot.Buttons;
import com.telegramBot.dbWork.users.User;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.sql.SQLException;



public class SystemHandler extends AbstractHandler {
    private static final Logger log = Logger.getLogger(SystemHandler.class);

    public SystemHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(Long chatId, Update update) {


        try {
            String maybe = User.searchUser(chatId);

            if (maybe.equals(""))
            {
                log.info("New user arive! ChatId = " + chatId);

                log.info("Doing the DB insert method...");
                try {
                    User.addNewUser(chatId);
                }
                catch (SQLException e)
                {
                    log.error("Ошибка Sql in addNewUser " + e.getLocalizedMessage());
                }
//                ChooseLanguage chooseLanguage = new ChooseLanguage(bot);
//                return chooseLanguage.operate(chatId, update);

            }
            else {
                log.info("Old user. ChatId = " + chatId);

            }

        } catch (SQLException e) {
            log.error("Ошибка Sql vot zdecs " + e.getLocalizedMessage());
        }


        StringBuilder s = new StringBuilder();
        String firstName = null;
        String lastName = null;
        String userName = null;
        try {
            firstName = update.getMessage().getChat().getFirstName();
            System.out.println(firstName);
        }
        catch (NullPointerException e)
        {
            log.error("popalas` nullpointerEx firstName " + e.getMessage());

        }
        try {
            lastName = update.getMessage().getChat().getLastName();
            System.out.println(lastName);
        }
        catch (NullPointerException e)
        {
            log.error("popalas` nullpointerEx lastName " + e.getMessage());
        }

        try {
            userName = update.getMessage().getChat().getUserName();
            System.out.println(userName);
        }
        catch (NullPointerException e)
        {
            log.error("popalas` nullpointerEx userName " + e.getMessage());
        }

        String text = "";


        if (firstName == null && lastName == null && userName == null)
        {
            text = "";
            goWat(chatId , text);
            return "";
        }

        if (firstName != null && lastName != null)
        {
            text = firstName + " " + lastName;
            goWat(chatId , text);
            return "";
        }
        else if (firstName != null)
        {
            text = firstName;
            goWat(chatId , text);
            return "";
        }
        else if (lastName != null)
        {
            text = lastName;
            goWat(chatId , text);
            return "";
        }


return "";
    }

    public void goWat(Long chatId, String text) {
        SendMessage firstMsg = new SendMessage();
        firstMsg.setChatId(chatId);
        firstMsg.setText("Добро пожаловать " + text +  " 🤗");
        bot.sendQueue.add(firstMsg);
        SendMessage sendMessage = Bot.doSendMsg(chatId,"startMsg");
        Buttons.setButtonsMain(sendMessage , chatId);
        bot.sendQueue.add(sendMessage);
    }


}
