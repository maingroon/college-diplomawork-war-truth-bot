package ua.borovyk.wartruthbot.bot.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.borovyk.wartruthbot.bot.keyboard.KeyboardHolder;
import ua.borovyk.wartruthbot.constant.KeyboardType;
import ua.borovyk.wartruthbot.service.ChatService;

import static java.util.stream.Collectors.joining;
import static ua.borovyk.wartruthbot.util.PropertyReader.readProperty;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageHandler {

    ChatService chatService;

    PsychologicalMesssageHandler psychologicalMesssageHandler;

    QuestionMessageHandler questionMessageHandler;

    SettingsMessageHandler settingsMessageHandler;

    public BotApiMethod<?> handleMessage(Message message) {
        var keyboardType = chatService.getChatKeyboardType(message.getChatId());
        return switch (keyboardType) {
            case MAIN -> handleMainKeyboard(message);
            case PSYCHOLOGICAL -> psychologicalMesssageHandler.handleMessage(message);
            case QUESTION -> questionMessageHandler.handleMessage(message);
            case SETTINGS -> settingsMessageHandler.handleMessage(message);
        };
    }

    private BotApiMethod<?> handleMainKeyboard(Message message) {
        var messageText = message.getText();
        if (messageText.equals(readProperty("main.button.psychological.name"))) {
            return handleMainPsychological(message);
        } else if (messageText.equals(readProperty("main.button.dictionary.name"))) {
            return handleMainDictionary(message);
        } else if (messageText.equals(readProperty("main.button.testing.name"))) {
            return handleMainTesting(message);
        } else if (messageText.equals(readProperty("main.button.video.name"))) {
            return handleMainVideo(message);
        } else if (messageText.equals(readProperty("main.button.safety.name"))) {
            return handleMainSafety(message);
        } else if (messageText.equals(readProperty("main.button.journalism.name"))) {
            return handleMainJournalism(message);
        } else if (messageText.equals(readProperty("main.button.displaced.name"))) {
            return handleMainDisplaced(message);
        } else if (messageText.equals(readProperty("main.button.question.name"))) {
            return handleMainQuestion(message);
        } else if (messageText.equals(readProperty("main.button.about.name"))) {
            return handleMainAbout(message);
        } else if (messageText.equals(readProperty("main.button.settings.name"))) {
            return handleMainSettings(message);
        } else {
            return null;
        }
    }

    private BotApiMethod<?> handleMainPsychological(Message message) {
        return sendMessageWithKeyboard(
                message.getChatId(),
                readProperty("main.button.psychological.text"),
                KeyboardType.PSYCHOLOGICAL
        );
    }

    private BotApiMethod<?> handleMainDictionary(Message message) {
        return sendMessageWithKeyboard(
                message.getChatId(),
                readProperty("main.button.dictionary.text"),
                KeyboardType.MAIN
        );
    }

    private BotApiMethod<?> handleMainTesting(Message message) {
        return sendMessageWithKeyboard(
                message.getChatId(),
                readProperty("main.button.testing.text"),
                KeyboardType.MAIN
        );
    }

    private BotApiMethod<?> handleMainVideo(Message message) {
        return sendMessageWithKeyboard(
                message.getChatId(),
                readProperty("main.button.video.text"),
                KeyboardType.MAIN
        );
    }

    private BotApiMethod<?> handleMainSafety(Message message) {
        return sendMessageWithKeyboard(
                message.getChatId(),
                readProperty("main.button.safety.text"),
                KeyboardType.MAIN
        );
    }

    private BotApiMethod<?> handleMainJournalism(Message message) {
        return sendMessageWithKeyboard(
                message.getChatId(),
                readProperty("main.button.journalism.text"),
                KeyboardType.MAIN
        );
    }

    private BotApiMethod<?> handleMainDisplaced(Message message) {
        return sendMessageWithKeyboard(
                message.getChatId(),
                readProperty("main.button.displaced.text"),
                KeyboardType.MAIN
        );
    }

    private BotApiMethod<?> handleMainQuestion(Message message) {
        return sendMessageWithKeyboard(
                message.getChatId(),
                readProperty("main.button.question.text"),
                KeyboardType.QUESTION
        );
    }

    private BotApiMethod<?> handleMainAbout(Message message) {
        return sendMessageWithKeyboard(
                message.getChatId(),
                readProperty("main.button.about.text"),
                KeyboardType.MAIN
        );
    }


    private BotApiMethod<?> handleMainSettings(Message message) {
        var text = readProperty("main.button.settings.text");
        var chatId = message.getChatId();
        var chat = chatService.getChatById(chatId);

        String topics;
        if (chat.getSelectedTopics().isEmpty()) {
            topics = readProperty("main.button.settings.text.topic.selected.not");
        } else {
            var topicsHolder = readProperty("main.button.settings.text.topic.selected");
            var joinedTopics = chat.getSelectedTopics().stream()
                    .map(topic -> topic.value)
                    .collect(joining(", "));
            topics = String.format(topicsHolder, joinedTopics);
        }
        String regions;
        if (chat.getSelectedRegions().isEmpty()) {
            regions = readProperty("main.button.settings.text.region.selected.not");
        } else {
            var regionsHolder = readProperty("main.button.settings.text.region.selected");
            var joinedRegions = chat.getSelectedRegions().stream()
                    .map(region -> region.genitive)
                    .collect(joining(", "));
            regions = String.format(regionsHolder, joinedRegions);
        }
        var formattedText = String.format(text, topics, regions);

        return sendMessageWithKeyboard(
                chatId,
                formattedText,
                KeyboardType.SETTINGS
        );
    }

    private SendMessage sendMessageWithKeyboard(Long chatId, String messageText, KeyboardType keyboardType) {
        chatService.updateChatKeyboardType(chatId, keyboardType);

        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(messageText)
                .replyMarkup(KeyboardHolder.getKeyboardByType(keyboardType))
                .build();
    }

}
