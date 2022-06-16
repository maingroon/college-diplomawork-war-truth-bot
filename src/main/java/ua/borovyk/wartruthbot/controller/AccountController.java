package ua.borovyk.wartruthbot.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.borovyk.wartruthbot.entity.Account;
import ua.borovyk.wartruthbot.service.AccountService;
import ua.borovyk.wartruthbot.validator.AccountValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cp")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    AccountValidator accountValidator;
    AccountService accountService;

    @GetMapping(value = "/moderator/register")
    public String registration(Model model) {
        model.addAttribute("moderator", new Account());
        return "register-moderator";
    }

    @PostMapping(value = "/moderator/register")
    public String registration(Model model,
                               @ModelAttribute("moderator") Account accountForm) {
        var errorMessages = accountValidator.validate(accountForm);
        if (!errorMessages.isEmpty()) {
            model.addAttribute("errorMessages", errorMessages);
        } else {
            accountService.createNewModerator(accountForm);
        }
        return "register-moderator";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

}
