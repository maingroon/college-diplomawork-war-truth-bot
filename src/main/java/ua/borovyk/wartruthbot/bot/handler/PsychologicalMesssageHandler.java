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
class PsychologicalMesssageHandler {

    ChatService chatService;
    public BotApiMethod<?> handleMessage(Message message) {
        var messageText = message.getText();
        if (messageText.equals(readProperty("psychological.button.child.help.name"))) {
            return handleChildHelp(message);
        } else if (messageText.equals(readProperty("psychological.button.steps.help.name"))) {
            return handleStepsHelp(message);
        } else if (messageText.equals(readProperty("psychological.button.stress.name"))) {
            return handleStress(message);
        } else if (messageText.equals(readProperty("psychological.button.together.name"))) {
            return handleTogether(message);
        } else if (messageText.equals(readProperty("psychological.button.back.name"))) {
            return handleBack(message);
        } else {
            return null;
        }
    }

    private BotApiMethod<?> handleChildHelp(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("psychological.button.child.help.text"),
                KeyboardType.PSYCHOLOGICAL
        );
    }

    private BotApiMethod<?> handleStepsHelp(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("psychological.button.steps.help.text"),
                KeyboardType.PSYCHOLOGICAL
        );
    }

    private BotApiMethod<?> handleStress(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("psychological.button.stress.text"),
                KeyboardType.PSYCHOLOGICAL
        );
    }

    private BotApiMethod<?> handleTogether(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("psychological.button.together.text"),
                KeyboardType.PSYCHOLOGICAL
        );
    }

    private BotApiMethod<?> handleBack(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("psychological.button.back.text"),
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
