package ua.borovyk.wartruthbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.borovyk.wartruthbot.constant.MessageType;
import ua.borovyk.wartruthbot.entity.Account;
import ua.borovyk.wartruthbot.entity.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findByIdAndType(Long id, MessageType type);

    List<Message> findAllByTypeAndResponderOrderByDateCreatedAsc(MessageType type, Account responder);

}
