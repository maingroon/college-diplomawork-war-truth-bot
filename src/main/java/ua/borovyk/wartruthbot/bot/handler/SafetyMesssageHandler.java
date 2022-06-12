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
class SafetyMesssageHandler {

    ChatService chatService;
    public BotApiMethod<?> handleMessage(Message message) {
        var messageText = message.getText();
        if (messageText.equals(readProperty("safety.button.president.office.name"))) {
            return handlePresidentOffice(message);
        } else if (messageText.equals(readProperty("safety.button.general.staff.name"))) {
            return handleGeneralStaff(message);
        } else if (messageText.equals(readProperty("safety.button.kabmin.name"))) {
            return handleKabmin(message);
        } else if (messageText.equals(readProperty("safety.button.ministry.defence.name"))) {
            return handleMinistryDefence(message);
        } else if (messageText.equals(readProperty("safety.button.national.police.name"))) {
            return handleNationalPolice(message);
        } else if (messageText.equals(readProperty("safety.button.mns.name"))) {
            return handleMns(message);
        } else if (messageText.equals(readProperty("safety.button.territorial.defense.name"))) {
            return handleTerritorialDefence(message);
        } else if (messageText.equals(readProperty("safety.button.back.name"))) {
            return handleBack(message);
        } else {
            return null;
        }
    }

    private BotApiMethod<?> handlePresidentOffice(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("safety.button.president.office.text"),
                KeyboardType.SAFETY
        );
    }

    private BotApiMethod<?> handleGeneralStaff(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("safety.button.general.staff.text"),
                KeyboardType.SAFETY
        );
    }

    private BotApiMethod<?> handleKabmin(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("safety.button.kabmin.text"),
                KeyboardType.SAFETY
        );
    }

    private BotApiMethod<?> handleMinistryDefence(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("safety.button.ministry.defence.text"),
                KeyboardType.SAFETY
        );
    }

    private BotApiMethod<?> handleNationalPolice(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("safety.button.national.police.text"),
                KeyboardType.SAFETY
        );
    }

    private BotApiMethod<?> handleMns(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("safety.button.mns.text"),
                KeyboardType.SAFETY
        );
    }

    private BotApiMethod<?> handleTerritorialDefence(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("safety.button.territorial.defense.text"),
                KeyboardType.SAFETY
        );
    }

    private BotApiMethod<?> handleBack(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("safety.button.back.text"),
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
