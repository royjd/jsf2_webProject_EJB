/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import commun.GroupListNewMessages;
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

/**
 *
 * @author Karl Lauret
 */
@Stateless
public class MessageServiceImpl implements MessageService {

    @EJB
    MessageDAO mgDao;

    @EJB
    UserDAO userDao;

    @EJB
    MessageUserDAO mgUserDao;

    /**
     *
     * @param content
     * @param subject
     * @param sender_id
     * @return
     */
    @Override
    public MessageEntity add(String content, String subject, Long sender_id) {
        UserEntity tmpS = userDao.findByID(sender_id);
        if (tmpS != null) {
            MessageEntity me = new MessageEntity(content, subject, tmpS);
            me = mgDao.save(me);
            return me;
        }
        return null;
    }

    /**
     *
     * @param m
     * @param r
     * @return
     */
    @Override
    public boolean sendToMails(MessageEntity m, List<String> r) {
        MessageEntity me = mgDao.findByID(m.getId());
        String tmpMessageGroup = "";
        //remove all duplicate
        r = new ArrayList<>(new HashSet<>(r));
        //test the sender is in the list if not add him
        if (!r.contains(me.getSendBy().getEmail())) {
            r.add(me.getSendBy().getEmail());
        }
        java.util.Collections.sort(r);
        for (String receiver : r) {

            UserEntity tmpR = userDao.findByEmail(receiver);
            if (tmpR != null) {
                tmpMessageGroup += tmpR.getId();
                MessageUserEntity tmpMUE = new MessageUserEntity(me, tmpR);
                tmpMUE = mgUserDao.save(tmpMUE);
                tmpMUE = mgUserDao.findByID(tmpMUE.getId());
                mgDao.addTarget(me, tmpMUE);
            }

        }

        me.setGroupName(tmpMessageGroup);
        mgDao.update(me);

        return true;
    }

    /**
     *
     * @param ue
     * @param groupMessage
     */
    @Override
    public void messageRead(UserEntity ue, String groupMessage) {
        List<MessageUserEntity> mues = mgUserDao.findNewMessageForUserAndGroupMessage(ue.getId(), groupMessage);
        for (MessageUserEntity mue : mues) {
            mue.setNewMessage(false);
            mgUserDao.update(mue);
        }
    }

    /**
     *
     * @param messageID
     */
    @Override
    public void messageRead(Long messageID) {
        MessageUserEntity mue = mgUserDao.findByID(messageID);

        mue.setNewMessage(false);
        mgUserDao.update(mue);

    }

    /**
     *
     * @param userID
     * @return
     */
    /*@Override
    public MessageDisplayList getGListPlusNewListFromUserID(Long userID) {
        List<MessageUserEntity> fta = this.mgUserDao.findAllMessageRByUserID(userID);
        HashMap<String, List<MessageUserEntity>> hmmue = new HashMap<>();
        HashMap<String, Boolean> newMessages = new HashMap<>();
        for (MessageUserEntity mue : fta) {
            if (!newMessages.containsKey(mue.getMessage().getGroupName())) {
                newMessages.put(mue.getMessage().getGroupName(), mue.isNewMessage());
            } else if (mue.isNewMessage() == true) {
                newMessages.put(mue.getMessage().getGroupName(), mue.isNewMessage());
            }
            if (!hmmue.containsKey(mue.getMessage().getGroupName())) {
                hmmue.put(mue.getMessage().getGroupName(), mue.getMessage().getTarget());
            }

        }
        MessageDisplayList mdl = new MessageDisplayList(hmmue, newMessages);
        return mdl;
    }*/
    /**
     *
     * @param userID
     * @param groupMessage
     * @return
     */
    /*@Override
    public MessageDisplayList getGListPlusNewListFromUserID(Long userID, String groupMessage) {
        List<MessageUserEntity> fta = this.mgUserDao.findAllMessageRByUserID(userID);
        HashMap<String, List<MessageUserEntity>> hmmue = new HashMap<>();
        HashMap<String, Boolean> newMessages = new HashMap<>();
        List<MessageEntity> me = new ArrayList<>();
        for (MessageUserEntity mue : fta) {
            if (!newMessages.containsKey(mue.getMessage().getGroupName())) {
                newMessages.put(mue.getMessage().getGroupName(), mue.isNewMessage());
            } else if (mue.isNewMessage() == true) {
                newMessages.put(mue.getMessage().getGroupName(), mue.isNewMessage());
            }
            if (mue.getMessage().getGroupName().equals(groupMessage)) {
                me.add(mue.getMessage());
            }
            if (!hmmue.containsKey(mue.getMessage().getGroupName())) {
                hmmue.put(mue.getMessage().getGroupName(), mue.getMessage().getTarget());
            }
        }
        MessageDisplayList mdl = new MessageDisplayList(hmmue, newMessages, me);
        return mdl;
    }*/
    /**
     *
     * @param m
     * @param friends
     * @return
     */
    @Override
    public boolean sendNotifToFriends(NotificationEntity m, List<FriendEntity> friends) {
        NotificationEntity me = (NotificationEntity) mgDao.findNotifByID(m.getId());
        String tmpMessageGroup = "notification";

        for (FriendEntity receiver : friends) {
            UserEntity tmpR;
            if (receiver.getFriend().getUsername().equals(m.getSendBy().getUsername())) {

                tmpR = receiver.getOwner();
            } else {
                tmpR = receiver.getFriend();
            }
            if (tmpR != null) {
                MessageUserEntity tmpMUE = new MessageUserEntity(me, tmpR);
                tmpMUE = mgUserDao.save(tmpMUE);
                tmpMUE = mgUserDao.findByID(tmpMUE.getId());
                mgDao.addTarget(me, tmpMUE);
            }

        }

        me.setGroupName(tmpMessageGroup);
        mgDao.update(me);

        return true;
    }

    /**
     *
     * @param userID
     * @return
     */
    @Override
    public List<MessageUserEntity> getNotificationByUser(Long userID) {
        return this.mgUserDao.findNewMessageForUserAndGroupMessage(userID, "notification");
    }

    /**
     *
     * @param p
     * @param subject
     * @param ue
     * @return
     */
    @Override
    public NotificationEntity addNotification(PostEntity p, String subject, UserEntity ue) {

        if (ue != null && ue.getId() != null) {
            MessageEntity me = new NotificationEntity(p, "notif", ue);
            me = mgDao.save(me);
            return (NotificationEntity) me;
        }
        return null;
    }

    @Override
    public void sendToMails(String message, String osef, Long userId, List<String> targets) {
        MessageEntity m = this.add(message, osef, userId);
        this.sendToMails(m, targets);
    }

    @Override
    public List<GroupListNewMessages> findGroupMessageByUserID(Long userID) {
        List<GroupListNewMessages> result = new ArrayList<>(); 
        for (MessageUserEntity mue : this.mgUserDao.findAllMessageRByUserID(userID)) {
            boolean found = false;
            for (GroupListNewMessages glm : result) {
                //System.err.println(mue.getMessage().getGroupName());

                if (glm.getGroupName().equals(mue.getMessage().getGroupName())) {
                    if (mue.isNewMessage()) {
                        glm.setNbNewsMessages(glm.getNbNewsMessages() + 1);
                    }
                   
                    found = true;
                }

            }
            if (!found || result.isEmpty()) {
                int x = 0;
                if (mue.isNewMessage()) {
                    x++;
                }
                GroupListNewMessages tmp = new GroupListNewMessages(mue.getMessage().getGroupName(), x);
                for(MessageUserEntity tmpMue : mue.getMessage().getTarget()){
                    tmp.addUsername(tmpMue.getUser().getUsername());
                } 
                result.add(tmp);

            }
        }
        return result;
    }

    @Override
    public List<MessageUserEntity> findMessageUserByGroupName(Long userID,String groupName) {
        return mgUserDao.findNewMessageForUserAndGroupMessage(userID, groupName);
    }

    @Override
    public void messageRead(Long userID, String groupMessage) {
        this.messageRead(this.userDao.findByID(userID), groupMessage);
    }

}
