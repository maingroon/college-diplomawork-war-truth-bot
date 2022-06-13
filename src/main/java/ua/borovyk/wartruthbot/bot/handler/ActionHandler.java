package ua.borovyk.wartruthbot.bot.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.borovyk.wartruthbot.bot.keyboard.KeyboardHolder;
import ua.borovyk.wartruthbot.constant.ChatStatus;
import ua.borovyk.wartruthbot.constant.KeyboardType;
import ua.borovyk.wartruthbot.service.ChatService;
import ua.borovyk.wartruthbot.util.PropertyReader;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ActionHandler {

    Logger log = LoggerFactory.getLogger(ActionHandler.class);

    static String JOIN = "member";
    static String LEFT = "kicked";

    ChatService chatService;

    public BotApiMethod<?> handleAction(ChatMemberUpdated chatMember) {
        var status = chatMember.getNewChatMember().getStatus();
        var chatId = chatMember.getChat().getId();
        var chat = chatService.getChatById(chatId);
        log.info("Status: {}", status);

        if (JOIN.equals(status)) {
            chat.setStatus(ChatStatus.ACTIVE);
            chat.setCurrentKeyboard(KeyboardType.MAIN);
            chatService.updateChat(chat);
            return SendMessage.builder()
                    .chatId(chatId.toString())
                    .text(PropertyReader.readProperty("main.greetings.text"))
                    .replyMarkup(KeyboardHolder.getKeyboardByType(KeyboardType.MAIN))
                    .build();
        } else if (LEFT.equals(status)) {
            chat.setStatus(ChatStatus.STOPPED);
            chatService.updateChat(chat);
        }
        return null;
    }

}
