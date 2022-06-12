package ua.borovyk.wartruthbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.borovyk.wartruthbot.constant.ChatStatus;
import ua.borovyk.wartruthbot.constant.NewsRegion;
import ua.borovyk.wartruthbot.constant.NewsTopic;
import ua.borovyk.wartruthbot.entity.Chat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByChatId(Long chatId);


    @Query("""
        SELECT DISTINCT chat FROM Chat chat
            LEFT JOIN chat.selectedTopics topics
            LEFT JOIN chat.selectedRegions regions
        WHERE chat.status = ?1
            AND topics IN ?2
            AND regions IN ?3
    """)
    List<Chat> findAllByStatusAndSelectedTopicsAndSelectedRegionsContaining(
            ChatStatus status,
            Set<NewsTopic> selectedTopics,
            Set<NewsRegion> selectedRegions
    );

}
