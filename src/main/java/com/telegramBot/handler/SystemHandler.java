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




              SendMessage firstMsg = new SendMessage();
              firstMsg.setChatId(chatId);
              firstMsg.setText("Добро пожаловать " +
                        update.getMessage().getChat().getFirstName() + " " +
                        update.getMessage().getChat().getLastName() +  " 🤗");

              bot.sendQueue.add(firstMsg);

              SendMessage sendMessage = Bot.doSendMsg(chatId,"startMsg");

              Buttons.setButtonsMain(sendMessage , chatId);
              bot.sendQueue.add(sendMessage);


        return "";
    }

}
