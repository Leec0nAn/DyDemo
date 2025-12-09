package com.example.dydemo.until;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NumberFormatUtil {
    public static String formatCountCn(int count) {
        if (count < 10000) {
            return String.valueOf(count);
        }
        if (count % 10000 == 0) {
            return (count / 10000) + "万";
        }
        int times10 = count / 1000; // 截断到一位小数（万的十分之一）
        int intPart = times10 / 10;
        int frac = times10 % 10;
        if (frac == 0) return intPart + "万";
        return intPart + "." + frac + "万";
    }

    public static String formatRelativeTime(long timestampMillis) {
        long now = System.currentTimeMillis();
        long diff = Math.max(0, now - timestampMillis);
        long minute = 60_000L;
        long hour = 60 * minute;
        long day = 24 * hour;
        if (diff < minute) return "刚刚";
        if (diff < hour) return (diff / minute) + "分钟前";
        if (diff < day) return (diff / hour) + "小时前";
        if (diff < 2 * day) return "昨天";
        return new SimpleDateFormat("yyyy.MM.dd").format(new Date(timestampMillis));
    }

    public static String ipToRegion(String ip) {
        if (ip == null || ip.isEmpty()) return "未知";
        try {
            String[] parts = ip.split("\\.");
            if (parts.length < 2) return "未知";
            int p1 = Integer.parseInt(parts[0]);
            int p2 = Integer.parseInt(parts[1]);
            if (p1 == 183) return "广东";
            if (p1 == 106) return "浙江";
            if (p1 == 101) return "上海";
            if (p1 == 171) return "四川";
            if (p1 == 111) return "北京";
            if (p1 == 123) return "北京";
            if (p1 == 120) return "天津";
            if (p1 == 113) return "广东";
            if (p1 == 58) return "北京";
            if (p1 == 39) return "北京";
            return "中国";
        } catch (Throwable t) {
            return "未知";
        }
    }
}
