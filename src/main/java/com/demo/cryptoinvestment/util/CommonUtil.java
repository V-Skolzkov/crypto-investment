package com.demo.cryptoinvestment.util;

import com.demo.cryptoinvestment.common.Pair;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public final class CommonUtil {

    private static final String FILE_FOOTER = "_VALUES.CSV";

    public static Pair<Long, Long> dayToUnixTimestamp(String date) {
        LocalDate from = LocalDate.parse(date);

        Instant instant = from.atStartOfDay(ZoneId.of("UTC")).toInstant();
        long fromTimeInMillis = instant.toEpochMilli();

        LocalDate to = from.plusDays(1);
        instant = to.atStartOfDay(ZoneId.of("UTC")).toInstant();
        long toTimeInMillis = instant.toEpochMilli();

        return Pair.of(fromTimeInMillis, toTimeInMillis);
    }

    public static String getCurrency(String path) {
        File f = new File(path);
        return f.getName().toUpperCase().replace(FILE_FOOTER, "");
    }
}
