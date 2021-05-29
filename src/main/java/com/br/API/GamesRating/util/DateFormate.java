package com.br.API.GamesRating.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateFormate {

    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    private DateFormate() {}

    public static String convertDate(Date date) {
        if (date != null) {
            var formatter = new SimpleDateFormat(DATE_FORMAT);
            try {
                return (formatter.format(date));
            } catch (Exception e) {
                return "Erro na conversão da data: " + e.getLocalizedMessage();
            }
        }
        return "";
    }

}
