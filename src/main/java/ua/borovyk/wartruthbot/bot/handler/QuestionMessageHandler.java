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
import ua.borovyk.wartruthbot.constant.MessageType;
import ua.borovyk.wartruthbot.repository.MessageRepository;
import ua.borovyk.wartruthbot.service.ChatService;
import ua.borovyk.wartruthbot.service.MessageService;

import static ua.borovyk.wartruthbot.util.PropertyReader.readProperty;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class QuestionMessageHandler {

    ChatService chatService;

    MessageRepository messageRepository;

    public BotApiMethod<?> handleMessage(Message message) {
        var messageText = message.getText();
        if (messageText.equals(readProperty("question.button.back.name"))) {
            return handleBack(message);
        } else {
            return handleQuestion(message);
        }
    }

    private BotApiMethod<?> handleQuestion(Message message) {
        var chat = chatService.getChatById(message.getChatId());
        var question = new ua.borovyk.wartruthbot.entity.Message();
        question.setChat(chat);
        question.setContent(message.getText());
        question.setType(MessageType.QUESTION);
        messageRepository.save(question);

        return sendMessage(
                message.getChatId(),
                readProperty("question.process.text"),
                KeyboardType.QUESTION
        );
    }

    private BotApiMethod<?> handleBack(Message message) {
        return sendMessage(
                message.getChatId(),
                readProperty("question.button.back.text"),
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
