package com.telegramBot.dbWork.heap;

import com.telegramBot.dbWork.Conn;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HeapWork {
    private static final Logger log = Logger.getLogger(HeapWork.class);

    public static void addNewRow(Long chatId , float amount , int service , String nameCat) throws SQLException
    {
        try {
            Conn.connect();
        } catch (SQLException e) {
            log.error("Sql error in addNewRow " + e.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            log.error("No class found!" + e.getLocalizedMessage());
        }
        log.info("Executing insert statmt for new row in heap");
        Conn.statmt.execute("INSERT INTO  heap(chatid , amount , service, nameCat) VALUES ( "  + chatId  + ',' + amount + ',' + service + ",'" + nameCat + "');");

        try {
            Conn.CloseDB();
        } catch (ClassNotFoundException e) {
            log.error("Error closing db connection " +  e.getLocalizedMessage());
        }
    }

    public static ArrayList getDayStatSpans(Long chatId) {
        ArrayList arrayList = new ArrayList();

        Calendar time = Calendar.getInstance();
        time.add(Calendar.MILLISECOND, -time.getTimeZone().getOffset(time.getTimeInMillis()));
        Date now = time.getTime();

        Date yesterday = Date.from(now.toInstant().minusSeconds(86400));
        log.info("Executing select statmt for getDayStatSpans for users table...");

        try {
            Conn.connect();
            ResultSet resSet = null;
            resSet = Conn.statmt.executeQuery(
                    "SELECT * FROM heap WHERE chatId = " + chatId + " AND service =  1;");


            if (resSet.isClosed()) {
                log.warn("result set is empty, thats odd");
                arrayList.add("none");
                //return hashMap;
            } else {


                while (resSet.next()) {
                    String string = resSet.getString("date");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//задаю формат даты
                    Date date = formatter.parse(string);//создаю дату через

                    if (!yesterday.after(date) && !now.before(date)) {//все записи за день
                        float temp = resSet.getFloat("amount");
                        String nameCat = resSet.getString("nameCat");
                        arrayList.add("" + nameCat + "---" + temp);

                    }
                }


            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

        public static HashMap getStat(Long chatId , int service)
    {

        HashMap hashMap = new HashMap();

        Calendar time = Calendar.getInstance();
        time.add(Calendar.MILLISECOND, -time.getTimeZone().getOffset(time.getTimeInMillis()));
        Date now = time.getTime();

        Date yesterday = Date.from(now.toInstant().minusSeconds(86400));
        Date mounthAgo = Date.from(now.toInstant().minusSeconds(2629743));


        log.info("Executing select statmt for getStat for users table...");

        try {
            Conn.connect();
            ResultSet resSet = null;
            resSet = Conn.statmt.executeQuery(
                    "SELECT * FROM heap WHERE chatId = " + chatId + " AND service = " + service + ";");


            if (resSet.isClosed()) {
                log.warn("result set is empty, thats odd");
                hashMap.put("sumDay",Float.valueOf(0));
                hashMap.put("nameCatDay","");
                hashMap.put("maxDayAmount",Float.valueOf(0));
                //return hashMap;
            }

            else {


                float sum = 0;
                float max = 0;
                String nameCatMax = "";

                while (resSet.next()) {
                    String string = resSet.getString("date");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//задаю формат даты
                    Date date = formatter.parse(string);//создаю дату через

                    if (!yesterday.after(date) && !now.before(date)) {//все записи за день
                        float temp = resSet.getFloat("amount");
                        if (max <= temp) {
                            max = temp;
                            nameCatMax = resSet.getString("nameCat");
                        }
                        sum = sum + temp;
                    }
                }
                hashMap.put("sumDay", sum);
                hashMap.put("nameCatDay", nameCatMax);
                hashMap.put("maxDayAmount", max);
            }



            ResultSet resSet1 = null;
            resSet1 = Conn.statmt.executeQuery(
                    "SELECT * FROM heap WHERE chatId = " + chatId + " AND service = " + service + ";");


            if (resSet1.isClosed()) {
                log.warn("result set is empty, thats odd");
                hashMap.put("sumMounth", Float.valueOf(0));
                hashMap.put("nameCatMounth", "");
                hashMap.put("maxMounthAmount", Float.valueOf(0));
            }

            else {
                float sum1 = 0;
                float max1 = 0;
                String nameCatMax1 = "";

                while (resSet1.next()) {
                    String string = resSet1.getString("date");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//задаю формат даты
                    Date date = formatter.parse(string);//создаю дату через

                    if (!mounthAgo.after(date) && !now.before(date)) {//все записи за день
                        float temp = resSet1.getFloat("amount");
                        if (max1 <= temp) {
                            max1 = temp;
                            nameCatMax1 = resSet1.getString("nameCat");
                        }
                        sum1 = sum1 + temp;
                    }
                }
                hashMap.put("sumMounth", sum1);
                hashMap.put("nameCatMounth", nameCatMax1);
                hashMap.put("maxMounthAmount", max1);

            }

            Conn.CloseDB();
        } catch (SQLException e) {
            log.error("SQL exeptrion in getStat()! " + e.getLocalizedMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return hashMap;
    }


}
