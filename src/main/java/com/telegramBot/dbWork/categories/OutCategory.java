package com.telegramBot.dbWork.categories;

import com.telegramBot.dbWork.Conn;
import com.telegramBot.dbWork.users.User;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OutCategory {
    private static final Logger log = Logger.getLogger(OutCategory.class);

    public static boolean searchOutCat(Long chatId , String maybeNameCat) throws SQLException {
        log.info("Executing select statmt for searchOutCat...");
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
                "SELECT * FROM outCat WHERE chatid = 1 AND nameCat = '" + maybeNameCat + "';");
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
                "SELECT * FROM outCat WHERE chatid = " + chatId + " AND nameCat = '" + maybeNameCat + "';");

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
        else {
            log.info("got one nameCat key from db");
            //nameCat = resSet.getString("nameCat");
            try {
                Conn.CloseDB();
            } catch (ClassNotFoundException e) {
                log.error("Error closing db connection " +  e.getLocalizedMessage());
            }
        }

        return true;
    }


    public static ArrayList getAllOutCats(Long chatId ) throws SQLException {
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


        ResultSet resSet = Conn.statmt.executeQuery(
                "SELECT * FROM outCat WHERE chatid = " + chatId + "  OR  chatid = 1 ;");

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

    public static int addNewCat(Long chatId , String nameCat) throws SQLException
    {
        if (nameCat.equals("")) {
            return 1;
        }
        else {
            try {
                Conn.connect();
            } catch (SQLException e) {
                log.error("Sql error in addNewUser " + e.getMessage());
            } catch (ClassNotFoundException e) {
                log.error("No class found!" + e.getLocalizedMessage());
            }
            log.info("Executing insert statmt for new user");
            Conn.statmt.execute("INSERT INTO outCat (chatid , nameCat) VALUES ( " + chatId + ",'" + nameCat + "');");


            try {
                Conn.CloseDB();
            } catch (ClassNotFoundException e) {
                log.error("Error closing db connection " + e.getLocalizedMessage());
            }
            return 0;

        }
    }


    public static int changeOutCat(Long chatId , String oldNameCat , String newNameCat) throws SQLException {
        try {
            Conn.connect();
        } catch (SQLException e) {
            log.error("Sql error in changeInCat " + e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error("No class found!" + e.getLocalizedMessage());
        }
        log.info("Executing update statmt for cat");
        Conn.statmt.execute("UPDATE outCat set nameCat = " + newNameCat + " WHERE nameCat = " + oldNameCat +
                " and " +
                " chatid = " + chatId  + ";");
        Conn.statmt.execute("UPDATE heap set nameCat = " + newNameCat + " WHERE nameCat = " + oldNameCat +
                " and " +
                " chatid = " + chatId  + ";");

        try {
            Conn.CloseDB();
        } catch (ClassNotFoundException e) {
            log.error("Error closing db connection " + e.getLocalizedMessage());
        }
        return 0;
    }

    public static int deleteOutCat(Long chatId , String nameCat) throws SQLException {
        try {
            Conn.connect();
        } catch (SQLException e) {
            log.error("Sql error in changeInCat " + e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error("No class found!" + e.getLocalizedMessage());
        }
        log.info("Executing update statmt for cat");
        Conn.statmt.execute("DELETE outCat WHERE nameCat = " + nameCat + " and chatid = " + chatId +  ";");
        Conn.statmt.execute("DELETE heap WHERE nameCat = " + nameCat + " and chatid = " + chatId +  ";");

        try {
            Conn.CloseDB();
        } catch (ClassNotFoundException e) {
            log.error("Error closing db connection " + e.getLocalizedMessage());
        }
        return 0;
    }
}
