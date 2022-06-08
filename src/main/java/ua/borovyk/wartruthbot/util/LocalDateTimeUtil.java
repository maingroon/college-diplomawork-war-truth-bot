package ua.borovyk.wartruthbot.util;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZoneId;

@UtilityClass
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocalDateTimeUtil {

    static ZoneId KYIV_ZONE = ZoneId.of("Europe/Kiev");

    public static LocalDateTime now() {
        return LocalDateTime.now(KYIV_ZONE);
    }

}
