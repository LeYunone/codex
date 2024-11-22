package com.leyunone.codex.util;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/12 11:44
 */
public class TimeUtil {

    public static String monthTime(String yyyyMM, boolean start) {
        // 获取当前时间并将其转换为 Calendar 对象  
        Integer month = Integer.valueOf(yyyyMM.substring(5, 7));
        Integer day = 30;
        if (month == 2) {
            day = 28; // 二月有 28 天  
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            day = 30; // 四月、六月、九月和十二月有 30 天  
        } else {
            day = 31;
        }
        yyyyMM = yyyyMM+"-";
        return start ? yyyyMM + "01" : yyyyMM + day;
    }
}
