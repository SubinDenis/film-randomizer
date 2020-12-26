package by.personal.filmrandomizer.controller;

import by.personal.filmrandomizer.FilmService;
import by.personal.filmrandomizer.entity.Film;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Controller
public class IndexController {

    @Autowired
    private FilmService filmService;

    @RequestMapping(value = "/get-film", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("film", filmService.saveAndGetFilm());
        return "film-view";
    }



}
