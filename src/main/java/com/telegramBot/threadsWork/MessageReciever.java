package com.telegramBot.threadsWork;

import com.telegramBot.bot.Bot;
import com.telegramBot.bot.Buttons;
import com.telegramBot.command.Command;
import com.telegramBot.command.ParsedCommand;
import com.telegramBot.command.Parser;
import com.telegramBot.dbWork.categories.InCat;
import com.telegramBot.dbWork.categories.OutCat;
import com.telegramBot.dbWork.heap.HeapWork;
import com.telegramBot.dbWork.users.User;
import com.telegramBot.handler.*;
import com.telegramBot.myUtil.RegEx;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class MessageReciever implements Runnable {
    private static final Logger log = Logger.getLogger(MessageReciever.class);
    private final int WAIT_FOR_NEW_MESSAGE_DELAY = 1000;
    private final String END_LINE = "\n";

    private String kostyl1;
    private float kostyl2;
    private int kostylDay = 0;
    private int kostylMounth = 0;
    private int kostylYear = 0;
    private String kostylMaster = "none";


    private Bot bot;
    private Parser parser;
    public MessageReciever(Bot bot) {
        this.bot = bot;
        parser = new Parser(bot.getBotName());

    }

    @Override
    public void run() {
        log.info("[STARTED] MsgReciever.  Bot class: " + bot);
        while (true) {
            for (Object object = bot.receiveQueue.poll(); object != null; object = bot.receiveQueue.poll()) {
                log.debug("New object for analyze in queue " + object.toString());
                try {
                    analyze(object);
                } catch (IOException e) {
                    log.error("Error in while true in run() in MessageReciever!!!!!!!!!!");
                }
            }
            try {
                Thread.sleep(WAIT_FOR_NEW_MESSAGE_DELAY);
            } catch (InterruptedException e) {
                log.error("Catch interrupt. Exit", e);
                return;
            }
        }
    }

    private void analyze(Object object) throws IOException {
        if (object instanceof Update) {
            Update update = (Update) object;
            log.info("Update recieved: " + update.toString());

            if (update.hasMessage()) {
                if (update.getMessage().hasText()) {

                    String inputText = update.getMessage().getText();
                    Long chatId = update.getMessage().getChatId();

                    if (RegEx.checkWithRegExp(inputText))
                    {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(chatId);
                        sendMessage.setText("я ввел твою хуйню в свою хуйню \n верь мне \n 8======3 ---+-++-+-+-+членоЛазер ");
                        bot.sendQueue.add(sendMessage);


                        HashMap hashMap = RegEx.delimNaPopalam(inputText);
                        String action = (String) hashMap.get("action");
                        action = action.replaceAll(" " , "");
                        float amount = (Float) hashMap.get("amount");
                       // System.out.println("то шо ввёл пользователь _"  + action + "_  " + amount);
                        try {
                            SendMessage s = new SendMessage();
                            s.setChatId(chatId);
                            s.setText("\uD83E\uDD91 кракен атакует ");
                            bot.sendQueue.add(s);

                            if (InCat.searchInCat(chatId,action))
                            {
                                HeapWork.addNewRow(chatId,amount,0 , action);
                            }
                            else if (OutCat.searchOutCat(chatId, action))
                            {
                                HeapWork.addNewRow(chatId,amount,1 , action);
                            }
                            else
                            {
                                SendMessage sendMessage1 = new SendMessage();
                                sendMessage1.setChatId(chatId);
                                sendMessage1.setText("ответный агонь из членоПушки!\n \uD83D\uDC4C 0%E<<<========8");
                                bot.sendQueue.add(sendMessage1);
                                kostyl1 = action;
                                kostyl2 = amount;
                                SendMessage sendMessage2 = Bot.doSendMsg(chatId,"newCat");
                                Buttons.setInlineKeyBoardNewCat(sendMessage2, chatId);
                                bot.sendQueue.add(sendMessage2);
                            }

                        }
                        catch (SQLException e)
                        {
                            log.error("SQL palundra!!! " + e.getLocalizedMessage());
                        }

                    }

                    analyzeForUpdateType(update);
                    String lang = User.getLanguage(chatId);


                    if (kostylDay == 1 || kostylMounth == 1 || kostylYear == 1 || RegEx.yoloRegExp(inputText))//вот тут будет еще условие с регулярными выражениями
                    {
                        if (RegEx.yoloRegExp(inputText))
                        {
                            System.out.println("Proshel vtoroy regExp!");
                            HashMap hashMap = RegEx.delimNaPopalam(inputText);
                            String master = (String)hashMap.get("action");
                            master.replaceAll(" ", "");
                            float lim = (float)hashMap.get("amount");
                            User.setMasterLim(chatId,master,lim);

                        }


                        try {
                            float lim = Float.valueOf(inputText);
                            User.setMasterLim(chatId, kostylMaster, lim);
                            kostylMaster = "none";
                            kostylDay = 0;
                            kostylMounth = 0;
                            kostylYear = 0;

                        } catch (NumberFormatException e) {
                            log.warn("hui na rul`");
                        }
                    }

                    if (inputText.equals(Bot.getFastMsg(lang,"mainBt1"))) {
                        MyFinanceHandler myFinanceHandler = new MyFinanceHandler(bot);
                        myFinanceHandler.operate(chatId,update);
                        //мои финансы
                    }


                    else if (inputText.equals(Bot.getFastMsg(lang,"mainBt2")))
                    {
                        CategoriesHandler categoriesHandler = new CategoriesHandler(bot);
                        categoriesHandler.operate(chatId,update);
                        //категории

                    }
                    else if (inputText.equals(Bot.getFastMsg(lang,"mainBt3")))
                    {
                        InstructionsHandler instructionsHandler = new InstructionsHandler(bot);
                        instructionsHandler.operate(chatId,update);
                        //инструкции
                    }
                    else if (inputText.equals(Bot.getFastMsg(lang,"mainBt4")))
                    {
                        SettingsHandler settingsHandler = new SettingsHandler(bot);
                        settingsHandler.operate(chatId ,  update);
                        //Настройки
                    }





                }


                

            } else if (update.hasCallbackQuery()) {
                String calBack = update.getCallbackQuery().getData();
                Long chatId = update.getCallbackQuery().getMessage().getChatId();
                log.warn("CallbackQuery !!!! ==== " + calBack);



                if (calBack.equals("setRussian"))
                {
                    User.setLanguge(chatId,"russian");
                    SendMessage sendMessage = Bot.doSendMsg(chatId,"changedLang");
                    Buttons.setButtonsMain(sendMessage , chatId);
                    bot.sendQueue.add(sendMessage);

                }
                else if (calBack.equals("setEnglish"))
                {
                    User.setLanguge(chatId,"english");
                    SendMessage sendMessage = Bot.doSendMsg(chatId,"changedLang");
                    Buttons.setButtonsMain(sendMessage , chatId);
                    bot.sendQueue.add(sendMessage);

                }
                else if (calBack.equals("changeLang"))
                {
                    ChooseLanguage chooseLanguage = new ChooseLanguage(bot);
                    chooseLanguage.operate(chatId,update);
                }
                else if (calBack.equals("changeDayLim"))
                {
                    ChangeMasterLimitHandler wat = new ChangeMasterLimitHandler(bot);
                    wat.operate(chatId,"day");
                    kostylDay = 1;
                    kostylMaster = "day";
                }
                else if (calBack.equals("changeMounthLim"))
                {
                    ChangeMasterLimitHandler wat = new ChangeMasterLimitHandler(bot);
                    wat.operate(chatId,"month");
                    kostylMounth = 1;
                    kostylMaster = "month";

                }
                else if (calBack.equals("changeYearLim"))
                {
                    ChangeMasterLimitHandler wat = new ChangeMasterLimitHandler(bot);
                    wat.operate(chatId,"year");
                    kostylYear = 1;
                    kostylMaster = "year";

                }

                else if (calBack.equals("changeLimit"))
                {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Work in progress, bitch!");
                    bot.sendQueue.add(sendMessage);

                    ChangeLimitsHandler changeLimitsHandler = new ChangeLimitsHandler(bot);
                    changeLimitsHandler.operate(chatId,update);
                }
                else if (calBack.equals("incomeCats"))
                {
                    /**
                     * показать все категории дохода
                     */
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Вот эти ребята ");
                    bot.sendQueue.add(sendMessage);
                    ArrayList list = new ArrayList();
                    try {
                         list = InCat.getAllInCats(chatId);
                    } catch (SQLException e) {
                        log.error("SQL error in getAllInCats !!! "+ e.getLocalizedMessage());
                        return;
                    }
                    StringBuilder s = new StringBuilder();
                    for (Object string: list) {
                        s.append(string).append("\n");
                    }

                    SendMessage sendMessage1 = new SendMessage();
                    sendMessage1.setChatId(chatId);
                    sendMessage1.setText(s.toString());
                    bot.sendQueue.add(sendMessage1);

                }
                else if (calBack.equals("spanCats"))
                {
                    /**
                     * показать все категоии разхода
                     */
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(chatId);
                    sendMessage.setText("Вот эти ребята ");
                    bot.sendQueue.add(sendMessage);
                    ArrayList list = new ArrayList();
                    try {
                        list = OutCat.getAllInCats(chatId);
                    } catch (SQLException e) {
                        log.error("SQL error in getAllInCats !!! "+ e.getLocalizedMessage());
                        return;
                    }
                    StringBuilder s = new StringBuilder();
                    for (Object string: list) {
                        s.append(string).append("\n");
                    }

                    SendMessage sendMessage1 = new SendMessage();
                    sendMessage1.setChatId(chatId);
                    sendMessage1.setText(s.toString());
                    bot.sendQueue.add(sendMessage1);
                }
                else if (calBack.equals("span"))
                {
                    try {
                        HeapWork.addNewRow(chatId,kostyl2,1,kostyl1);
                        OutCat.addNewCat(chatId,kostyl1);

                    } catch (SQLException e) {
                        log.error("SQL error in callback! " + e.getLocalizedMessage());
                    }
                }
                else if (calBack.equals("income"))
                {
                    try {
                        HeapWork.addNewRow(chatId,kostyl2,0,kostyl1);
                        InCat.addNewCat(chatId,kostyl1);



                    } catch (SQLException e) {
                        log.error("SQL error in callback! " + e.getLocalizedMessage());
                    }

                }
                else if (calBack.equals("cancel"))
                {
                    MyFinanceHandler myFinanceHandler = new MyFinanceHandler(bot);
                    myFinanceHandler.operate(chatId,update);
                    //мои финансы
                }


            }


        } else log.warn("Cant operate type of object: " + object.toString());
    }




    private void analyzeForUpdateType(Update update) throws IOException {
        Long chatId = update.getMessage().getChatId();
        String inputText = update.getMessage().getText();


        ParsedCommand parsedCommand = parser.getParsedCommand(inputText);
        AbstractHandler handlerForCommand = getHandlerForCommand(parsedCommand.getCommand(), chatId);
        String operationResult = handlerForCommand.operate(chatId, update);
        errorMSG(chatId, operationResult);

    }

    private void errorMSG(Long chatId, String opRes) {
        if (!"".equals(opRes)) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(opRes);
            bot.sendQueue.add(message);
        }
    }

    private AbstractHandler getHandlerForCommand(Command command, Long chatId) {
        if (command == null) {
            log.warn("Null command accepted. This is not good scenario.");
            return new DefaultHandler(bot);
        }
        switch (command) {
            case START:
            case ID:
                SystemHandler systemHandler = new SystemHandler(bot);
                log.info("Handler for command[" + command.toString() + "] is: " + systemHandler);
                return systemHandler;
            case SETTINGS:
                SettingsHandler settingsHandler = new SettingsHandler(bot);
                log.info("Handler for command[" + command.toString() + "] is: " + settingsHandler);
                return settingsHandler;
            case HELP:
                InstructionsHandler instructionsHandler = new InstructionsHandler(bot);
                log.info("Handler for command[" + command.toString() + "] is: " + instructionsHandler);
                return instructionsHandler;
            case CATEGORIES:
                CategoriesHandler categoriesHandler = new CategoriesHandler(bot);
                log.info("Handler for command[" + command.toString() + "] is: " + categoriesHandler);
                return categoriesHandler;
            case FINANCES:
                MyFinanceHandler myFinanceHandler = new MyFinanceHandler(bot);
                log.info("Handler for command[" + command.toString() + "] is: " + myFinanceHandler);
                return myFinanceHandler;
            default:
                log.info("Handler for command[" + command.toString() + "] not Set. Return DefaultHandler");
                return new DefaultHandler(bot);
        }
    }

    
}
