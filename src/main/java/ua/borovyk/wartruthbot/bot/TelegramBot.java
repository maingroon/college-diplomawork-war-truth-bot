package ua.borovyk.wartruthbot.bot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;
import ua.borovyk.wartruthbot.bot.handler.ActionHandler;
import ua.borovyk.wartruthbot.bot.handler.CallbackHandler;
import ua.borovyk.wartruthbot.bot.handler.MessageHandler;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TelegramBot extends SpringWebhookBot {

    Logger log = LoggerFactory.getLogger(TelegramBot.class);

    @Getter
    @NonFinal
    @Value("${telegram.bot.username}")
    String botUsername;

    @Getter
    @NonFinal
    @Value("${telegram.bot.token}")
    String botToken;

    @Getter
    @NonFinal
    @Value("${telegram.bot.path}")
    String botPath;

    ActionHandler actionHandler;

    CallbackHandler callbackHandler;

    MessageHandler messageHandler;

    public TelegramBot(SetWebhook setWebhook,
                       ActionHandler actionHandler,
                       CallbackHandler callbackHandler,
                       MessageHandler messageHandler) {
        super(setWebhook);
        this.actionHandler = actionHandler;
        this.callbackHandler = callbackHandler;
        this.messageHandler = messageHandler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return handleUpdate(update);
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    private BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasMyChatMember()) {
            return actionHandler.handleAction(update.getMyChatMember());
        } else if (update.hasCallbackQuery()) {
            return callbackHandler.handleCallback(update.getCallbackQuery());
        } else if (update.hasMessage()) {
            return messageHandler.handleMessage(update.getMessage());
        }
        return null;
    }

}
