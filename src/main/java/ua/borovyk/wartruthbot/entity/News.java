package ua.borovyk.wartruthbot.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ua.borovyk.wartruthbot.constant.NewsRegion;
import ua.borovyk.wartruthbot.constant.NewsTopic;
import ua.borovyk.wartruthbot.util.LocalDateTimeUtil;

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
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    String content;

    @Column(nullable = false)
    boolean published = false;

    @Column(name = "publish_date", nullable = false)
    LocalDateTime publishDate = LocalDateTimeUtil.now();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "news_region", joinColumns = @JoinColumn(name = "news_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    Set<NewsRegion> regions = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "news_topic", joinColumns = @JoinColumn(name = "news_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "topic", nullable = false)
    Set<NewsTopic> topics = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(id, news.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
