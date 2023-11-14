package vtp.crm.common.vo.converter;

import com.naharoo.commons.mapstruct.BidirectionalMapper;
import org.mapstruct.Named;
import vtp.crm.common.utils.Translator;
import vtp.crm.common.utils.common.BooleanUtils;
import vtp.crm.common.utils.common.DateTimeUtils;

import java.util.Date;

public interface CommonMapper<S, D> extends BidirectionalMapper<S, D> {

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
}
