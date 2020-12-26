package by.personal.filmrandomizer;


import by.personal.filmrandomizer.entity.Film;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Component
public class FilmService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Film saveAndGetFilm() {
        Film film = new Film();
        film.setMediaLink("123");
        film.setName(LocalDateTime.now().toString());
        film.setYear(1234);
        film.setSourceLink("asd");
        entityManager.persist(film);
        entityManager.flush();
        return film;
    }

}
