package vtp.crm.common.vo.converter;

import com.naharoo.commons.mapstruct.UnidirectionalMapper;
import org.mapstruct.Named;
import vtp.crm.common.utils.Translator;
import vtp.crm.common.utils.common.BooleanUtils;
import vtp.crm.common.utils.common.DateTimeUtils;

import java.text.ParseException;
import java.util.Date;

public interface CommonUnidirectionalMapper<S, D> extends UnidirectionalMapper<S, D> {

    @Named("stringToBoolean")
    default Boolean stringToBoolean(String s) {
        return BooleanUtils.toBooleanObject(s);
    }

    @Named("booleanToString")
    default String stringToBoolean(Boolean b) {
        return BooleanUtils.toCharacterObject(b);
    }

    @Named("translateMessage")
    default String translateMessage(String msg) {
        return Translator.toLocale(msg);
    }

    @Named("dateToTimeString")
    default String dateToTimeString(Date d) {
        return DateTimeUtils.formatDateTime(d);
    }

    @Named("parseDate")
    default Date parseDate(String dateStr) throws ParseException {
        return DateTimeUtils.parseDateForMobileApp(dateStr);
    }

    @Named("parseDateTime")
    default Date parseDateTime(String dateTimeStr) throws ParseException {
        return DateTimeUtils.parseDateTimeForMobileApp(dateTimeStr);
    }

    @Named("formatDate")
    default String formatDate(Date date) {
        return DateTimeUtils.formatDateForMobileApp(date);
    }

    @Named("formatDateTime")
    default String formatDateTime(Date date) {
        return DateTimeUtils.formatDateTimeForMobileApp(date);
    }

}
