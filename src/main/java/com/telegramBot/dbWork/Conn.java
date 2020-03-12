package com.telegramBot.dbWork;

import org.apache.log4j.Logger;

import java.sql.*;

public class Conn {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    private static final Logger log = Logger.getLogger(Conn.class);



    public static void connect() throws ClassNotFoundException, SQLException
    {
        try {
            log.info("Start the db connection!");
            conn = null;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/java/com/telegramBot/dbWork/users.db");

            statmt = conn.createStatement();
            log.info("Connection [" + conn.toString() + "] DONE");
        }
        catch (SQLException e)
        {
            log.error("SQL error! PALUNDRA!!!!!" + e.getMessage() + "  " + e.getSQLState());
        }

    }


    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        String s = conn.toString();
        conn.close();
        statmt.close();
//        resSet.close();
        log.info("Connection [" + s  + "] CLOSED! ");
            }
}
