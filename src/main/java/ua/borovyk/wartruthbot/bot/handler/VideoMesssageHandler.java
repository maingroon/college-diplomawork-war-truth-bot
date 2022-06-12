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
class VideoMesssageHandler {

    ChatService chatService;
    public BotApiMethod<?> handleMessage(Message message) {
        var messageText = message.getText();
        if (messageText.equals(readProperty("video.button.lexical.laboratory.name"))) {
            return handleLexicalLaboratory(message);
        } else if (messageText.equals(readProperty("video.button.multimedia.dictionary.name"))) {
            return handleMultimediaDictionary(message);
        } else if (messageText.equals(readProperty("video.button.you.media.name"))) {
            return handleYouMedia(message);
        } else if (messageText.equals(readProperty("video.button.back.name"))) {
            return handleBack(message);
        } else {
            return null;
        }
    }

    private BotApiMethod<?> handleLexicalLaboratory(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("video.button.lexical.laboratory.text"),
                KeyboardType.VIDEO
        );
    }

    private BotApiMethod<?> handleMultimediaDictionary(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("video.button.multimedia.dictionary.text"),
                KeyboardType.VIDEO
        );
    }

    private BotApiMethod<?> handleYouMedia(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("video.button.you.media.text"),
                KeyboardType.VIDEO
        );
    }

    private BotApiMethod<?> handleBack(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("video.button.back.text"),
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
