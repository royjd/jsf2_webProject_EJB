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
 * @author zakaridia
 */
@Stateless
public class ProfileDAOImpl implements ProfileDAO{

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
    public Long save(ProfileEntity p) {
        p = this.em.merge(p);    
        this.em.persist(p);
        return p.getId();
    }

    /**
     *
     * @param p
     */
    @Override
    public ProfileEntity update(ProfileEntity p) {
        return this.em.merge(p);
    }
    
    /**
     *
     * @param p
     */
    @Override
    public void delete(ProfileEntity p) {
        p = this.em.merge(p);
        this.em.remove(p);
    }



    
}
