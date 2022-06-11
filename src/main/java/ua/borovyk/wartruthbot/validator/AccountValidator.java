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

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountValidator implements Validator {

    AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var account = (Account) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "admin.add.account.username.required");
        if (account.getUsername().length() < 8 || account.getUsername().length() > 32) {
            errors.rejectValue("username", "admin.add.account.username.size");
        }

        accountService.getAccountByUsername(account.getUsername())
                .ifPresent(username -> errors.rejectValue("username", "admin.add.account.username.exists"));

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "admin.add.account.password.required");
        if (account.getPassword().length() < 8 || account.getPassword().length() > 32) {
            errors.rejectValue("password", "admin.add.account.password.size");
        }
    }

}
