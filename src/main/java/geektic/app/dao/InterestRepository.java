package geektic.app.dao;

import geektic.app.model.Interest;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Repository;

@Repository
public class InterestRepository {

    private static final Logger LOGGER = Logger.getLogger(InterestRepository.class);

    @PersistenceContext
    EntityManager entityManager;

    public InterestRepository() {
    }

    /**
     * Retourne le interet identifiée par l’ID donné
     *
     * @param id
     * @return Geek
     */
    public Interest findById(Long id) {
        return entityManager.find(Interest.class, id);
    }

    /**
     * Retourne tous les interets
     *
     * @return List<Interest>
     */
    public List<Interest> findAll() {
        TypedQuery<Interest> query = entityManager.createQuery("SELECT r FROM Interest r", Interest.class);
        return query.getResultList();
    }

}
