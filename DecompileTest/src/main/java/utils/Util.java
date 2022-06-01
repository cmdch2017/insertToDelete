package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static final String DATA_PATTERN = "yyyy-MM-dd HH:mm:ss";
    //单位
    public static String parseSize(long size){
        String[] unit = {"B","KB","MB","GB","PB","TB"};
        int index = 0;
        while(size > 1024 && index < unit.length - 1){
            size/=1024;
            index++;
        }
        return size+unit[index];
    }

    /**
     * 解析为中文时间
     * @param lastModified
     * @return
     */
    public static String parseDate(Date lastModified){
        return new SimpleDateFormat(DATA_PATTERN).format(lastModified);
    }

    public static void main(String[] args) {
        System.out.println(parseSize(100000000000000l));
        System.out.println(parseDate(new Date()));
    }
}
