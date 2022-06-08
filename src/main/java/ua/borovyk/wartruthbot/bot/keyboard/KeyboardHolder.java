package ua.borovyk.wartruthbot.bot.keyboard;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ua.borovyk.wartruthbot.constant.KeyboardType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ua.borovyk.wartruthbot.util.PropertyReader.readProperty;

@UtilityClass
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KeyboardHolder {

    static final Map<KeyboardType, ReplyKeyboardMarkup> keyboards = new HashMap<>();

    public static ReplyKeyboardMarkup getKeyboardByType(KeyboardType type) {
        return switch (type) {
            case MAIN -> getMainKeyboard();
            case SETTINGS -> getSettingsKeyboard();
        };
    }

    private static ReplyKeyboardMarkup getMainKeyboard() {
        if (!keyboards.containsKey(KeyboardType.MAIN)) {
            var row1 = new KeyboardRow();
            row1.add(readProperty("main.button.psychological.name"));
            row1.add(readProperty("main.button.dictionary.name"));
            var row2 = new KeyboardRow();
            row2.add(readProperty("main.button.testing.name"));
            row2.add(readProperty("main.button.video.name"));
            var row3 = new KeyboardRow();
            row3.add(readProperty("main.button.safety.name"));
            row3.add(readProperty("main.button.journalism.name"));
            var row4 = new KeyboardRow();
            row4.add(readProperty("main.button.displaced.name"));
            row4.add(readProperty("main.button.ato.name"));
            var row5 = new KeyboardRow();
            row5.add(readProperty("main.button.about.name"));
            row5.add(readProperty("main.button.settings.name"));
            var keyboardRows = List.of(row1, row2, row3, row4, row5);

            var mainKeyboard = new ReplyKeyboardMarkup();
            mainKeyboard.setKeyboard(keyboardRows);
            mainKeyboard.setSelective(true);
            mainKeyboard.setResizeKeyboard(true);
            mainKeyboard.setOneTimeKeyboard(false);

            keyboards.put(KeyboardType.MAIN, mainKeyboard);
        }

        return keyboards.get(KeyboardType.MAIN);
    }

    private static ReplyKeyboardMarkup getSettingsKeyboard() {
        if (!keyboards.containsKey(KeyboardType.SETTINGS)) {
            var row1 = new KeyboardRow();
            row1.add(readProperty("settings.button.add.topic.name"));
            row1.add(readProperty("settings.button.add.region.name"));
            var row2 = new KeyboardRow();
            row2.add(readProperty("settings.button.remove.topic.name"));
            row2.add(readProperty("settings.button.remove.region.name"));
            var row3 = new KeyboardRow();
            row3.add(readProperty("settings.button.back.name"));
            var keyboardRows = List.of(row1, row2, row3);

            var mainInformationKeyboard = new ReplyKeyboardMarkup();
            mainInformationKeyboard.setKeyboard(keyboardRows);
            mainInformationKeyboard.setSelective(true);
            mainInformationKeyboard.setResizeKeyboard(true);
            mainInformationKeyboard.setOneTimeKeyboard(false);

            keyboards.put(KeyboardType.SETTINGS, mainInformationKeyboard);
        }

        return keyboards.get(KeyboardType.SETTINGS);
    }

}
