package com.navec.utils;

import java.sql.Timestamp;

public class TimestampUtils {

    private TimestampUtils() {

    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp getTimestampIn24Hrs() {
        int next24hr = 1000 * 60 * 60 * 24;
        return new Timestamp(System.currentTimeMillis() + next24hr);
    }
}
