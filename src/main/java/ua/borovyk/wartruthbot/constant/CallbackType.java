package ua.borovyk.wartruthbot.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public enum CallbackType {

    ADD_TOPIC("add_topic_"),
    ADD_REGION("add_region_"),
    REMOVE_TOPIC("remove_topic_"),
    REMOVE_REGION("remove_region_");

    String value;

}
