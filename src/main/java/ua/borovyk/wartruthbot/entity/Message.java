package ua.borovyk.wartruthbot.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ua.borovyk.wartruthbot.constant.MessageType;
import ua.borovyk.wartruthbot.util.LocalDateTimeUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @OneToOne
    @JoinColumn(name = "chat_id", nullable = false)
    Chat chat;

    @Column(nullable = false)
    MessageType type;

    @Column(name = "sequence_number", nullable = false)
    int sequenceNumber = 0;

    @Column(columnDefinition = "TEXT", nullable = false)
    String content;

    @ManyToOne
    @JoinColumn(name = "responder_id")
    Account responder;

    @Column(name = "date_created", nullable = false)
    LocalDateTime dateCreated = LocalDateTimeUtil.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
