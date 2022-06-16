package ua.borovyk.wartruthbot.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.borovyk.wartruthbot.bot.TelegramBot;
import ua.borovyk.wartruthbot.constant.MessageType;
import ua.borovyk.wartruthbot.entity.Message;
import ua.borovyk.wartruthbot.repository.MessageRepository;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageService {

    Logger log = LoggerFactory.getLogger(MessageService.class);

    TelegramBot telegramBot;
    MessageRepository messageRepository;

    public void saveMessage(Message message) {
        Objects.requireNonNull(message);
        messageRepository.save(message);
    }

    public Message getQuestionById(Long questionId) {
        Objects.requireNonNull(questionId);
        return messageRepository.findByIdAndType(questionId, MessageType.QUESTION)
                .orElseThrow();
    }

    public void sendQuestionReply(Message reply) {
        Objects.requireNonNull(reply);
        var sendMessage = new SendMessage(reply.getChat().getChatId().toString(), reply.getContent());
        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.info("Failed to send reply message, chatId: {}", reply.getChat().getChatId());
        }
        messageRepository.save(reply);
    }

    public List<Message> listUnreplyedQuestion() {
        return messageRepository.findAllByTypeAndResponderOrderByDateCreatedAsc(MessageType.QUESTION, null);
    }

}
