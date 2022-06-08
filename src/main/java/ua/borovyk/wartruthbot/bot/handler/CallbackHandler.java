package ua.borovyk.wartruthbot.bot.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ua.borovyk.wartruthbot.constant.CallbackType;
import ua.borovyk.wartruthbot.constant.NewsRegion;
import ua.borovyk.wartruthbot.constant.NewsTopic;
import ua.borovyk.wartruthbot.service.ChatService;
import ua.borovyk.wartruthbot.util.PropertyReader;

import java.util.Objects;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CallbackHandler {

    Logger log = LoggerFactory.getLogger(CallbackHandler.class);

    ChatService chatService;

    public BotApiMethod<?> handleCallback(CallbackQuery callback) {
        var callbackData = callback.getData();
        var chatId = callback.getFrom().getId();
        Objects.requireNonNull(chatId);
        Objects.requireNonNull(callbackData);

        if (callbackData.startsWith(CallbackType.ADD_TOPIC.value)) {
            var topic = NewsTopic.valueOf(getInfoFromCallbackData(CallbackType.ADD_TOPIC, callbackData));
            return addTopic(topic, chatId);
        } else if (callbackData.startsWith(CallbackType.ADD_REGION.value)) {
            var region = NewsRegion.valueOf(getInfoFromCallbackData(CallbackType.ADD_REGION, callbackData));
            return addRegion(region, chatId);
        } else if (callbackData.startsWith(CallbackType.REMOVE_TOPIC.value)) {
            var topic = NewsTopic.valueOf(getInfoFromCallbackData(CallbackType.REMOVE_TOPIC, callbackData));
            return removeTopic(topic, chatId);
        } else if (callbackData.startsWith(CallbackType.REMOVE_REGION.value)) {
            var region = NewsRegion.valueOf(getInfoFromCallbackData(CallbackType.REMOVE_REGION, callbackData));
            return removeRegion(region, chatId);
        }
        return null;
    }

    private BotApiMethod<?> addTopic(NewsTopic topic, Long chatId) {
        var chat = chatService.getChatById(chatId);
        var selectedTopics = chat.getSelectedTopics();

        if (selectedTopics.contains(topic)) {
            var text = PropertyReader.readProperty("settings.process.add.topic.exists");
            var formattedText = String.format(text, topic.value);
            return new SendMessage(chatId.toString(), formattedText);
        }

        selectedTopics.add(topic);
        chat.setSelectedTopics(selectedTopics);
        chatService.updateChat(chat);

        return new SendMessage(chatId.toString(), PropertyReader.readProperty("settings.process.add.topic.added"));
    }

    private BotApiMethod<?> addRegion(NewsRegion region, Long chatId) {
        var chat = chatService.getChatById(chatId);
        var selectedRegions = chat.getSelectedRegions();

        if (selectedRegions.contains(region)) {
            var text = PropertyReader.readProperty("settings.process.add.region.exists");
            var formattedText = String.format(text, region.genitive);
            return new SendMessage(chatId.toString(), formattedText);
        }

        selectedRegions.add(region);
        chat.setSelectedRegions(selectedRegions);
        chatService.updateChat(chat);

        return new SendMessage(chatId.toString(), PropertyReader.readProperty("settings.process.add.region.added"));
    }

    private BotApiMethod<?> removeTopic(NewsTopic topic, Long chatId) {
        var chat = chatService.getChatById(chatId);
        var selectedTopics = chat.getSelectedTopics();

        if (!selectedTopics.contains(topic)) {
            var text = PropertyReader.readProperty("settings.process.add.topic.exists.not");
            var formattedText = String.format(text, topic.value);
            return new SendMessage(chatId.toString(), formattedText);
        }

        selectedTopics.remove(topic);
        chat.setSelectedTopics(selectedTopics);
        chatService.updateChat(chat);

        return new SendMessage(chatId.toString(), PropertyReader.readProperty("settings.process.add.topic.removed"));
    }

    private BotApiMethod<?> removeRegion(NewsRegion region, Long chatId) {
        var chat = chatService.getChatById(chatId);
        var selectedRegions = chat.getSelectedRegions();

        if (!selectedRegions.contains(region)) {
            var text = PropertyReader.readProperty("settings.process.add.region.exists.not");
            var formattedText = String.format(text, region.genitive);
            return new SendMessage(chatId.toString(), formattedText);
        }

        selectedRegions.remove(region);
        chat.setSelectedRegions(selectedRegions);
        chatService.updateChat(chat);

        return new SendMessage(chatId.toString(), PropertyReader.readProperty("settings.process.add.region.removed"));
    }

    private String getInfoFromCallbackData(CallbackType callbackType, String callbackData) {
        Objects.requireNonNull(callbackType);
        return callbackData.substring(callbackType.value.length());
    }

}
