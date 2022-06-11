package ua.borovyk.wartruthbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.borovyk.wartruthbot.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
