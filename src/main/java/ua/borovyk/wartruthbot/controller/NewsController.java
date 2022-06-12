package ua.borovyk.wartruthbot.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.borovyk.wartruthbot.constant.NewsRegion;
import ua.borovyk.wartruthbot.constant.NewsTopic;
import ua.borovyk.wartruthbot.entity.News;
import ua.borovyk.wartruthbot.service.NewsService;

import java.util.Arrays;

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
        model.addAttribute("allTopics", Arrays.stream(NewsTopic.values()).toList());
        model.addAttribute("allRegions", Arrays.stream(NewsRegion.values()).toList());
        return "create-news";
    }

    @PostMapping("/create")
    public String createNews(Model model,
                             @ModelAttribute("news") News news) {
        newsService.saveNews(news);
        return "redirect:/cp/news";
    }

    @GetMapping("/{id}/edit")
    public String editNews(Model model,
                           @PathVariable("id") Long newsId) {
        var news = newsService.getNewsById(newsId);
        model.addAttribute("news", news);
        model.addAttribute("allTopics", Arrays.stream(NewsTopic.values()).toList());
        model.addAttribute("allRegions", Arrays.stream(NewsRegion.values()).toList());
        return "edit-news";
    }

    @PostMapping("/{id}/edit")
    public String editNews(Model model,
                           @ModelAttribute("news") News news) {
        newsService.saveNews(news);
        return "redirect:/cp/news";
    }

    @PostMapping("/{id}/delete")
    public String deleteNews(Model model,
                             @PathVariable("id") Long newsId) {
        newsService.deleteNews(newsId);
        return "redirect:/cp/news";
    }

}
