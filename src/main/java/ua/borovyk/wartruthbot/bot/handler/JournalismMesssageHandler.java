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

import static ua.borovyk.wartruthbot.util.PropertyReader.readProperty;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class JournalismMesssageHandler {

    ChatService chatService;
    public BotApiMethod<?> handleMessage(Message message) {
        var messageText = message.getText();
        if (messageText.equals(readProperty("journalism.button.clickbait.name"))) {
            return handleClickbait(message);
        } else if (messageText.equals(readProperty("journalism.button.markers.name"))) {
            return handleMarkers(message);
        } else if (messageText.equals(readProperty("journalism.button.features.bot.name"))) {
            return handleFeatures(message);
        } else if (messageText.equals(readProperty("journalism.button.correct.lexical.name"))) {
            return handleCorrectLexical(message);
        } else if (messageText.equals(readProperty("journalism.button.recommendation.ethics.name"))) {
            return handleRecommendation(message);
        } else if (messageText.equals(readProperty("journalism.button.educational.materials.name"))) {
            return handleEducation(message);
        } else if (messageText.equals(readProperty("journalism.button.back.name"))) {
            return handleBack(message);
        } else {
            return null;
        }
    }

    private BotApiMethod<?> handleClickbait(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("journalism.button.clickbait.text"),
                KeyboardType.JOURNALISM
        );
    }

    private BotApiMethod<?> handleMarkers(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("journalism.button.markers.text"),
                KeyboardType.JOURNALISM
        );
    }

    private BotApiMethod<?> handleFeatures(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("journalism.button.features.bot.text"),
                KeyboardType.JOURNALISM
        );
    }

    private BotApiMethod<?> handleCorrectLexical(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("journalism.button.correct.lexical.text"),
                KeyboardType.JOURNALISM
        );
    }

    private BotApiMethod<?> handleRecommendation(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("journalism.button.recommendation.ethics.text"),
                KeyboardType.JOURNALISM
        );
    }

    private BotApiMethod<?> handleEducation(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("journalism.button.educational.materials.text"),
                KeyboardType.JOURNALISM
        );
    }

    private BotApiMethod<?> handleBack(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("journalism.button.back.text"),
                KeyboardType.MAIN
        );
    }

    private SendMessage sendMessage(Long chatId, String messageText, KeyboardType keyboardType) {
        chatService.updateChatKeyboardType(chatId, keyboardType);

        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(messageText)
                .replyMarkup(KeyboardHolder.getKeyboardByType(keyboardType))
                .build();
    }

}
