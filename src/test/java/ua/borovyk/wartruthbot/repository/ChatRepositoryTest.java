package ua.borovyk.wartruthbot.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.borovyk.wartruthbot.Application;
import ua.borovyk.wartruthbot.constant.ChatStatus;
import ua.borovyk.wartruthbot.constant.NewsRegion;
import ua.borovyk.wartruthbot.constant.NewsTopic;
import ua.borovyk.wartruthbot.entity.Chat;

import javax.transaction.Transactional;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class ChatRepositoryTest {

    @Autowired
    private ChatRepository chatRepository;

    @Test
    void testFindChatsWithRegionsAndTopicsIntersections() {
        var chat = new Chat();
        chat.setChatId(1L);
        chat.setSelectedRegions(Set.of(NewsRegion.SM, NewsRegion.KV));
        chat.setSelectedTopics(Set.of(NewsTopic.INFORMATION, NewsTopic.ALERT));
        chatRepository.save(chat);

        var result = chatRepository.findAllByStatusAndSelectedTopicsAndSelectedRegionsContaining(
                ChatStatus.ACTIVE,
                Set.of(NewsTopic.INFORMATION, NewsTopic.MIGRANTS),
                Set.of(NewsRegion.SM, NewsRegion.DP)
        );
        Assertions.assertEquals(1, result.size());
    }

}

