/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.FriendEntity;
import dao.MessageDAO;
import dao.MessageEntity;
import dao.MessageUserDAO;
import dao.MessageUserEntity;
import dao.NotificationEntity;
import dao.PostEntity;
import dao.UserDAO;
import dao.UserEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import servicesSecondaire.MessageElementaire;
import servicesSecondaire.UserService2;

/**
 *
 * @author Karl Lauret
 */
@Stateless
public class MessageServiceImpl implements MessageService {

    @EJB
    MessageElementaire messageElementaire;

    @EJB
    UserService2 userElementaire;

    /**
     *
     * @param m
     * @param r
     * @return
     */
    @Override
    public boolean sendToMails(MessageEntity m, List<String> r) {
        MessageEntity me = m;
        String tmpMessageGroup = "";
        //remove all duplicate
        r = new ArrayList<>(new HashSet<>(r));
        //test the sender is in the list if not add him
        if (!r.contains(me.getSendBy().getEmail())) {
            r.add(me.getSendBy().getEmail());
        }
        java.util.Collections.sort(r);
        for (String receiver : r) {

            UserEntity tmpR = userElementaire.findByEmail(receiver);
            if (tmpR != null) {
                tmpMessageGroup += tmpR.getId();
                messageElementaire.addTargetToMessage(tmpR, me);
            }

        }
        messageElementaire.updateGroupName(me, tmpMessageGroup);

        return true;
    }

    @Override
    public void sendToMails(String message, String osef, Long userId, List<String> targets) {
        MessageEntity m = messageElementaire.add(message, osef, userId);
        this.sendToMails(m, targets);
    }

    /**
     *
     * @param me
     * @param friends
     * @return
     */
    @Override
    public boolean sendNotifToFriends(NotificationEntity me, List<FriendEntity> friends) {
       // NotificationEntity me = (NotificationEntity) mgDao.findNotifByID(m.getId());
        String tmpMessageGroup = "notification";

        for (FriendEntity receiver : friends) {
            UserEntity tmpR;
            if (receiver.getFriend().getUsername().equals(me.getSendBy().getUsername())) {

                tmpR = receiver.getOwner();
            } else {
                tmpR = receiver.getFriend();
            }
            if (tmpR != null) {
                messageElementaire.addTargetToMessage(tmpR, me);
            }

        }
        messageElementaire.updateGroupName(me, tmpMessageGroup);

        return true;
    }

}
