package ua.borovyk.wartruthbot.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public enum NewsTopic {
    ALERT("загроза безпеки"),
    MIGRANTS("переселенці"),
    INFORMATION("загальна інформація");

    String value;

}
