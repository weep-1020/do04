package cn.md.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatExample {
    public String date(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.format(date);
    }
}
