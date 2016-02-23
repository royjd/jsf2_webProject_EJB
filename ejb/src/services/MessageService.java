/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import commun.GroupListNewMessages;
import dao.FriendEntity;
import dao.MessageEntity;
import dao.MessageUserEntity;
import dao.NotificationEntity;
import dao.PostEntity;
import dao.UserEntity;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author Karl Lauret
 */
@Local
public interface MessageService {

    /**
     * send the message to all the given emails
     *
     * @param m message to send
     * @param emais list of emails to which we send the email
     * @return
     */
    public boolean sendToMails(MessageEntity m, List<String> emais);

    /**
     * send the notification to all the friend list given
     *
     * @param m notification to send
     * @param friends list of friends
     * @return
     */
    public boolean sendNotifToFriends(NotificationEntity m, List<FriendEntity> friends);

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
     * create a notification
     *
     * @param p post attached to the notification
     * @param subject of the notification
     * @param ue post author who trigger the notification
     * @return NotificationEntity
     */
    public NotificationEntity addNotification(PostEntity p, String subject, UserEntity ue);

    /**
     * mark all the messages of the given user of a specific message's groupName
     * as read
     *
     * @param ue
     * @param groupMessage
     */
    public void messageRead(UserEntity ue, String groupMessage);

    /**
     * return a MessageDisplayListwhich contain multiple list needed for the
     * display of the message without the message list of a given groupMessage
     *
     * @param userID
     * @return MessageDisplayList
     */
    // public MessageDisplayList getGListPlusNewListFromUserID(Long userID);
    /**
     * return a MessageDisplayListwhich contain multiple list needed for the
     * display of the message with the message list of a given groupMessage
     *
     * @param userID
     * @param groupMessage
     * @return MessageDisplayList
     */
    //public MessageDisplayList getGListPlusNewListFromUserID(Long userID, String groupMessage);
    /**
     * return all the notification for a given user id
     *
     * @param userID
     * @return
     */
    public List<MessageUserEntity> getNotificationByUser(Long userID);

    /**
     * mark a message as read
     *
     * @param messageID
     */
    public void messageRead(Long messageID);

    public void sendToMails(String message, String osef, Long userId, List<String> targets);

    public List<GroupListNewMessages> findGroupMessageByUserID(Long userId);
    
    public List<MessageUserEntity> findMessageUserByGroupName(Long userID,String groupName);

}
