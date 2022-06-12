package ua.borovyk.wartruthbot.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.borovyk.wartruthbot.entity.News;
import ua.borovyk.wartruthbot.service.NewsService;

@Controller
@RequestMapping("/cp/news")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NewsController {

    NewsService newsService;

    @GetMapping
    public String allNews(Model model) {
        var news = newsService.listOfSortedNews(Sort.Direction.DESC, "publishDate");
        model.addAttribute("newsList", news);
        return "all-news";
    }

    @GetMapping("/create")
    public String createNews(Model model) {
        model.addAttribute("news", new News());
        return "create-news";
    }

    @PostMapping("/create")
    public String createNews(Model model,
                             @ModelAttribute("newsForm") News newsForm) {
        return "create-news";
    }

}
