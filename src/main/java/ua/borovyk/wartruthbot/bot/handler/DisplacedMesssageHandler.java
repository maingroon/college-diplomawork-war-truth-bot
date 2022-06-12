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
class DisplacedMesssageHandler {

    ChatService chatService;
    public BotApiMethod<?> handleMessage(Message message) {
        var messageText = message.getText();
        if (messageText.equals(readProperty("displaced.button.where.get.help.name"))) {
            return handleWhereGetHelp(message);
        } else if (messageText.equals(readProperty("displaced.button.who.get.help.name"))) {
            return handleWhoGetHelp(message);
        } else if (messageText.equals(readProperty("displaced.button.new.conditions.name"))) {
            return handleNewCondition(message);
        } else if (messageText.equals(readProperty("displaced.button.support.name"))) {
            return handleSupport(message);
        } else if (messageText.equals(readProperty("displaced.button.issue.payment.name"))) {
            return handleIssuePayment(message);
        } else if (messageText.equals(readProperty("displaced.button.back.name"))) {
            return handleBack(message);
        } else {
            return null;
        }
    }

    private BotApiMethod<?> handleWhereGetHelp(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("displaced.button.where.get.help.text"),
                KeyboardType.DISPLACED
        );
    }

    private BotApiMethod<?> handleWhoGetHelp(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("displaced.button.who.get.help.text"),
                KeyboardType.DISPLACED
        );
    }

    private BotApiMethod<?> handleNewCondition(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("displaced.button.new.conditions.text"),
                KeyboardType.DISPLACED
        );
    }

    private BotApiMethod<?> handleSupport(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("displaced.button.support.text"),
                KeyboardType.DISPLACED
        );
    }

    private BotApiMethod<?> handleIssuePayment(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("displaced.button.issue.payment.text"),
                KeyboardType.DISPLACED
        );
    }

    private BotApiMethod<?> handleBack(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("displaced.button.back.text"),
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
