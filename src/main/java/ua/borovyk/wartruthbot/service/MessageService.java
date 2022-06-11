package ua.borovyk.wartruthbot.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ua.borovyk.wartruthbot.constant.MessageType;
import ua.borovyk.wartruthbot.entity.Message;
import ua.borovyk.wartruthbot.repository.MessageRepository;

import java.util.Objects;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageService {

    MessageRepository messageRepository;

    ChatService chatService;

    public void addQuestion(Long chatId, String content) {
        Objects.requireNonNull(chatId);
        Objects.requireNonNull(content);

        var chat = chatService.getChatById(chatId);
        var question = new Message();
        question.setChat(chat);
        question.setContent(content);
        question.setType(MessageType.QUESTION);
        messageRepository.save(question);
    }

}
