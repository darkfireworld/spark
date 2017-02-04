package org.dfw.spark.core.support.kit;

import java.util.Calendar;

/**
 * 日期工具类
 */
public class TimeKit {
    /**
     * 获取两个时间之内的工作日时间（只去掉两个日期之间的周末时间，法定节假日未去掉）
     *
     * @param start 起始时间
     * @param end   结束时间
     * @return 相隔时间差MS
     */
    static public long calcNetWorkDays(long start, long end) {
        //如果起始时间大于结束时间，将二者交换
        if (start > end) {
            long temp = start;
            start = end;
            end = temp;
        }
        //根据参数获取起始时间与结束时间的日历类型对象
        Calendar sdate = Calendar.getInstance();
        Calendar edate = Calendar.getInstance();
        sdate.setTimeInMillis(start);
        edate.setTimeInMillis(end);
        //如果两个时间在同一周并且都不是周末日期，则直接返回时间差，增加执行效率
        if (sdate.get(Calendar.YEAR) == edate.get(Calendar.YEAR)
                && sdate.get(Calendar.WEEK_OF_YEAR) == edate.get(Calendar.WEEK_OF_YEAR)
                && sdate.get(Calendar.DAY_OF_WEEK) != 1 && sdate.get(Calendar.DAY_OF_WEEK) != 7
                && edate.get(Calendar.DAY_OF_WEEK) != 1 && edate.get(Calendar.DAY_OF_WEEK) != 7) {
            return end - start;
        }
        //首先取得起始日期与结束日期的下个周一的日期
        Calendar snextM = getNextMonday(sdate);
        Calendar enextM = getNextMonday(edate);
        //获取这两个周一之间的实际天数
        int days = calcDiffDays(snextM, enextM);
        //获取这两个周一之间的工作日数(两个周一之间的天数肯定能被7整除，并且工作日数量占其中的5/7)
        int workdays = days / 7 * 5;
        //获取开始时间的偏移量
        long scharge = 0;
        if (sdate.get(Calendar.DAY_OF_WEEK) != 1 && sdate.get(Calendar.DAY_OF_WEEK) != 7) {
            //只有在开始时间为非周末的时候才计算偏移量
            scharge += (7 - sdate.get(Calendar.DAY_OF_WEEK)) * 24 * 3600000;
            scharge -= sdate.get(Calendar.HOUR_OF_DAY) * 3600000;
            scharge -= sdate.get(Calendar.MINUTE) * 60000;
            scharge -= sdate.get(Calendar.SECOND) * 1000;
            scharge -= sdate.get(Calendar.MILLISECOND);
        }
        //获取结束时间的偏移量
        long echarge = 0;
        if (edate.get(Calendar.DAY_OF_WEEK) != 1 && edate.get(Calendar.DAY_OF_WEEK) != 7) {
            //只有在结束时间为非周末的时候才计算偏移量
            echarge += (7 - edate.get(Calendar.DAY_OF_WEEK)) * 24 * 3600000;
            echarge -= edate.get(Calendar.HOUR_OF_DAY) * 3600000;
            echarge -= edate.get(Calendar.MINUTE) * 60000;
            echarge -= edate.get(Calendar.SECOND) * 1000;
            echarge -= edate.get(Calendar.MILLISECOND);
        }
        //计算最终结果，具体为：workdays加上开始时间的时间偏移量，减去结束时间的时间偏移量
        return (workdays * 24 * 3600 * 1000L) + (scharge - echarge);
    }

    static private Calendar getNextMonday(Calendar cal) {
        int addnum = 9 - cal.get(Calendar.DAY_OF_WEEK);
        if (addnum == 8) addnum = 1;//周日的情况
        cal.add(Calendar.DATE, addnum);
        return cal;
    }

    /**
     * 获取两个日期之间的实际天数，支持跨年
     */
    static int calcDiffDays(Calendar start, Calendar end) {
        if (start.after(end)) {
            Calendar swap = start;
            start = end;
            end = swap;
        }
        int days = end.get(Calendar.DAY_OF_YEAR) - start.get(Calendar.DAY_OF_YEAR);
        int y2 = end.get(Calendar.YEAR);
        if (start.get(Calendar.YEAR) != y2) {
            start = (Calendar) start.clone();
            do {
                days += start.getActualMaximum(Calendar.DAY_OF_YEAR);
                start.add(Calendar.YEAR, 1);
            } while (start.get(Calendar.YEAR) != y2);
        }
        return days;
    }
}
