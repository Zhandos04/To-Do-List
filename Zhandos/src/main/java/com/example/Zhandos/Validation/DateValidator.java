package com.example.Zhandos.Validation;

import com.example.Zhandos.API.Holiday;
import com.example.Zhandos.API.Holidays;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DateValidator {


    // Предопределенные праздники
    private List<LocalDate> holiday = Holidays.holiday();

    // Метод для проверки, является ли дата выходным или праздничным днем
    public boolean isHolidayOrWeekend(LocalDate date) {
        return isWeekend(date) || isHoliday(date);
    }

    // Проверка на выходной день (суббота или воскресенье)
    private boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    // Проверка на праздник
    private boolean isHoliday(LocalDate date) {
        return holiday.contains(date);
    }

    // Поиск ближайшего доступного рабочего дня
    public LocalDate getNextAvailableDate(LocalDate date) {
        LocalDate nextDate = date.plusDays(1);
        while (isHolidayOrWeekend(nextDate)) {
            nextDate = nextDate.plusDays(1);
        }
        return nextDate;
    }
}
