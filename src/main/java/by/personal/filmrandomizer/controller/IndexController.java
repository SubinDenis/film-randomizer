package by.personal.filmrandomizer.controller;

import by.personal.filmrandomizer.FilmService;
import by.personal.filmrandomizer.entity.Film;
import by.personal.filmrandomizer.integration.ParseHubRestClient;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private ParseHubRestClient parseHubRestClient;

    @Value("#{'${parsehub.poject-tokens}'.split(',')}")
    private List<String> projectTokens;

    @RequestMapping(value = "/get-film", method = RequestMethod.GET)
    public String index(Model model) throws Exception {

        Map<String, String> stringStringMap = parseHubRestClient.runProject(projectTokens.get(0));
        Thread.sleep(10000);
        String runToken = stringStringMap.get("run_token");
        Map<String, String> runInfo = parseHubRestClient.getRunInfo(runToken);
        Thread.sleep(10000);
        Map<String, String> runData = parseHubRestClient.getRunData(runToken);

        return "film-view";
    }



}
