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
        if (!keyboards.containsKey(type)) {
            switch (type) {
                case MAIN -> createMainKeyboard();
                case PSYCHOLOGICAL -> createPsychologicalKeyboard();
                case DICTIONARY -> createDictionaryKeyboard();
                case TESTING -> createTestingKeyboard();
                case VIDEO -> createVideoKeyboard();
                case SAFETY -> createSafetyKeyboard();
                case JOURNALISM -> createJournalismKeyboard();
                case DISPLACED -> createDisplacedKeyboard();
                case QUESTION -> createQuestionKeyboard();
                case SETTINGS -> createSettingsKeyboard();
            }
        }
        return keyboards.get(type);
    }

    private static void createMainKeyboard() {
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
        row4.add(readProperty("main.button.question.name"));
        var row5 = new KeyboardRow();
        row5.add(readProperty("main.button.settings.name"));
        row5.add(readProperty("main.button.about.name"));
        var keyboardRows = List.of(row1, row2, row3, row4, row5);

        var mainKeyboard = new ReplyKeyboardMarkup();
        mainKeyboard.setKeyboard(keyboardRows);
        mainKeyboard.setSelective(true);
        mainKeyboard.setResizeKeyboard(true);
        mainKeyboard.setOneTimeKeyboard(false);

        keyboards.put(KeyboardType.MAIN, mainKeyboard);
    }

    private static void createPsychologicalKeyboard() {
        var row1 = new KeyboardRow();
        row1.add(readProperty("psychological.button.child.help.name"));
        var row2 = new KeyboardRow();
        row2.add(readProperty("psychological.button.steps.help.name"));
        var row3 = new KeyboardRow();
        row3.add(readProperty("psychological.button.stress.name"));
        var row4 = new KeyboardRow();
        row4.add(readProperty("psychological.button.together.name"));
        var row5 = new KeyboardRow();
        row5.add(readProperty("psychological.button.back.name"));
        var keyboardRows = List.of(row1, row2, row3, row4, row5);

        var psychologicalKeyboard = new ReplyKeyboardMarkup();
        psychologicalKeyboard.setKeyboard(keyboardRows);
        psychologicalKeyboard.setSelective(true);
        psychologicalKeyboard.setResizeKeyboard(true);
        psychologicalKeyboard.setOneTimeKeyboard(false);

        keyboards.put(KeyboardType.PSYCHOLOGICAL, psychologicalKeyboard);
    }

    private static void createDictionaryKeyboard() {
        var row1 = new KeyboardRow();
        row1.add(readProperty("dictionary.button.infomedia.name"));
        var row2 = new KeyboardRow();
        row2.add(readProperty("dictionary.button.back.name"));
        var keyboardRows = List.of(row1, row2);

        var dictionaryKeyboard = new ReplyKeyboardMarkup();
        dictionaryKeyboard.setKeyboard(keyboardRows);
        dictionaryKeyboard.setSelective(true);
        dictionaryKeyboard.setResizeKeyboard(true);
        dictionaryKeyboard.setOneTimeKeyboard(false);

        keyboards.put(KeyboardType.DICTIONARY, dictionaryKeyboard);
    }

    private static void createTestingKeyboard() {
        var row1 = new KeyboardRow();
        row1.add(readProperty("testing.button.check.infomedia.level.name"));
        var row2 = new KeyboardRow();
        row2.add(readProperty("testing.button.quiz.medialiteracy.name"));
        var row3 = new KeyboardRow();
        row3.add(readProperty("testing.button.journalist.work.name"));
        var row4 = new KeyboardRow();
        row4.add(readProperty("testing.button.lost.literacy.name"));
        var row5 = new KeyboardRow();
        row5.add(readProperty("testing.button.back.name"));
        var keyboardRows = List.of(row1, row2, row3, row4, row5);

        var testingKeyboard = new ReplyKeyboardMarkup();
        testingKeyboard.setKeyboard(keyboardRows);
        testingKeyboard.setSelective(true);
        testingKeyboard.setResizeKeyboard(true);
        testingKeyboard.setOneTimeKeyboard(false);

        keyboards.put(KeyboardType.TESTING, testingKeyboard);
    }

    private static void createVideoKeyboard() {
        var row1 = new KeyboardRow();
        row1.add(readProperty("video.button.lexical.laboratory.name"));
        var row2 = new KeyboardRow();
        row2.add(readProperty("video.button.multimedia.dictionary.name"));
        var row3 = new KeyboardRow();
        row3.add(readProperty("video.button.you.media.name"));
        var row4 = new KeyboardRow();
        row4.add(readProperty("video.button.back.name"));
        var keyboardRows = List.of(row1, row2, row3, row4);

        var videoKeyboard = new ReplyKeyboardMarkup();
        videoKeyboard.setKeyboard(keyboardRows);
        videoKeyboard.setSelective(true);
        videoKeyboard.setResizeKeyboard(true);
        videoKeyboard.setOneTimeKeyboard(false);

        keyboards.put(KeyboardType.VIDEO, videoKeyboard);
    }

    private static void createSafetyKeyboard() {
        var row1 = new KeyboardRow();
        row1.add(readProperty("safety.button.president.office.name"));
        row1.add(readProperty("safety.button.general.staff.name"));
        var row2 = new KeyboardRow();
        row2.add(readProperty("safety.button.kabmin.name"));
        row2.add(readProperty("safety.button.ministry.defence.name"));
        var row3 = new KeyboardRow();
        row3.add(readProperty("safety.button.national.police.name"));
        row3.add(readProperty("safety.button.mns.name"));
        var row4 = new KeyboardRow();
        row4.add(readProperty("safety.button.territorial.defense.name"));
        row4.add(readProperty("safety.button.back.name"));
        var keyboardRows = List.of(row1, row2, row3, row4);

        var safetyKeyboard = new ReplyKeyboardMarkup();
        safetyKeyboard.setKeyboard(keyboardRows);
        safetyKeyboard.setSelective(true);
        safetyKeyboard.setResizeKeyboard(true);
        safetyKeyboard.setOneTimeKeyboard(false);

        keyboards.put(KeyboardType.SAFETY, safetyKeyboard);
    }

    private static void createJournalismKeyboard() {
        var row1 = new KeyboardRow();
        row1.add(readProperty("journalism.button.clickbait.name"));
        row1.add(readProperty("journalism.button.markers.name"));
        var row2 = new KeyboardRow();
        row2.add(readProperty("journalism.button.features.bot.name"));
        row2.add(readProperty("journalism.button.correct.lexical.name"));
        var row3 = new KeyboardRow();
        row3.add(readProperty("journalism.button.recommendation.ethics.name"));
        row3.add(readProperty("journalism.button.educational.materials.name"));
        var row4 = new KeyboardRow();
        row4.add(readProperty("journalism.button.back.name"));
        var keyboardRows = List.of(row1, row2, row3, row4);

        var journalismKeyboard = new ReplyKeyboardMarkup();
        journalismKeyboard.setKeyboard(keyboardRows);
        journalismKeyboard.setSelective(true);
        journalismKeyboard.setResizeKeyboard(true);
        journalismKeyboard.setOneTimeKeyboard(false);

        keyboards.put(KeyboardType.JOURNALISM, journalismKeyboard);
    }

    private static void createDisplacedKeyboard() {
        var row1 = new KeyboardRow();
        row1.add(readProperty("displaced.button.who.get.help.name"));
        var row2 = new KeyboardRow();
        row2.add(readProperty("displaced.button.new.conditions.name"));
        var row3 = new KeyboardRow();
        row3.add(readProperty("displaced.button.support.name"));
        var row4 = new KeyboardRow();
        row4.add(readProperty("displaced.button.issue.payment.name"));
        var row5 = new KeyboardRow();
        row5.add(readProperty("displaced.button.back.name"));
        var keyboardRows = List.of(row1, row2, row3, row4, row5);

        var displacedKeyboard = new ReplyKeyboardMarkup();
        displacedKeyboard.setKeyboard(keyboardRows);
        displacedKeyboard.setSelective(true);
        displacedKeyboard.setResizeKeyboard(true);
        displacedKeyboard.setOneTimeKeyboard(false);

        keyboards.put(KeyboardType.DISPLACED, displacedKeyboard);
    }

    private static void createQuestionKeyboard() {
        var row1 = new KeyboardRow();
        row1.add(readProperty("question.button.back.name"));
        var keyboardRows = List.of(row1);

        var questionKeyboard = new ReplyKeyboardMarkup();
        questionKeyboard.setKeyboard(keyboardRows);
        questionKeyboard.setSelective(true);
        questionKeyboard.setResizeKeyboard(true);
        questionKeyboard.setOneTimeKeyboard(false);

        keyboards.put(KeyboardType.QUESTION, questionKeyboard);
    }

    private static void createSettingsKeyboard() {
        var row1 = new KeyboardRow();
        row1.add(readProperty("settings.button.add.topic.name"));
        row1.add(readProperty("settings.button.add.region.name"));
        var row2 = new KeyboardRow();
        row2.add(readProperty("settings.button.remove.topic.name"));
        row2.add(readProperty("settings.button.remove.region.name"));
        var row3 = new KeyboardRow();
        row3.add(readProperty("settings.button.back.name"));
        var keyboardRows = List.of(row1, row2, row3);

        var informationKeyboard = new ReplyKeyboardMarkup();
        informationKeyboard.setKeyboard(keyboardRows);
        informationKeyboard.setSelective(true);
        informationKeyboard.setResizeKeyboard(true);
        informationKeyboard.setOneTimeKeyboard(false);

        keyboards.put(KeyboardType.SETTINGS, informationKeyboard);
    }

}
