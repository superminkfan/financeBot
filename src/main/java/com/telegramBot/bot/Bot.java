package com.telegramBot.bot;

import com.telegramBot.dbWork.users.User;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Bot extends TelegramLongPollingBot {
    private static final Logger log = Logger.getLogger(Bot.class);
    private final int RECONNECT_PAUSE = 10000;
    private static String PROXY_HOST = "193.70.81.255" /* proxy host */;
    private static Integer PROXY_PORT = 3128 /* proxy port */;
    private static String PROXY_USER = "elrid" /* proxy user */;
    private static String PROXY_PASSWORD = "Hf,jnf999" /* proxy password */;

    private String botName;
    private String botToken;

    public final Queue<Object> sendQueue = new ConcurrentLinkedQueue<>();
    public final Queue<Object> receiveQueue = new ConcurrentLinkedQueue<>();


    public Bot(String botName, String botToken) {
        this.botName = botName;
        this.botToken = botToken;
        setJsonObject();

    }
    public String getBotName() {
        return botName;
    }



    @Override
    public void onUpdateReceived(Update update) {
        log.info("Receive new Update. updateID: " + update.getUpdateId());
        receiveQueue.add(update);
    }

    @Override
    public String getBotUsername() {
        log.debug("Bot name: " + botName);
        return botName;    }

    @Override
    public String getBotToken() {
        log.debug("Bot token: " + botToken);
        return botToken;    }


    public void botConnect() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();




        try {
            telegramBotsApi.registerBot(this);
            log.info("[STARTED] TelegramAPI. Bot Connected. Bot class: " + this);
        }
        catch (TelegramApiRequestException e)
        {
            log.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
            try
            {
                Thread.sleep(RECONNECT_PAUSE);
            }
            catch (InterruptedException e1)
            {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }




    public static JSONObject getJsonObject() {
        return jsonObject;
    }
    public static JSONObject jsonObject;

    public  void setJsonObject() {
        FileReader reader = null;
        try {
            reader = new FileReader("src/main/java/com/telegramBot/bot/language.json");

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка!!! в файл ридере " + e.getLocalizedMessage());
            e.printStackTrace();
        }




//--------------------------------
//        String resource = "language.json";
//
//        // this is the path within the jar file
//        InputStream inputStream = Bot.class.getResourceAsStream("/resources/" + resource);
//        if (inputStream == null) {
//            // this is how we load file within editor (eg eclipse)
//            inputStream = Bot.class.getClassLoader().getResourceAsStream(resource);
//            System.out.println("vot eti rebyata");
//        }
//        System.out.println(inputStream);
//        InputStreamReader isr = new InputStreamReader(inputStream);
//        BufferedReader reader = new BufferedReader(isr);

//-----------------------
        JSONParser jsonParser = new JSONParser();
        try {
            Bot.jsonObject = (JSONObject) jsonParser.parse(reader);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Ошибка в jsone !!! " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public static SendMessage doSendMsg(Long chatId, String pointer)
    {
        String jsonMsg = getMsg(chatId,pointer);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdown(true);
        sendMessage.setText(jsonMsg);
        return sendMessage;
    }

    public static String getMsg(Long chatId, String pointer)
    {
        String lang = User.getLanguage(chatId);
        return getFastMsg(lang,pointer);
    }

    public static String getFastMsg(String lang, String pointer)
    {
        JSONObject jsonLang = (JSONObject) jsonObject.get(lang);
        String jsonMsg = (String) jsonLang.get(pointer);
        return jsonMsg;
    }

}
