package ua.borovyk.wartruthbot.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ua.borovyk.wartruthbot.constant.KeyboardType;
import ua.borovyk.wartruthbot.constant.NewsRegion;
import ua.borovyk.wartruthbot.constant.NewsTopic;
import ua.borovyk.wartruthbot.entity.Chat;
import ua.borovyk.wartruthbot.repository.ChatRepository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatService {

    ChatRepository chatRepository;

    public Chat getChatById(Long chatId) {
        Objects.requireNonNull(chatId);
        return chatRepository.findByChatId(chatId)
                .orElseGet(Chat::new);
    }

    public KeyboardType getChatKeyboardType(Long chatId) {
        Objects.requireNonNull(chatId);
        var optChat = chatRepository.findByChatId(chatId);

        return optChat.map(Chat::getCurrentKeyboard)
                .orElse(KeyboardType.MAIN);
    }

    public void updateChat(Chat chat) {
        Objects.requireNonNull(chat);
        chatRepository.save(chat);
    }

    public void updateChatKeyboardType(Long chatId, KeyboardType keyboardType) {
        Objects.requireNonNull(chatId);
        Objects.requireNonNull(keyboardType);

        var chat = getChatById(chatId);
        chat.setChatId(chatId);
        chat.setCurrentKeyboard(keyboardType);
        chatRepository.save(chat);
    }

    public Set<NewsTopic> getSelectedTopics(Long chatId) {
        Objects.requireNonNull(chatId);
        return chatRepository.findByChatId(chatId)
                .map(Chat::getSelectedTopics)
                .orElseGet(HashSet::new);
    }

    public Set<NewsRegion> getSelectedRegions(Long chatId) {
        Objects.requireNonNull(chatId);
        return chatRepository.findByChatId(chatId)
                .map(Chat::getSelectedRegions)
                .orElseGet(HashSet::new);
    }

}
