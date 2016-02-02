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
import javax.persistence.Query;

/**
 *
 * @author zakaridia
 */
@Stateless
public class ExperienceDAOImpl implements ExperienceDAO {

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
     * @param e
     * @return
     */
    @Override
    public Long save(ExperienceEntity e) {
        e = this.em.merge(e);
        this.em.persist(e);
        return e.getId();
    }

    /**
     *
     * @param e
     */
    @Override
    public void update(ExperienceEntity e) {
        this.em.merge(e);
    }

    /**
     *
     * @param e
     */
    @Override
    public void delete(ExperienceEntity e) {
        e = this.em.merge(e);
        this.em.remove(e);
    }

    /**
     *
     * @param profileId
     * @return
     */
    @Override
    public List<ExperienceEntity> findExperiencesForProfil(Long profileId) {
        Query q;
        q = this.em.createQuery("SELECT e FROM ExperienceEntity e WHERE e.profile.id = ?");
        q.setParameter(1, profileId);
        return q.getResultList();
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ExperienceEntity findById(Long id) {
        return this.em.find(ExperienceEntity.class, id);
    }

    /**
     *
     * @param profileId
     * @param limit
     * @return
     */
    @Override
    public List<ExperienceEntity> findExperiencesForProfil(Long profileId, int limit) {
        Query q;
        q = this.em.createQuery("SELECT e FROM ExperienceEntity e WHERE e.profile.id = ?");
        q.setParameter(1, profileId);
        q.setMaxResults(limit);
        return q.getResultList();
    }

    /**
     *
     * @param userID
     * @return
     */
    @Override
    public ExperienceEntity findLastExperienceForUser(Long userID) {
        try {

            Query q;
            q = this.em.createQuery("SELECT e FROM ExperienceEntity e WHERE e.profile.profileOwner.id = ? order by e.id desc");
            q.setParameter(1, userID);
            q.setMaxResults(1);
            return (ExperienceEntity) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     *
     * @param profileID
     * @return
     */
    @Override
    public ExperienceEntity findLastExperienceForProfile(Long profileID) {
        try {

            Query q;
            q = this.em.createQuery("SELECT e FROM ExperienceEntity e WHERE e.profile.id = ? order by e.id desc");
            q.setParameter(1, profileID);
            q.setMaxResults(1);
            return (ExperienceEntity) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
