package by.personal.filmrandomizer.controller;

import by.personal.filmrandomizer.service.FilmService;
import by.personal.filmrandomizer.entity.Film;
import by.personal.filmrandomizer.integration.IntegrationService;
import by.personal.filmrandomizer.integration.ParseHubRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private ParseHubRestClient parseHubRestClient;

    @Autowired
    private IntegrationService integrationService;

    @Value("#{'${parsehub.poject-tokens}'.split(',')}")
    private List<String> projectTokens;

    @Autowired
    @Qualifier("htmlRestTemplate")
    private RestTemplate htmlRestTemplate;

    @RequestMapping(value = "/get-film", method = RequestMethod.GET)
    public String index(Model model) throws Exception {
        Film f = filmService.getRandom();
        ResponseEntity<String> html = htmlRestTemplate.getForEntity(f.getSourceLink(), String.class);

        System.out.println("ASD");
        model .addAttribute("film", html.getBody());
        return "film-view";
    }



}
