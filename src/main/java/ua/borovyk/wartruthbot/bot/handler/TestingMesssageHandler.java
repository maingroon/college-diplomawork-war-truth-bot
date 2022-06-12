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
class TestingMesssageHandler {

    ChatService chatService;
    public BotApiMethod<?> handleMessage(Message message) {
        var messageText = message.getText();
        if (messageText.equals(readProperty("testing.button.check.infomedia.level.name"))) {
            return handleCheckInfomedia(message);
        } else if (messageText.equals(readProperty("testing.button.quiz.medialiteracy.name"))) {
            return handleMedialiteracy(message);
        } else if (messageText.equals(readProperty("testing.button.journalist.work.name"))) {
            return handleJournalistWork(message);
        } else if (messageText.equals(readProperty("testing.button.lost.literacy.name"))) {
            return handleLostLiteracy(message);
        } else if (messageText.equals(readProperty("testing.button.back.name"))) {
            return handleBack(message);
        } else {
            return null;
        }
    }

    private BotApiMethod<?> handleCheckInfomedia(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("testing.button.check.infomedia.level.text"),
                KeyboardType.TESTING
        );
    }

    private BotApiMethod<?> handleMedialiteracy(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("testing.button.quiz.medialiteracy.text"),
                KeyboardType.TESTING
        );
    }

    private BotApiMethod<?> handleJournalistWork(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("testing.button.journalist.work.text"),
                KeyboardType.TESTING
        );
    }

    private BotApiMethod<?> handleLostLiteracy(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("testing.button.lost.literacy.text"),
                KeyboardType.TESTING
        );
    }

    private BotApiMethod<?> handleBack(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("testing.button.back.text"),
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
