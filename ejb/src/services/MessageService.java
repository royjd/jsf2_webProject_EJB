/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

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
    public boolean sendNotifToFriends( List<FriendEntity> friends,PostEntity post,UserEntity ue);

   





    public void sendToMails(String message, String osef, Long userId, List<String> targets);

  

}
