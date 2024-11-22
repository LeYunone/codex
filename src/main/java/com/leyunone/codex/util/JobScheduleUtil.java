package com.leyunone.codex.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.*;

/**
 * @author xuxueli 2019-05-21
 */
public class JobScheduleUtil {

    private static Logger logger = LoggerFactory.getLogger(JobScheduleUtil.class);

    public static final Integer INTERVALTIME = 5000;


    // ---------------------- tools ----------------------
    public static Date generateNextValidTime(String cron, Date fromTime) {
        try {
            return new CronExpression(cron).getNextValidTimeAfter(fromTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
