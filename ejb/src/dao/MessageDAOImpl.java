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
 * @author Karl Lauret
 */
@Stateless
public class MessageDAOImpl implements MessageDAO {

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
    public MessageEntity save(MessageEntity u) {
        u = this.em.merge(u);
        this.em.persist(u);
        UserEntity sendedBy = u.getSendBy();
        sendedBy.addMessageS(u);
        em.merge(sendedBy);
        return u;
    }

    /**
     *
     * @param u
     */
    @Override
    public void update(MessageEntity u) {
        this.em.merge(u);
    }

    /**
     *
     * @param u
     */
    @Override
    public void delete(MessageEntity u) {
        u = em.merge(u);
        em.remove(u);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public MessageEntity findByID(Long id) {
        return (MessageEntity) this.em.find(MessageEntity.class, id);
    }

    /**
     *
     * @param m
     * @param mue
     */
    @Override
    public void addTarget(MessageEntity m,MessageUserEntity mue) {
        m.addTarget(mue);
        //m.updateGroupName();
        em.merge(m);
    }

    /**
     *
     * @param messageGroup
     * @return
     */
    @Override
    public MessageEntity findByMessageGroup(String messageGroup) {
        try {
            return (MessageEntity) this.em.createQuery("SELECT t FROM MessageEntity t where t.groupName = :value1")
                    .setParameter("value1", messageGroup).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public MessageEntity findNotifByID(Long id) {
        return (MessageEntity) this.em.find(MessageEntity.class, id);
    }
}
