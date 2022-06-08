package ua.borovyk.wartruthbot.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import ua.borovyk.wartruthbot.constant.ChatStatus;
import ua.borovyk.wartruthbot.constant.KeyboardType;
import ua.borovyk.wartruthbot.constant.NewsRegion;
import ua.borovyk.wartruthbot.constant.NewsTopic;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(
        name = "chat",
        uniqueConstraints = {
                @UniqueConstraint(name = "chat_chat_id_uk", columnNames = "chat_id")
        }
)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(name = "chat_id", nullable = false)
    Long chatId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ChatStatus status = ChatStatus.ACTIVE;

    @Column(name = "current_keyboard", nullable = false)
    @Enumerated(EnumType.STRING)
    KeyboardType currentKeyboard = KeyboardType.MAIN;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "chat_selected_topic", joinColumns = @JoinColumn(name = "chat_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "selected_topic", nullable = false)
    Set<NewsTopic> selectedTopics = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "chat_selected_region", joinColumns = @JoinColumn(name = "chat_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "selected_region", nullable = false)
    Set<NewsRegion> selectedRegions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(id, chat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
