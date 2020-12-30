package by.personal.filmrandomizer.dao.impl;

import by.personal.filmrandomizer.dao.FilmDao;
import by.personal.filmrandomizer.entity.Film;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
        Query query = em.createQuery("Select f from Film f where f.sourceLink = :link");
        Optional<Film> result = Optional.empty();
        try {
            result = Optional.ofNullable((Film) query.setParameter("link", link).getSingleResult());
        } catch (NoResultException e) {

        }
        return result;
    }

    @Override
    public Film getRandom() {
        Query countQuery = em.createNativeQuery("select count(*) from data.film");
        BigInteger count = (BigInteger)countQuery.getSingleResult();

        Random random = new Random();
        int number = random.nextInt(count.intValue());

        Query selectQuery = em.createQuery("select f from Film f");
        selectQuery.setFirstResult(number);
        selectQuery.setMaxResults(1);
        return (Film) selectQuery.getSingleResult();
    }
}
