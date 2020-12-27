package by.personal.filmrandomizer.service;


import by.personal.filmrandomizer.dao.FilmDao;
import by.personal.filmrandomizer.entity.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FilmService {

    private final Logger logger = LoggerFactory.getLogger(FilmService.class);

    @Autowired
    private FilmDao filmDao;

    public Integer saveOrUpdate(Film film) {
        Optional<Film> result = filmDao.findBySourceLink(film.getSourceLink());
        if (result.isEmpty()) {
            filmDao.save(film);
        } else {
            filmDao.update(film);
        }
        logger.info("saveOrUpdate: filmId is: " + film.getFilmId());
        return film.getFilmId();
    }

}
