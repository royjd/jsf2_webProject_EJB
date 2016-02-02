/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Karl Lauret
 */
@Stateless
public class UserDAOImpl implements UserDAO {

    @PersistenceContext(unitName = "fanfareFinalPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     *
     * @param em
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     *
     * @param u
     * @return
     */
    @Override
    public Long save(UserEntity u) {
        u = this.em.merge(u);
        this.em.persist(u);
        return u.getId();
    }

    /**
     *
     * @param u
     */
    @Override
    public void update(UserEntity u) {
        this.em.merge(u);
    }

    /**
     *
     * @param u
     */
    @Override
    public void delete(UserEntity u) {
        u = em.merge(u);
        em.remove(u);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public UserEntity findByID(Long id) {
        return (UserEntity) this.em.find(UserEntity.class, id);
    }

    /**
     *
     * @param email
     * @return
     */
    @Override
    public UserEntity findByEmail(String email) {
        try {
            return (UserEntity) this.em.createQuery("SELECT t FROM UserEntity t where t.email = :value1")
                    .setParameter("value1", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public UserEntity findByUsername(String username) {
        try {
            return (UserEntity) this.em.createQuery("SELECT t FROM UserEntity t where t.username = :value1")
                    .setParameter("value1", username).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    /*@Transactional
    @Override
    public void addFriend(UserEntity friend, UserEntity owner, FriendEntity fe) {
        friend.addFriendedBy(fe);
        owner.addFriend(fe);
        this.em.merge(friend);
        this.em.merge(owner);
    }*/

    /**
     *
     * @param param
     * @return
     */

    @Override
    public List<UserEntity> findBysearch(String param) {
        try {

            List<UserEntity> userEntities = this.em.createQuery("SELECT t FROM UserEntity t where t.email LIKE :value1 OR t.username LIKE :value1")
                    .setParameter("value1", "%" + param + "%").getResultList();

            return userEntities;

        } catch (NoResultException e) {
            return null;
        }

    }

   

}
