package by.personal.filmrandomizer.controller;

import by.personal.filmrandomizer.service.FilmService;
import by.personal.filmrandomizer.entity.Film;
import by.personal.filmrandomizer.integration.IntegrationService;
import by.personal.filmrandomizer.integration.ParseHubRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/get-film", method = RequestMethod.GET)
    public String index(Model model) throws Exception {

        List<Map<String, Object>> data = integrationService.getDataForAll(Collections.singletonList("tCf0C7Tj6_Te"));
        List<Film> filmList = integrationService.extractFilmData(data);
//        integrationService.mergeData(filmList);


        System.out.println("ASD");
        model.addAttribute("film", filmList.get(2));
        return "film-view";
    }



}
