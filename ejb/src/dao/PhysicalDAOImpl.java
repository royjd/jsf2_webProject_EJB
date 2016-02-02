/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author zdiawara
 */
@Stateless
public class PhysicalDAOImpl implements PhysicalDAO{

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
     * @param p
     * @return
     */
    @Override
    public Long save(PhysicalEntity p) {
        p = this.em.merge(p);
        this.em.persist(p);
        return p.getId();
    }

    /**
     *
     * @param p
     */
    @Override
    public void update(PhysicalEntity p) {
        this.em.merge(p);
    }

    /**
     *
     * @param p
     */
    @Override
    public void delete(PhysicalEntity p) {
        p = this.em.merge(p);
        this.em.remove(p);
    }

    /**
     *
     * @param profileId
     * @return
     */
    @Override
    public PhysicalEntity findByProfileId(Long profileId) {
        try{
            return (PhysicalEntity) this.em.createQuery("SELECT p FROM PhysicalEntity p where p.profile.id = :profileId")
                 .setParameter("profileId", profileId).getSingleResult();
        }catch(NoResultException e){
             return null;
        }
    }
    
}
