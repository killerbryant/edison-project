package com.edison.demo.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

	public static Timestamp getNow() {
		Long datetime = Calendar.getInstance().getTimeInMillis();
        Timestamp timestamp = new Timestamp(datetime);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tsStr = sdf.format(timestamp);
        timestamp = Timestamp.valueOf(tsStr);
        return timestamp;
	}
}
