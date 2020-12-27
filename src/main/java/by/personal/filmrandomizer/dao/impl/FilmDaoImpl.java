package by.personal.filmrandomizer.dao.impl;

import by.personal.filmrandomizer.dao.FilmDao;
import by.personal.filmrandomizer.entity.Film;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class FilmDaoImpl implements FilmDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Film> get(long id) {
        return Optional.ofNullable(em.find(Film.class, id));
    }

    @Override
    public List<Film> getAll() {
        Query query = em.createQuery("select f from Film f");
        return query.getResultList();
    }

    @Override
    public void save(Film film) {
        em.persist(film);
    }

    @Override
    public void update(Film film) {
        em.merge(film);
    }

    @Override
    public void delete(Film film) {
        em.remove(film);
    }

    @Override
    public Optional<Film> findBySourceLink(String link) {
        Query query = em.createQuery("Select f from Film where f.sourceLink == :link");
        return Optional.ofNullable((Film)query.setParameter("link", link).getSingleResult());
    }
}
