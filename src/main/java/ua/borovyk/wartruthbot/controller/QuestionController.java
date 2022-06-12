package ua.borovyk.wartruthbot.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.borovyk.wartruthbot.constant.MessageType;
import ua.borovyk.wartruthbot.entity.Message;
import ua.borovyk.wartruthbot.service.AccountService;
import ua.borovyk.wartruthbot.service.MessageService;
import ua.borovyk.wartruthbot.util.LocalDateTimeUtil;

@Controller
@RequestMapping("/cp/questions")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuestionController {

    MessageService messageService;

    AccountService accountService;

    @GetMapping
    public String allQuestions(Model model) {
        var questions = messageService.listUnreplyedQuestion();
        model.addAttribute("questions", questions);
        return "all-questions";
    }

    @GetMapping("/{id}")
    public String getQuestion(Model model,
                              @PathVariable("id") Long questionId) {
        var question = messageService.getQuestionById(questionId);
        model.addAttribute("question", question);
        model.addAttribute("reply", new Message());
        return "get-question";
    }

    @PostMapping("/{id}")
    public String replyQuestion(Model model,
                                @PathVariable("id") Long questionId,
                                @ModelAttribute("reply") Message reply) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUser = accountService.getAccountByUsername(authentication.getName())
                .orElseThrow();

        var question = messageService.getQuestionById(questionId);
        question.setResponder(currentUser);

        reply.setId(null);
        reply.setResponder(currentUser);
        reply.setType(MessageType.ANSWER);
        reply.setChat(question.getChat());
        reply.setDateCreated(LocalDateTimeUtil.now());

        messageService.sendQuestionReply(reply);
        messageService.saveMessage(question);

        return "redirect:/cp/questions";
    }

}
