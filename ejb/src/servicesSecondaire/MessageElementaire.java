/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.MessageEntity;
import dao.MessageUserEntity;
import dao.NotificationEntity;
import dao.PostEntity;
import dao.UserEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Karl Lauret
 */
@Local
public interface MessageElementaire {

    /**
     * create a message
     *
     * @param content the content of the message
     * @param subject the subject of the message
     * @param sender_id the author of the message
     * @return MessageEntity
     */
    public MessageEntity add(String content, String subject, Long sender_id);

    /**
     * mark all the messages of the given user of a specific message's groupName
     * as read
     *
     * @param ue
     * @param groupMessage
     */
    public void messageRead(UserEntity ue, String groupMessage);

    public void messageRead(Long userID, String groupMessage);

    /**
     * mark a message as read
     *
     * @param messageID
     */
    public void messageRead(Long messageID);

    /**
     * return all the notification for a given user id
     *
     * @param userID
     * @return
     */
    public List<MessageUserEntity> getNotificationByUser(Long userID);

    /**
     * create a notification
     *
     * @param p post attached to the notification
     * @param subject of the notification
     * @param ue post author who trigger the notification
     * @return NotificationEntity
     */
    public NotificationEntity addNotification(PostEntity p, String subject, UserEntity ue);

    public List<String> findGroupMessageByUserID(Long userID);

    public List<String> findTargetsOfMessagesByGroupName(String groupName);

    public List<MessageUserEntity> findMessageUserByGroupName(Long userID, String groupName);

    public void addTargetToMessage(UserEntity ue, MessageEntity me);

    public void updateGroupName(MessageEntity me, String groupName);

    public Integer findNbOfNewMessageForGroupName(Long userID,String groupName);

    public Integer findNbOfNewMessageForUserID(Long userId);

    public Integer findNbOfNewNotificationForUserID(Long userId);
}
