package ua.borovyk.wartruthbot.bot.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.borovyk.wartruthbot.bot.keyboard.KeyboardHolder;
import ua.borovyk.wartruthbot.constant.CallbackType;
import ua.borovyk.wartruthbot.constant.KeyboardType;
import ua.borovyk.wartruthbot.constant.NewsRegion;
import ua.borovyk.wartruthbot.constant.NewsTopic;
import ua.borovyk.wartruthbot.service.ChatService;

import java.text.Collator;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static ua.borovyk.wartruthbot.util.PropertyReader.readProperty;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class SettingsMessageHandler {

    final Collator collator = Collator.getInstance(new Locale("uk", "UA"));

    ChatService chatService;

    public BotApiMethod<?> handleMessage(Message message) {
        var messageText = message.getText();
        if (messageText.equals(readProperty("settings.button.add.topic.name"))) {
            return handleAddTopic(message);
        } else if (messageText.equals(readProperty("settings.button.add.region.name"))) {
            return handleAddRegion(message);
        } else if (messageText.equals(readProperty("settings.button.remove.topic.name"))) {
            return handleRemoveTopic(message);
        } else if (messageText.equals(readProperty("settings.button.remove.region.name"))) {
            return handleRemoveRegion(message);
        } else if (messageText.equals(readProperty("settings.button.back.name"))) {
            return handleBack(message);
        } else {
            return null;
        }
    }

    private BotApiMethod<?> handleAddTopic(Message message) {
        return sendMessageWithInlineKeyboard(
                message.getChatId(),
                readProperty("settings.button.add.topic.text"),
                createAddTopicKeyboard(message.getChatId())
        );
    }

    private BotApiMethod<?> handleAddRegion(Message message) {
        return sendMessageWithInlineKeyboard(
                message.getChatId(),
                readProperty("settings.button.add.region.text"),
                createAddRegionKeyboard(message.getChatId())
        );
    }

    private BotApiMethod<?> handleRemoveTopic(Message message) {
        return sendMessageWithInlineKeyboard(
                message.getChatId(),
                readProperty("settings.button.remove.topic.text"),
                createRemoveTopicKeyboard(message.getChatId())
        );
    }

    private BotApiMethod<?> handleRemoveRegion(Message message) {
        return sendMessageWithInlineKeyboard(
                message.getChatId(),
                readProperty("settings.button.remove.region.text"),
                createRemoveRegionKeyboard(message.getChatId())
        );
    }

    private BotApiMethod<?> handleBack(Message message) {
        return sendMessageWithKeyboard(
                message.getChatId(),
                readProperty("settings.button.back.text"),
                KeyboardType.MAIN
        );
    }

    private InlineKeyboardMarkup createAddTopicKeyboard(Long chatId) {
        var selectedTopics = chatService.getSelectedTopics(chatId);
        Set<NewsTopic> forSelectTopics = Arrays.stream(NewsTopic.values()).collect(Collectors.toSet());
        forSelectTopics.removeAll(selectedTopics);

        var keyboardLines = forSelectTopics.stream()
                .sorted((t1, t2) -> collator.compare(t1.value, t2.value))
                .map(topic -> InlineKeyboardButton.builder()
                        .text(topic.value)
                        .callbackData(CallbackType.ADD_TOPIC.value + topic.name())
                        .build())
                .map(List::of)
                .toList();
        return new InlineKeyboardMarkup(keyboardLines);
    }

    private InlineKeyboardMarkup createAddRegionKeyboard(Long chatId) {
        var selectedRegions = chatService.getSelectedRegions(chatId);
        var forSelectRegions = Arrays.stream(NewsRegion.values()).collect(Collectors.toSet());
        forSelectRegions.removeAll(selectedRegions);

        var keyboardLines = forSelectRegions.stream()
                .sorted((r1, r2) -> collator.compare(r1.genitive, r2.genitive))
                .map(region -> InlineKeyboardButton.builder()
                        .text(region.genitive)
                        .callbackData(CallbackType.ADD_REGION.value + region.name())
                        .build())
                .map(List::of)
                .toList();
        return new InlineKeyboardMarkup(keyboardLines);
    }

    private InlineKeyboardMarkup createRemoveTopicKeyboard(Long chatId) {
        var selectedTopics = chatService.getSelectedTopics(chatId);
        var keyboardLines = selectedTopics.stream()
                .sorted((t1, t2) -> collator.compare(t1.value, t2.value))
                .map(topic -> InlineKeyboardButton.builder()
                        .text(topic.value)
                        .callbackData(CallbackType.REMOVE_TOPIC.value + topic.name())
                        .build())
                .map(List::of)
                .toList();
        return new InlineKeyboardMarkup(keyboardLines);
    }

    private InlineKeyboardMarkup createRemoveRegionKeyboard(Long chatId) {
        var selectedRegions = chatService.getSelectedRegions(chatId);
        var keyboardLines = selectedRegions.stream()
                .sorted((r1, r2) -> collator.compare(r1.genitive, r2.genitive))
                .map(region -> InlineKeyboardButton.builder()
                        .text(region.genitive)
                        .callbackData(CallbackType.REMOVE_REGION.value + region.name())
                        .build())
                .map(List::of)
                .toList();
        return new InlineKeyboardMarkup(keyboardLines);
    }

    private SendMessage sendMessageWithKeyboard(Long chatId, String messageText, KeyboardType keyboardType) {
        chatService.updateChatKeyboardType(chatId, keyboardType);

        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(messageText)
                .replyMarkup(KeyboardHolder.getKeyboardByType(keyboardType))
                .build();
    }

    private SendMessage sendMessageWithInlineKeyboard(Long chatId, String messageText, InlineKeyboardMarkup keyboard) {
        return SendMessage.builder()
                .chatId(chatId.toString())
                .text(messageText)
                .replyMarkup(keyboard)
                .build();
    }

}
