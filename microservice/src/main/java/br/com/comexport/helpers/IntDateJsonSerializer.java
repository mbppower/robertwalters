package br.com.comexport.helpers;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Converts a LocalDate to Integer representing a Date with the format yyyyMMdd
 */
public class IntDateJsonSerializer extends StdConverter<LocalDate, Integer> {
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public Integer convert(LocalDate value) {
        return Integer.parseInt(value.format(DATE_FORMATTER));
    }
}