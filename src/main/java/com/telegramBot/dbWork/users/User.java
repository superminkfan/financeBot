package com.telegramBot.dbWork.users;

import com.telegramBot.dbWork.Conn;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class User {
    private static final Logger log = Logger.getLogger(User.class);

    public static String searchUser(Long chatId) throws SQLException {
        log.info("Executing select statmt for searchUser...");

        try {
            Conn.connect();
        } catch (SQLException e) {
            log.error("Sql error in searchUser " + e.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            log.error("No class found!" + e.getLocalizedMessage());
        }

        ResultSet resSet = Conn.statmt.executeQuery(
                "SELECT * FROM users WHERE chatid = " + chatId);

        if (resSet.isClosed())
        {
            log.warn("result set is empty---new user or error? " + User.class.toString());
            return "";
        }


        try {
            Conn.CloseDB();
        } catch (ClassNotFoundException e) {
            log.error("Error closing db connection " +  e.getLocalizedMessage());
        }


        return chatId.toString();
    }

    public static void addNewUser(Long chatId) throws SQLException
    {
        try {
            Conn.connect();
        } catch (SQLException e) {
            log.error("Sql error in addNewUser " + e.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            log.error("No class found!" + e.getLocalizedMessage());
        }
        log.info("Executing insert statmt for new user");
        Conn.statmt.execute("INSERT INTO users (chatid) VALUES ( "  + chatId  + ");");


        try {
            Conn.CloseDB();
        } catch (ClassNotFoundException e) {
            log.error("Error closing db connection " +  e.getLocalizedMessage());
        }
    }


    public static void setLanguge(Long chatId , String lang)
    {
        try {
            Conn.connect();
        } catch (SQLException e) {
            log.error("Sql error in  setLanguge()" + e.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            log.error("No class found!" + e.getLocalizedMessage());
        }

        log.info("Executing update statmt for setLanguge...");
        try {
            Conn.statmt.execute("UPDATE users SET lang = '"  + lang + "' where chatid =  " + chatId + ";");
        } catch (SQLException e) {
            log.error("Error in execute setLanguge!"  + e.getLocalizedMessage());
        }

        try {
            Conn.CloseDB();
        } catch (ClassNotFoundException e) {
            log.error("Class not found! " +  e.getLocalizedMessage());
        } catch (SQLException e) {
            log.error("Error closing db connection " +  e.getLocalizedMessage());
        }

    }

    public static String getLanguage(Long chatId)
    {
        String language = "";
        log.info("Executing select statmt for getLanguage for users table...");

        try {
            Conn.connect();
            ResultSet resSet = null;
            resSet = Conn.statmt.executeQuery(
                    "SELECT * FROM users WHERE chatId = " + chatId + ";");

            if (resSet.isClosed()) {
                log.warn("result set is empty, thats odd");
                return "";
            }
            language = resSet.getString("lang");

            Conn.CloseDB();
        } catch (SQLException e) {
            log.error("SQL exeptrion in getLanguage()! " + e.getLocalizedMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return language;
    }


    public static void setMasterLim(Long chatId , String master , float limit)
    {
        try {
            Conn.connect();
        } catch (SQLException e) {
            log.error("Sql error in  setMasterLim()" + e.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            log.error("No class found!" + e.getLocalizedMessage());
        }

        log.info("Executing update statmt for setMasterLim...");
        try {
            Conn.statmt.execute("UPDATE users SET " + master + " = "  + limit + " where chatid =  " + chatId + ";");
        } catch (SQLException e) {
            log.error("Error in execute setMasterLim!"  + e.getLocalizedMessage());
        }


        try {
            Conn.CloseDB();
        } catch (ClassNotFoundException e) {
            log.error("Class not found! " +  e.getLocalizedMessage());
        } catch (SQLException e) {
            log.error("Error closing db connection " +  e.getLocalizedMessage());
        }

    }

    public static HashMap getBudget(Long chatId)
    {
        HashMap hashMap = new HashMap();
        log.info("Executing select statmt for getBudget for users table...");

        try {
            Conn.connect();
            ResultSet resSet = null;
            resSet = Conn.statmt.executeQuery(
                    "SELECT * FROM users WHERE chatId = " + chatId + ";");

            if (resSet.isClosed()) {
                log.warn("result set is empty, thats odd");
                return null;
            }

          float day = resSet.getFloat("day");
          float mounth = resSet.getFloat("month");
          float year = resSet.getFloat("year");
              hashMap.put("day",day);
              hashMap.put("mounth",mounth);
              hashMap.put("year",year);

            Conn.CloseDB();
        } catch (SQLException e) {
            log.error("SQL exeptrion in getBudget()! " + e.getLocalizedMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return hashMap;
    }






    public static int getNumberOfUsers()
    {
        int i = 0;
        log.info("Executing select statmt for getNumberOfUsers ...");
        try {
            Conn.connect();


        ResultSet resSet = Conn.statmt.executeQuery(
                "SELECT * FROM users ;");

        if (resSet.isClosed())
        {
            log.warn("result set is empty, thats odd");
            return -1;
        }

        while (resSet.next())
        {
            i = i + 1;
        }
        try {
            Conn.CloseDB();
        } catch (ClassNotFoundException e) {
            log.error("Error closing db connection " +  e.getLocalizedMessage());
        }
        } catch (SQLException e) {
            log.error("Sql connection error in getNumberOfUsers()" + e.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            log.error("No class found!" + e.getLocalizedMessage());
        }

        return i+1;
    }


}
