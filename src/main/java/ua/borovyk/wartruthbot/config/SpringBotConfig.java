package ua.borovyk.wartruthbot.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpringBotConfig {

    @Value("${telegram.bot.path}")
    String botPath;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(botPath).build();
    }

}
