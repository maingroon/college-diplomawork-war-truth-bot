package ua.borovyk.wartruthbot.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public enum NewsRegion {

    CK("Черкаси", "Черкаська"),
    CR("Автономна Республіка Крим", "Автономна Республіка Крим"),
    CH("Чернігів", "Чернігівська"),
    CV("Чернівці", "Чернівецька"),
    DP("Дніпропетровськ", "Дніпропетровська"),
    DT("Донецьк", "Донецька"),
    IF("Івано-Франківськ", "Івано-Франківська"),
    KK("Харків", "Харківська"),
    KS("Херсон", "Херсонська"),
    KM("Хмельницьк", "Хмельницька"),
    KV("Київ", "Київська"),
    KH("Кропивницький", "Кіровоградська"),
    LH("Луганськ", "Луганська"),
    LV("Львів", "Львівська"),
    MY("Миколаїв", "Миколаївська"),
    OD("Одеса", "Одеська"),
    PL("Полтава", "Полтавська"),
    RV("Рівне", "Рівненська"),
    SM("Суми", "Сумська"),
    TP("Тернопіль", "Тернопільська"),
    VI("Вінниця", "Вінницька"),
    VO("Луцьк", "Волинська"),
    ZK("Закарпаття", "Закарпатська"),
    ZP("Запоріжжя", "Запорізька"),
    ZT("Житомир", "Житомирська");

    String center;
    String genitive;

}
