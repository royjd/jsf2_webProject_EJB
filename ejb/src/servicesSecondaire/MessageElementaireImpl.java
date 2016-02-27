/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import commun.GroupListNewMessages;
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
                for (MessageUserEntity tmpMue : mue.getMessage().getTarget()) {
                    tmp.addUsername(tmpMue.getUser().getUsername());
                }
                result.add(tmp);

            }
        }
        return result;
    }

    @Override
    public List<MessageUserEntity> findMessageUserByGroupName(Long userID, String groupName) {
        return mgUserDao.findNewMessageForUserAndGroupMessage(userID, groupName);
    }

    @Override
    public void messageRead(Long userID, String groupMessage) {
        this.messageRead(this.userDao.findByID(userID), groupMessage);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void addTargetToMessage(UserEntity ue,MessageEntity me) {

        MessageUserEntity tmpMUE = new MessageUserEntity(me, ue);
        tmpMUE = mgUserDao.save(tmpMUE);
        tmpMUE = mgUserDao.findByID(tmpMUE.getId());
        mgDao.addTarget(me, tmpMUE);

    }
    
    @Override
    public void updateGroupName(MessageEntity me,String groupName){
                   
        me.setGroupName(groupName);
        mgDao.update(me); 
        
    }

}
