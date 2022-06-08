package ua.borovyk.wartruthbot.constant;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public enum ChatStatus {

    ACTIVE,

    STOPPED,

    BLOCKED,

}
