/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.ExperienceDAO;
import dao.FriendDAO;
import dao.FriendEntity;
import dao.PhysicalDAO;
import dao.PostDAO;
import dao.ProfileDAO;
import dao.UserDAO;
import dao.UserEntity;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Karl Lauret
 */
@Stateless
public class UserService2Impl implements UserService2 {

    @EJB
    UserDAO userDao;

    @EJB
    PostDAO postDao;

    @EJB
    PhotoService photoService;

    @EJB
    servicesSecondaire.PostService2 postService;

    @EJB
    FriendDAO friendDao;

    @EJB
    ProfileDAO profileDao;

    @EJB
    ExperienceDAO experienceDao;

    @EJB
    PhysicalDAO physicalDao;

    /**
     *
     * @param u
     * @return
     */
    @Override
    public UserEntity create(UserEntity u) {
        if (!existUser(u.getEmail(), u.getUsername())) {
            Long userId = userDao.save(u);
            u.setId(userId);
            u = userDao.findByID(u.getId());
            photoService.createDefaultProfilePhotos(u);
            profileDao.update(u.getProfile());
            return u;
        }
        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public UserEntity findByID(Long id) {
        return userDao.findByID(id);
    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public UserEntity findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     *
     * @param email
     * @return
     */
    @Override
    public UserEntity findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    /**
     *
     * @param u
     * @return
     */
    @Override
    public boolean delete(UserEntity u) {
        if (this.userDao.findByID(u.getId()) != null) {
            userDao.delete(u);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param email
     * @param password
     * @return
     */
    @Override
    public UserEntity isValidUser(String email, String password) {
        UserEntity ue = this.userDao.findByEmail(email);
        if (ue != null) {
            try {
                if (ue.isValidPassword(password)) {
                    return ue;
                }
                return null;
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(UserService2Impl.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     *
     * @param ownerId
     * @param friendId
     * @return
     */
    @Override
    public boolean addFriend(Long ownerId, Long friendId) {
        if (ownerId.equals(friendId)) {
            return false;
        }
        UserEntity friend = this.userDao.findByID(friendId);
        UserEntity owner = this.userDao.findByID(ownerId);
        FriendEntity fe = this.friendDao.findByFriendShip(friendId, ownerId);//Variables have bad Name the order doesn't matter for the find
        if (friend != null && owner != null && fe == null) {
            fe = new FriendEntity(owner, friend);
            this.friendDao.save(fe);
            //fe = this.friendDao.findByID(fe.getId());//TODO HERE NOT SURE THAT THIS IS NEEDED
            //this.userDao.addFriend(friend, owner, fe);
            return true;
        }
        return false;
    }

    /**
     *
     * @param ownerId
     * @param friendId
     * @return
     */
    @Override
    public boolean isFriend(Long ownerId, Long friendId) {
        if (ownerId.equals(friendId)) {
            return false;
        }
        UserEntity friend = this.userDao.findByID(friendId);
        UserEntity owner = this.userDao.findByID(ownerId);
        FriendEntity fe = this.friendDao.findByFriendShip(friendId, ownerId);//Variables have bad Name the order doesn't matter for the find
        return friend != null && owner != null && fe != null;
    }

    /**
     *
     * @param username1
     * @param username2
     * @return
     */
    @Override
    public boolean isFriend(String username1, String username2) {
        if (username1.equals(username2)) {
            return false;
        }
        UserEntity friend = this.userDao.findByUsername(username1);
        UserEntity owner = this.userDao.findByUsername(username2);
        FriendEntity fe = this.friendDao.findByFriendShip(friend.getId(), owner.getId());//Variables have bad Name the order doesn't matter for the find
        return friend != null && owner != null && fe != null;
    }

    /**
     *
     * @param friendId
     * @return
     */
    @Override
    public boolean removeFriend(Long friendId) {
        FriendEntity fe = this.friendDao.findByID(friendId);
        if (fe != null) {
            this.friendDao.delete(fe);
            return true;
        }
        return false;
    }

    /**
     *
     * @param param
     * @return
     */
    @Override
    public List<UserEntity> search(String param) {
        return this.userDao.findBysearch(param);
    }

    /**
     *
     * @param userId
     * @return
     */
    @Override
    public List<FriendEntity> getFriendToAccept(Long userId) {

        return friendDao.findFriendToAcceptFromUserID(userId);
    }

    /**
     *
     * @param acceptedBy
     * @param acceptedFrom
     * @return
     */
    @Override
    public boolean acceptFriendship(Long acceptedBy, Long acceptedFrom) {
        if (acceptedBy.equals(acceptedFrom)) {
            return false;
        }
        FriendEntity fe = this.friendDao.findByFriendShip(acceptedBy, acceptedFrom);
        if (fe != null) {
            fe.setAccepted(Boolean.TRUE);
            this.friendDao.update(fe);
            return true;
        }
        return false;
    }

    /**
     *
     * @param deniedBy
     * @param deniedFrom
     * @return
     */
    @Override
    public boolean deniedFriendship(Long deniedBy, Long deniedFrom) {
        if (deniedBy.equals(deniedFrom)) {
            return false;
        }
        FriendEntity fe = this.friendDao.findByFriendShip(deniedBy, deniedFrom);
        if (fe != null) {
            this.friendDao.delete(fe);
        }
        return false;

    }

    /**
     *
     * @param userID
     * @return
     */
    @Override
    public List<FriendEntity> getFriendsListFriendByUserID(Long userID) {
        return this.friendDao.findFriendsByUserID(userID);
    }

    /**
     *
     * @param userID
     * @return
     */
    @Override
    public List<UserEntity> getFriendsListUserByUserID(Long userID) {
        List<UserEntity> lue = new ArrayList<>();
        List<FriendEntity> lfe = this.friendDao.findFriendsByUserID(userID);
        for (FriendEntity fe : lfe) {
            if (fe.getFriend().getId().equals(userID)) {
                lue.add(fe.getOwner());
            } else {
                lue.add(fe.getFriend());
            }
        }
        return lue;

    }

    /**
     *
     * @param ue
     * @return
     */
    @Override
    public List<UserEntity> getFriendToAccept(UserEntity ue) {
        ue.getFriendToAccept().size();
        if (ue.getId() != null && ue.getFriendToAccept() != null) {

            return ue.getFriendToAccept();
        } else {
            return null;
        }
    }

    /**
     *
     * @param ue
     * @return
     */
    @Override
    public List<FriendEntity> getFriends(UserEntity ue) {
        if (ue.getId() != null) {
            List<FriendEntity> friends = new ArrayList<>();
            friends.addAll(ue.getFriends());
            friends.addAll(ue.getFriendedBy());
            return friends;
        }
        return null;
    }

    @Override
    public boolean existUser(String email, String username) {
        return (this.userDao.findByEmail(email) != null) || (this.userDao.findByUsername(username) != null);
    }

}
