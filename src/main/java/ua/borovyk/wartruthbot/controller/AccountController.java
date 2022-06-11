package ua.borovyk.wartruthbot.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.borovyk.wartruthbot.entity.Account;
import ua.borovyk.wartruthbot.service.AccountService;
import ua.borovyk.wartruthbot.service.SecurityService;
import ua.borovyk.wartruthbot.validator.AccountValidator;

@Controller
@RequestMapping("/cp")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    AccountService accountService;

    AccountValidator accountValidator;

    SecurityService securityService;

    @RequestMapping(value = "/moderator/create", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("moderator", new Account());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("moderator") Account accountForm,
                               BindingResult bindingResult,
                               Model model) {
        accountValidator.validate(accountForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        accountService.createNewModerator(accountForm);
        securityService.autoLogin(accountForm.getUsername(), accountForm.getPassword());

        return "registration";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        accountService.createDefaultMe();
        if (error != null) {
            model.addAttribute("error", "Імʼя або пароль не коректні.");
        }
        if (logout != null) {
            model.addAttribute("message", "Ви успішно вийшли.");
        }

        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(Model model) {
        return "main";
    }

}
