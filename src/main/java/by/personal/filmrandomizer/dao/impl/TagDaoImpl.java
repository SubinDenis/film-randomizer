package by.personal.filmrandomizer.dao.impl;

import by.personal.filmrandomizer.dao.TagDao;
import by.personal.filmrandomizer.entity.Tag;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class TagDaoImpl implements TagDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Tag> get(long id) {
        return Optional.ofNullable(em.find(Tag.class, id));
    }

    @Override
    public List<Tag> getAll() {
        Query query = em.createQuery("select t from Tag t");
        return query.getResultList();
    }

    @Override
    public void save(Tag tag) {
        em.persist(tag);
    }

    @Override
    public void update(Tag tag) {
        em.merge(tag);
    }

    @Override
    public void delete(Tag tag) {
        em.remove(tag);
    }
}
