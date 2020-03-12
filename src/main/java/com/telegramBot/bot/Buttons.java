package com.telegramBot.bot;

import com.telegramBot.dbWork.users.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Buttons {



    public static void setInlineKeyBoardLang(SendMessage sendMessage) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("\uD83C\uDDF7\uD83C\uDDFA Русский");
        inlineKeyboardButton1.setCallbackData("setRussian");
        inlineKeyboardButton2.setText("\uD83C\uDDEC\uD83C\uDDE7 English");
        inlineKeyboardButton2.setCallbackData("setEnglish");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }

    public static void setInlineKeyBoardSettings(SendMessage sendMessage , Long chatId) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        String lang = User.getLanguage(chatId);
        inlineKeyboardButton2.setText(Bot.getFastMsg(lang,"settingsBt1"));
        inlineKeyboardButton2.setCallbackData("changeLang");
        inlineKeyboardButton3.setText(Bot.getFastMsg(lang,"settingsBt2"));
        inlineKeyboardButton3.setCallbackData("changeLimit");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        keyboardButtonsRow2.add(inlineKeyboardButton3);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }

    public static void setButtonsMain(SendMessage sendMessage , Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        String lang = User.getLanguage(chatId);
        keyboardFirstRow.add(new KeyboardButton(Bot.getFastMsg(lang,"mainBt1")));
        keyboardFirstRow.add(new KeyboardButton(Bot.getFastMsg(lang,"mainBt2")));
        keyboardSecondRow.add(new KeyboardButton(Bot.getFastMsg(lang,"mainBt3")));
        keyboardSecondRow.add(new KeyboardButton(Bot.getFastMsg(lang,"mainBt4")));
        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

    public static void setInlineKeyBoardNewCat(SendMessage sendMessage , Long chatId) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        String lang = User.getLanguage(chatId);
        inlineKeyboardButton1.setText(Bot.getFastMsg(lang,"newCatBt3"));
        inlineKeyboardButton1.setCallbackData("cancel");
        inlineKeyboardButton2.setText(Bot.getFastMsg(lang,"newCatBt1"));
        inlineKeyboardButton2.setCallbackData("span");
        inlineKeyboardButton3.setText(Bot.getFastMsg(lang,"newCatBt2"));
        inlineKeyboardButton3.setCallbackData("income");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        keyboardButtonsRow1.add(inlineKeyboardButton3);
        keyboardButtonsRow2.add(inlineKeyboardButton1);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }

    public static void setInlineKeyBoardChangeLim(SendMessage sendMessage , Long chatId) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        String lang = User.getLanguage(chatId);
        inlineKeyboardButton1.setText(Bot.getFastMsg(lang,"changeLimitsBt1"));
        inlineKeyboardButton1.setCallbackData("changeDayLim");
        inlineKeyboardButton2.setText(Bot.getFastMsg(lang,"changeLimitsBt2"));
        inlineKeyboardButton2.setCallbackData("changeMounthLim");
        inlineKeyboardButton3.setText(Bot.getFastMsg(lang,"changeLimitsBt3"));
        inlineKeyboardButton3.setCallbackData("changeYearLim");
        inlineKeyboardButton4.setText(Bot.getFastMsg(lang,"changeLimitsBt4"));
        inlineKeyboardButton4.setCallbackData("cancel");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        keyboardButtonsRow1.add(inlineKeyboardButton3);
        keyboardButtonsRow2.add(inlineKeyboardButton4);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }

    public static void setInlineKeyBoardCategories(SendMessage sendMessage , Long chatId) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        String lang = User.getLanguage(chatId);
        inlineKeyboardButton1.setText(Bot.getFastMsg(lang,"catMsg3"));
        inlineKeyboardButton1.setCallbackData("cancel");
        inlineKeyboardButton2.setText(Bot.getFastMsg(lang,"catMsg1"));
        inlineKeyboardButton2.setCallbackData("incomeCats");
        inlineKeyboardButton3.setText(Bot.getFastMsg(lang,"catMsg2"));
        inlineKeyboardButton3.setCallbackData("spanCats");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        keyboardButtonsRow1.add(inlineKeyboardButton3);
        keyboardButtonsRow2.add(inlineKeyboardButton1);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }







}
