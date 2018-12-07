package com.yh.hr.utils;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;


public class TimeUtils {

    public static long getRemainSecondsOneDay() {
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
        return seconds;
    }

    public static long getRemainSecondsOneHour() {
        LocalDateTime time = LocalDateTime.now();
        long seconds = time.getMinute() * 60 + time.getSecond();
        return 3600 - seconds;
    }

    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(new Date());
        return str;
    }

    public static void main(String[] args) {

        // TODO Auto-generated method stub

        String source="https%3A%---aHR0cHMlM0ElMkYlMkZyMS0tLQ";

        //编码
        Encoder encoder = Base64.getEncoder();
        String encodedStr =  encoder.encodeToString(source.getBytes());
        System.out.println(encodedStr);

        //解码
        Decoder decoder = Base64.getDecoder();
        byte[] decodedByteArr =  decoder.decode(encodedStr);
        String decodedStr = new String(decodedByteArr);
        System.out.println(decodedStr);

    }

}
