package com.telegramBot.dbWork;

import com.telegramBot.dbWork.heap.HeapWork;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class heapTest {
    private static final Logger log = Logger.getLogger(HeapWork.class);
    @Test
    public  void getStat()
    {
        //Date now = new Date();

        Calendar time = Calendar.getInstance();
        time.add(Calendar.MILLISECOND, -time.getTimeZone().getOffset(time.getTimeInMillis()));
        Date now = time.getTime();

        System.out.println(now);
        Date yesterday = Date.from(now.toInstant().minusSeconds(86400));
        System.out.println(yesterday);

        Long chatId = Long.valueOf(687187915);
        HashMap hashMap = new HashMap();
        log.info("Executing select statmt for getBudget for users table...");

        try {
            Conn.connect();
            ResultSet resSet = null;
            resSet = Conn.statmt.executeQuery(
                    "SELECT * FROM heap WHERE chatId = " + chatId + ";");


            if (resSet.isClosed()) {
                log.warn("result set is empty, thats odd");
                return;
            }

            while (resSet.next()) {
                String string = resSet.getString("date");
               // System.out.println(string);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//задаю формат даты
                Date date = formatter.parse(string);//создаю дату через


                if (!yesterday.after(date) && !now.before(date)) {
                    System.out.println("wat " + date);



                }
            }




            Conn.CloseDB();
        } catch (SQLException e) {
            log.error("SQL exeptrion in getStat()! " + e.getLocalizedMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return;
    }

}
