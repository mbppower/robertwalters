package br.com.comexport.helpers;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.LocalDate;

/**
 * Converts a Integer representing a Date with the format yyyyMMdd into a LocalDate
 */
public class IntDateJsonDeserializer extends StdConverter<Integer, LocalDate> {

    @Override
    public LocalDate convert(Integer value) {
        return LocalDate.parse(value.toString(), IntDateJsonSerializer.DATE_FORMATTER);
    }
}


