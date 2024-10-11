package com.leyunone.codex.util;

import com.leyunone.codex.model.XxlJobInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author xuxueli 2019-05-21
 */
public class JobScheduleUtil {
    
    private static Logger logger = LoggerFactory.getLogger(JobScheduleUtil.class);
    
    public static final Integer INTERVALTIME = 5000;

    // ---------------------- tools ----------------------
    public static Long generateNextValidTime(String cron) {
        try {
            Date nextValidTime = new CronExpression(cron).getNextValidTimeAfter(new Date(System.currentTimeMillis() + INTERVALTIME));
            return nextValidTime.getTime();
        }catch (Exception e){
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        while (true){
            System.out.println(new Date(generateNextValidTime("0/5 0/1 * * * ?")));
            Thread.sleep(1000);
        }
    }

}
