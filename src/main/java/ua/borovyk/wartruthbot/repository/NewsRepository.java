package ua.borovyk.wartruthbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.borovyk.wartruthbot.entity.News;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findAllByPublishedFalseAndPublishDateLessThanEqual(LocalDateTime publishDate);

}
