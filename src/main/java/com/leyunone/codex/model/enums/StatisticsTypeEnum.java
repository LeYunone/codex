package com.leyunone.codex.model.enums;

import org.apache.commons.lang3.time.DateUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

public enum StatisticsTypeEnum implements StatisticsDateConvert {
    /**
     * 请求参数转换
     */
    STATISTICS_90_DAYS(0){
        @Override
        public String getStatisticsDate() {
            Date dayDate = DateUtils.addDays(new Date(), -89);
                SimpleDateFormat dayFormat = new SimpleDateFormat("YYYY-MM-dd");
            return dayFormat.format(dayDate);
        }
    },

    /**
     * 响应参数转换
     */
    STATISTICS_12_MONTHS(1){
        @Override
        public String getStatisticsDate() {
            Date monthDate = DateUtils.addMonths(new Date(), -11);
            SimpleDateFormat monthFormat = new SimpleDateFormat("YYYY-MM-dd");
            return monthFormat.format(monthDate);
        }
    };

    private Integer type;


    StatisticsTypeEnum(int type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public static StatisticsTypeEnum getStatisticsType(Integer type){
        if(null == type){
            return null;
        }
        for(StatisticsTypeEnum statisticsTypeEnum : StatisticsTypeEnum.values()){
            if(statisticsTypeEnum.getType().equals(type)){
                return statisticsTypeEnum;
            }
        }
        throw new IllegalArgumentException("illegal type");
    }

}
