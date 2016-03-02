/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.MessageDAO;
import dao.MessageEntity;
import dao.MessageUserDAO;
import dao.MessageUserEntity;
import dao.NotificationEntity;
import dao.PostEntity;
import dao.UserDAO;
import dao.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Karl Lauret
 */
@Stateless
public class MessageElementaireImpl implements MessageElementaire {

    @EJB
    UserDAO userDao;

    @EJB
    MessageDAO mgDao;

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
     * @param ue
     * @param groupMessage
     */
    @Override
    public void messageRead(UserEntity ue, String groupMessage) {
        List<MessageUserEntity> mues = mgUserDao.findMessageForUserAndGroupMessage(ue.getId(), groupMessage);
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
    @Override
    public List<MessageUserEntity> getNotificationByUser(Long userID) {
        return this.mgUserDao.findMessageForUserAndGroupMessage(userID, "notification");
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
    public List<String> findGroupMessageByUserID(Long userID) {
        List<String> result = new ArrayList<>();
        for (MessageUserEntity mue : this.mgUserDao.findAllMessageRByUserID(userID)) {
            boolean found = false;
            for (String glm : result) {
                //System.err.println(mue.getMessage().getGroupName());

                if (glm.equals(mue.getMessage().getGroupName())) {
                    found = true;
                }

            }
            if (!found || result.isEmpty()) {

                result.add(mue.getMessage().getGroupName());
            }
        }
        return result;
    }

    @Override
    public List<MessageUserEntity> findMessageUserByGroupName(Long userID, String groupName) {
        return mgUserDao.findMessageForUserAndGroupMessage(userID, groupName);
    }

    @Override
    public void messageRead(Long userID, String groupMessage) {
        this.messageRead(this.userDao.findByID(userID), groupMessage);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void addTargetToMessage(UserEntity ue, MessageEntity me) {

        MessageUserEntity tmpMUE = new MessageUserEntity(me, ue);
        tmpMUE = mgUserDao.save(tmpMUE);
        tmpMUE = mgUserDao.findByID(tmpMUE.getId());
        mgDao.addTarget(me, tmpMUE);

    }

    @Override
    public void updateGroupName(MessageEntity me, String groupName) {

        me.setGroupName(groupName);
        mgDao.update(me);

    }

    @Override
    public List<String> findTargetsOfMessagesByGroupName(String groupName) {
        List<String> result = new ArrayList<>();
        MessageEntity me = this.mgDao.findByMessageGroup(groupName);
        if (me != null) {
            for (MessageUserEntity target : me.getTarget()) {
                result.add(target.getUser().getUsername());
            }
        }
        return result;
    }

    @Override
    public Integer findNbOfNewMessageForGroupName(Long userID, String groupName) {
        return this.mgUserDao.findNewMessageForUserAndGroupMessage(userID, groupName).size();

    }

    @Override
    public Integer findNbOfNewMessageForUserID(Long userID) {
        return this.mgUserDao.findNewMessageForUser(userID).size();

    }

    @Override
    public Integer findNbOfNewNotificationForUserID(Long userID) {
        return this.mgUserDao.findNewNotificationForUser(userID).size();
    }

}
