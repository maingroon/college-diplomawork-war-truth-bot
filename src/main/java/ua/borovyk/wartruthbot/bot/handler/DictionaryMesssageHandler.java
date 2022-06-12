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
class DictionaryMesssageHandler {

    ChatService chatService;
    public BotApiMethod<?> handleMessage(Message message) {
        var messageText = message.getText();
        if (messageText.equals(readProperty("dictionary.button.infomedia.name"))) {
            return handleInfomedia(message);
        } else if (messageText.equals(readProperty("dictionary.button.back.name"))) {
            return handleBack(message);
        } else {
            return null;
        }
    }

    private BotApiMethod<?> handleInfomedia(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("dictionary.button.infomedia.text"),
                KeyboardType.DICTIONARY
        );
    }

    private BotApiMethod<?> handleBack(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("dictionary.button.back.text"),
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
