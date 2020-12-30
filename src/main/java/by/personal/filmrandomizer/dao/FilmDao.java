package by.personal.filmrandomizer.dao;

import by.personal.filmrandomizer.entity.Film;

import java.util.Optional;

public interface FilmDao extends Dao<Film> {

    Optional<Film> findBySourceLink(String link);

    Film getRandom();
}
