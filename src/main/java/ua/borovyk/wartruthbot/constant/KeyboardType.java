package ua.borovyk.wartruthbot.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public enum KeyboardType {

    MAIN,
    QUESTION,
    SETTINGS,

}
