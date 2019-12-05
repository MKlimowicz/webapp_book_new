package app.mapper;


import app.dto.MagazineDto;
import app.model.Magazine;

import java.time.LocalDate;

public class MagazineMapper {


    public static MagazineDto toDto(Magazine magazine) {
        MagazineDto dto = new MagazineDto();
        dto.setId(magazine.getId());
        dto.setPublisher(magazine.getPublisher());
        dto.setTitle(magazine.getTitle());
        dto.setYear(magazine.getYear());
        dto.setLanguage(magazine.getLanguage());

        String convertedDate = convertLocalDateToString(magazine.getTimePublication());
        dto.setTimePublication(convertedDate);
        return dto;
    }

    private static String convertLocalDateToString(LocalDate timePublication) {
        int year = timePublication.getYear();
        int month = timePublication.getMonth().getValue();
        int dayOfMonth = timePublication.getDayOfMonth();
        String date = year+"-"+month+"-"+dayOfMonth;
        return date;
    }

    public static Magazine toEntity(MagazineDto dto) {
        Magazine entity = new Magazine();
        entity.setId(dto.getId());
        entity.setLanguage(dto.getLanguage());
        entity.setPublisher(dto.getPublisher());

        LocalDate localDate = convertStringToLocalDate(dto.getTimePublication());
        entity.setTimePublication(localDate);

        entity.setTitle(dto.getTitle());
        entity.setYear(dto.getYear());
        return entity;
    }

    private static LocalDate convertStringToLocalDate(String dateStr) {
        String[] splitDateStr = dateStr.split("-");
        int year = Integer.valueOf(splitDateStr[0]);
        int month = Integer.valueOf(splitDateStr[1]);
        int day = Integer.valueOf(splitDateStr[2]);
        return LocalDate.of(year, month, day);
    }
}
