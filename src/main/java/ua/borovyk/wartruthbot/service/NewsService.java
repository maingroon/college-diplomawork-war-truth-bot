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
import ua.borovyk.wartruthbot.constant.ChatStatus;
import ua.borovyk.wartruthbot.entity.News;
import ua.borovyk.wartruthbot.repository.ChatRepository;
import ua.borovyk.wartruthbot.repository.NewsRepository;
import ua.borovyk.wartruthbot.util.LocalDateTimeUtil;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NewsService {

    Logger log = LoggerFactory.getLogger(NewsService.class);

    TelegramBot telegramBot;

    NewsRepository newsRepository;

    ChatRepository chatRepository;

    public int publishDatedNews() {
        var newsForPublish = newsRepository.findAllByPublishedFalseAndPublishDateLessThanEqual(LocalDateTimeUtil.now());
        log.info("Found to publish: {} news", newsForPublish.size());
        return newsForPublish.stream()
                .mapToInt(news -> {
                    var result = publishNews(news);
                    news.setPublished(true);
                    newsRepository.save(news);
                    return result;
                }).sum();
    }

    private int publishNews(News news) {
        var chats = chatRepository.findAllByStatusAndSelectedTopicsAndSelectedRegionsContaining(
                ChatStatus.ACTIVE,
                news.getTopics(),
                news.getRegions()
        );
        return chats.stream()
                .map(chat -> new SendMessage(chat.getChatId().toString(), news.getContent()))
                .mapToInt(message -> {
                    try {
                        telegramBot.execute(message);
                    } catch (TelegramApiException e) {
                        log.error("Failed to publish news with id: {} for chatId: {}", news.getId(), message.getChatId());
                        log.error(e.toString());
                        return 0;
                    }
                    return 1;
                }).sum();
    }

}