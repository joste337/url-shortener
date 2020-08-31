package shortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import shortener.model.Response;
import shortener.model.Shortened;
import shortener.repository.ShortenedRepository;
import shortener.service.AliasGenerator;
import shortener.service.UrlValidator;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ShortenerController {
    @Autowired
    private UrlValidator urlValidator;
    @Autowired
    private AliasGenerator aliasGenerator;
    @Autowired
    private ShortenedRepository shortenedRepository;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/{alias:^[a-zA-Z0-9]+\\.?}")
    @ResponseBody
    public RedirectView redirect(@PathVariable String alias, HttpServletRequest httpServletRequest) {
        Shortened shortened = shortenedRepository.findByAlias(alias);

        if (shortened != null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(shortened.getUrl());
            return redirectView;
        } else {
            return new RedirectView("/");
        }
    }

    @PostMapping(value = "/")
    @ResponseBody
    public Response create(@RequestBody Shortened requestedShortened) {
        urlValidator.validateUrl(requestedShortened.getUrl());
        String alias = aliasGenerator.retrieveAlias(requestedShortened.getAlias());
        Shortened shortened = shortenedRepository.insert(new Shortened(requestedShortened.getUrl(), alias));
        return new Response("New alias \"" + shortened.getAlias() + "\" created for url \"" + shortened.getUrl() + "\"",
                HttpStatus.OK);
    }
}
