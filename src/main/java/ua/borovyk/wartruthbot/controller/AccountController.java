package ua.borovyk.wartruthbot.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.borovyk.wartruthbot.entity.Account;
import ua.borovyk.wartruthbot.service.AccountService;

@Controller
@RequestMapping("/cp")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    AccountService accountService;

    @GetMapping(value = "/moderator/register")
    public String registration(Model model) {
        model.addAttribute("moderator", new Account());
        return "register-moderator";
    }

    @PostMapping(value = "/moderator/register")
    public String registration(@ModelAttribute("moderator") Account accountForm) {
        accountService.createNewModerator(accountForm);
        return "register-moderator";
    }

    @GetMapping(value = "/login")
    public String login() {
        accountService.createDefaultMe();
        return "login";
    }

}
