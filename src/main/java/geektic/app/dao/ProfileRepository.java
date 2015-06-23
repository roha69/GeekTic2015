package geektic.app.dao;

import geektic.app.model.EnumGender;
import geektic.app.model.Profile;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import geektic.app.model.User;
import org.apache.log4j.Logger;

/**
 *
 * Repository class for the Geek entity
 *
 */
@Repository
public class ProfileRepository {

    private static final Logger LOGGER = Logger.getLogger(ProfileRepository.class);

    @PersistenceContext
    EntityManager entityManager;

    public ProfileRepository() {
    }
    
    
    /**
     * finds a user given its username
     *
     * @param username - the username of the searched user
     * @return  a matching user, or null if no user found.
     */
    public User findUserByUsername(String username) {

        List<User> users = entityManager.createNamedQuery(User.FIND_BY_USERNAME, User.class)
                .setParameter("username", username)
                .getResultList();

        return users.size() == 1 ? users.get(0) : null;
    }

    /**
     * Retourne le geek identifiée par l’ID donné
     *
     * @param id
     * @return Geek
     */
    public Profile findById(Long id) {
        TypedQuery<Profile> query = entityManager.createQuery("SELECT distinct g FROM Profile g left join fetch g.interets where g.id = :id", Profile.class);
        query.setParameter("id", id);
        return query.getResultList().get(0);
        //return entityManager.find(Geek.class, id);
    }

    /**
     * Retourne tous les geeks
     *
     * @return List<Geek>
     */
    public List<Profile> findAll() {
        TypedQuery<Profile> query = entityManager.createQuery("SELECT distinct g FROM Profile g left join fetch g.interets", Profile.class);
        return query.getResultList();
    }

    /**
     * Retourne liste geek selon les critères genre et intérets
     *
     * @param gender
     * @param interests
     * @return List<Geek>
     */
    public List<Profile> findByCriteria(EnumGender gender, List<Long> interests) {
        TypedQuery<Profile> query = entityManager.createQuery("SELECT distinct g FROM Profile g left join fetch g.interets inner join g.interets ir where ir.id in :list_interet and g.genre = :genre", Profile.class);
        query.setParameter("list_interet", interests);
        query.setParameter("genre", gender);
        return query.getResultList();

    }

    /**
     * Retourne le nombre geeks
     *
     * @return int
     */
    public int count() {
        Query query = entityManager.createQuery("SELECT count(g.id) FROM Profile g");
        Long count = (Long) query.getSingleResult();
        return (int) count.longValue();
    }

    /**
     * Ajoute 1 au nombre de vue du geek
     *
     * @param id
     */
    public void increaseView(Long id) {
        Query query = entityManager.createQuery("UPDATE Profile set vues = vues +1 where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    /**
     *
     * save changes made to a geek, or insert it if its new
     *
     * @param geek
     */
    public void save(Profile geek) {
        entityManager.merge(geek);
    }

    /**
     * checks if a username is still available in the database
     *
     * @param username - the username to be checked for availability
     * @return true if the username is still available
     */
    public boolean isUsernameAvailable(String username) {

        List<User> users = entityManager.createNamedQuery(User.FIND_BY_USERNAME, User.class)
                .setParameter("username", username)
                .getResultList();

        return users.isEmpty();
    }

}
