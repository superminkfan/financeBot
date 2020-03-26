package com.telegramBot.dbWork.categories;

import com.telegramBot.dbWork.Conn;
import com.telegramBot.dbWork.users.User;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InCategory {
    private static final Logger log = Logger.getLogger(InCategory.class);

    public static boolean searchInCat(Long chatId , String maybeNameCat) throws SQLException {
        log.info("Executing select statmt for searchInCat...");

        try {
            Conn.connect();
        } catch (SQLException e) {
            log.error("Sql error in searchInCat " + e.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            log.error("No class found!" + e.getLocalizedMessage());
        }


        ResultSet resSet1 = Conn.statmt.executeQuery(
                "SELECT * FROM inCat WHERE chatid = 1 AND nameCat = '" + maybeNameCat + "';");
        if (resSet1.isClosed())
        {
            log.warn("standart categories result set is empty!!!!!!!!!! " + User.class.toString());
        }
        else {
            log.info("got one nameCat key from db");
            try {
                Conn.CloseDB();
            } catch (ClassNotFoundException e) {
                log.error("Error closing db connection " +  e.getLocalizedMessage());
            }
            return true;
        }



        ResultSet resSet = Conn.statmt.executeQuery(
                "SELECT * FROM inCat WHERE chatid = " + chatId + " AND nameCat = '" + maybeNameCat + "';");

        if (resSet.isClosed())
        {
            log.warn("result set is empty!!!!!!!!!! " + User.class.toString());
            try {
                Conn.CloseDB();
            } catch (ClassNotFoundException e) {
                log.error("Error closing db connection " +  e.getLocalizedMessage());
            }
            return false;
        }

        log.info("got one nameCat key from db");
        //String nameCat = resSet.getString("nameCat");
        try {
            Conn.CloseDB();
        } catch (ClassNotFoundException e) {
            log.error("Error closing db connection " +  e.getLocalizedMessage());
        }


        return true;
    }

    public static ArrayList getAllInCats(Long chatId, int i ) throws SQLException {
        log.info("Executing select statmt for getAllInCats...");

        try {
            Conn.connect();
        } catch (SQLException e) {
            log.error("Sql error in getAllInCats " + e.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            log.error("No class found!" + e.getLocalizedMessage());
        }

        ResultSet resSet = null;
        if (i ==1) {
            resSet = Conn.statmt.executeQuery(
                    "SELECT * FROM inCat WHERE chatid = " + chatId + "  OR  chatid = 1 ;");
        }
        else  if(i == 2)
        {
            resSet = Conn.statmt.executeQuery(
                    "SELECT * FROM inCat WHERE chatid = " + chatId + ";");
        }
        if (resSet.isClosed())
        {
            log.warn("result set is empty!!!!!!!!!! " + User.class.toString());
            try {
                Conn.CloseDB();
            } catch (ClassNotFoundException e) {
                log.error("Error closing db connection " +  e.getLocalizedMessage());
            }
            return null;
        }

        ArrayList list = new ArrayList();
        String elem = null;
        while (resSet.next())
        {
             elem = resSet.getString("nameCat");
             list.add(elem);
        }
        try {
            Conn.CloseDB();
        } catch (ClassNotFoundException e) {
            log.error("Error closing db connection " +  e.getLocalizedMessage());
        }


        return list;
    }

    public static int addNewCat(Long chatId , String nameCat) throws SQLException {
        if (nameCat.equals("")) {
            return 1;
        } else {
            try {
                Conn.connect();
            } catch (SQLException e) {
                log.error("Sql error in addNewUser " + e.getMessage());
            } catch (ClassNotFoundException e) {
                log.error("No class found!" + e.getLocalizedMessage());
            }
            log.info("Executing insert statmt for new user");
            Conn.statmt.execute("INSERT INTO inCat (chatid , nameCat) VALUES ( " + chatId + ",'" + nameCat + "');");

            try {
                Conn.CloseDB();
            } catch (ClassNotFoundException e) {
                log.error("Error closing db connection " + e.getLocalizedMessage());
            }
            return 0;
        }
    }

    public static int changeInCat(Long chatId , String oldNameCat , String newNameCat) throws SQLException {
            try {
                Conn.connect();
            } catch (SQLException e) {
                log.error("Sql error in changeInCat " + e.getMessage());
            } catch (ClassNotFoundException e) {
                log.error("No class found!" + e.getLocalizedMessage());
            }
            log.info("Executing update statmt for cat");
            Conn.statmt.execute("UPDATE inCat set nameCat = '" + newNameCat + "' WHERE nameCat = '" + oldNameCat +
                    "' and " +
                    " chatid = " + chatId  + ";");

        Conn.statmt.execute("UPDATE heap set nameCat = '" + newNameCat + "' WHERE nameCat = '" + oldNameCat +
                "' and " +
                " chatid = " + chatId  + ";");

            try {
                Conn.CloseDB();
            } catch (ClassNotFoundException e) {
                log.error("Error closing db connection " + e.getLocalizedMessage());
            }
            return 0;
        }

    public static int deleteInCat(Long chatId , String nameCat) throws SQLException {
        try {
            Conn.connect();
        } catch (SQLException e) {
            log.error("Sql error in changeInCat " + e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error("No class found!" + e.getLocalizedMessage());
        }
        log.info("Executing update statmt for cat");
        Conn.statmt.execute("DELETE inCat WHERE nameCat = '" + nameCat + "' and chatid = " + chatId +  ";");
        Conn.statmt.execute("DELETE heap WHERE nameCat = '" + nameCat + "' and chatid = " + chatId +  ";");

        try {
            Conn.CloseDB();
        } catch (ClassNotFoundException e) {
            log.error("Error closing db connection " + e.getLocalizedMessage());
        }
        return 0;
    }

}
