package ua.borovyk.wartruthbot.validator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.borovyk.wartruthbot.entity.Account;
import ua.borovyk.wartruthbot.service.AccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountValidator {

    AccountService accountService;

    public List<String> validate(Account account) {
        Objects.requireNonNull(account);
        var username = account.getUsername();
        var password = account.getPassword();

        var errorMessages = new ArrayList<String>();
        if (username == null || username.isBlank()) {
            errorMessages.add("Імʼя користувача не може бути пустим.");
        } else if (username.length() < 6 || username.length() > 32) {
            errorMessages.add("Імʼя користувача має бути не менше 6 і не більше 32 символів.");
        } else if (accountService.getAccountByUsername(username).isPresent()) {
            errorMessages.add("Користувач з таким іменем вже існує.");
        }

        if (password == null || password.isBlank()) {
            errorMessages.add("Пароль користувача не може бути пустим.");
        } else if (password.length() < 6 || password.length() > 32) {
            errorMessages.add("Пароль користувача має бути не менше 6 і не більше 32 символів.");
        }
        return errorMessages;
    }

}
