package ua.borovyk.wartruthbot.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.borovyk.wartruthbot.bot.TelegramBot;
import ua.borovyk.wartruthbot.constant.ChatStatus;
import ua.borovyk.wartruthbot.entity.News;
import ua.borovyk.wartruthbot.repository.ChatRepository;
import ua.borovyk.wartruthbot.repository.NewsRepository;
import ua.borovyk.wartruthbot.util.LocalDateTimeUtil;

import java.util.List;
import java.util.Objects;

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

    public List<News> listOfSortedNews(Sort.Direction direction, String property) {
        return newsRepository.findAll(Sort.by(direction, property));
    }

    public News getNewsById(Long newsId) {
        Objects.requireNonNull(newsId);
        return newsRepository.findById(newsId)
                .orElseThrow();
    }

    public void saveNews(News news) {
        Objects.requireNonNull(news);
        newsRepository.save(news);
    }

    public void deleteNews(Long newsId) {
        Objects.requireNonNull(newsId);
        newsRepository.deleteById(newsId);
    }

}
