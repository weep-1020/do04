package cn.md.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatExample {
    /**
     * 将Date对象转换为字符串日期格式
     * 此方法主要用于将系统日期转换为更可读的字符串格式
     * 如果输入的Date对象为空，则返回空值，以避免无效的日期转换尝试
     * 选择使用"yyyy/MM/dd"格式，以便在系统中统一日期的显示和处理方式
     *
     * @param date 待转换的Date对象
     * @return 以"yyyy/MM/dd"格式返回的日期字符串，如果输入为null，则返回null
     */
    public String date(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.format(date);
    }
}
