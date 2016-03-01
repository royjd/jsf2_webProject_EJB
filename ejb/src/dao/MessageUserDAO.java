/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.HashMap;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Karl Lauret
 */
@Local
public interface MessageUserDAO {

    /**
     *
     * @param u
     * @return
     */
    public MessageUserEntity save(MessageUserEntity u);

    /**
     *
     * @param u
     */
    public void update(MessageUserEntity u);

    /**
     *
     * @param u
     */
    public void delete(MessageUserEntity u);
    
    /**
     *
     * @param id
     * @return
     */
    public MessageUserEntity findByID(Long id);

    /**
     * return the list of new MessageUserEntity for a given message's groupName
     * @param userID
     * @param groupMessage
     * @return
     */
    public List<MessageUserEntity> findMessageForUserAndGroupMessage(Long userID, String groupMessage);
    
    /**
     * return all the messages received by the given user id
     * 
     * @param userID
     * @return
     */
    public List<MessageUserEntity> findAllMessageRByUserID(Long userID);
    public List<MessageUserEntity> findNewMessageForUserAndGroupMessage(Long userID, String groupMessage);

    public List<MessageUserEntity> findNewMessageForUser(Long userID);

    public List<MessageUserEntity> findNewNotificationForUser(Long userID);
    
}
