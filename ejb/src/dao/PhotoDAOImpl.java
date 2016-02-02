/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author zakaridia
 */
@Stateless
public class PhotoDAOImpl implements PhotoDAO{

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
     * @param photo
     * @return
     */
    @Override
    public Long save(PhotoEntity photo){
        photo = this.em.merge(photo);
        this.em.persist(photo);
        return photo.getId();
    }

    /**
     *
     * @param photo
     */
    @Override
    public void update(PhotoEntity photo){
        photo = this.em.merge(photo);
        this.em.persist(photo);
    }
    
    /**
     *
     * @param id
     * @return
     */
    @Override
    public PhotoEntity find(Long id){
        return this.em.find(PhotoEntity.class, id);
    }
    
    /**
     *
     * @param limit
     * @return
     */
    @Override
    public List<PhotoEntity> find(int limit){
        Query q;
        q = this.em.createQuery("SELECT photo FROM PhotoEntity photo");
        q.setMaxResults(limit);
        return q.getResultList();
    }

    /**
     *
     * @return
     */
    @Override
    public List<PhotoEntity> findAll() {
        Query q;
        q = this.em.createQuery("SELECT photo FROM PhotoEntity photo");
        return q.getResultList();
    }

    /**
     *
     * @param photo
     */
    @Override
    public void delete(PhotoEntity photo) {
        photo = this.em.merge(photo);
        this.em.remove(photo);
    }
}
