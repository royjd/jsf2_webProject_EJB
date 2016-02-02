/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.ejb.Local;

/**
 *
 * @author Karl Lauret
 */
@Local
public interface MessageDAO {

    /**
     *
     * @param m
     * @return
     */
    
    public MessageEntity save(MessageEntity m);

    /**
     *
     * @param m
     */
    public void update(MessageEntity m);

    /**
     *
     * @param m
     */
    public void delete(MessageEntity m);

    /**
     *
     * @param id
     * @return
     */
    public MessageEntity findByID(Long id);

    /**
     * 
     * Add a target to message
     * 
     * @param m message to add the target to
     * @param mue the target to the message
     */
    public void addTarget(MessageEntity m, MessageUserEntity mue);
    
    /**
     * return all the messages that share the same groupName
     * 
     * @param messageGroup 
     * @return
     */
    public MessageEntity findByMessageGroup(String messageGroup);

    /**
     * return the notification matching the given id
     * 
     * @param id the notification id
     * @return
     */
    public MessageEntity findNotifByID(Long id);

}
