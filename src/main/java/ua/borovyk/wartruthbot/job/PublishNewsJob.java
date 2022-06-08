package ua.borovyk.wartruthbot.job;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.borovyk.wartruthbot.service.NewsService;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PublishNewsJob {

    Logger log = LoggerFactory.getLogger(PublishNewsJob.class);

    NewsService newsService;

    @Scheduled(cron = "0 * * * * *")
    public void publishDatedNews() {
        log.info("Started publishDatedNews job");
        var publishedNewsSize = newsService.publishDatedNews();
        log.info("Published: {} news", publishedNewsSize);
        log.info("Finished publishDatedNews job");
    }

}
