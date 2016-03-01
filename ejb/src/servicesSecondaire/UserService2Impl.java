/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import servicesTertiaire.PostService2;
import dao.ExperienceDAO;
import dao.FriendDAO;
import dao.FriendEntity;
import dao.MediaEntity;
import dao.PhotoDAO;
import dao.PhotoEntity;
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
    PostService2 postService2;
    @EJB
    UserDAO userDao;

    @EJB
    PostDAO postDao;


    @EJB
    FriendDAO friendDao;
    @EJB
    PhotoDAO photoDao;
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
            this.createDefaultProfilePhotos(u);
            profileDao.update(u.getProfile());
            return u;
        }
        return null;
    }
 
    private void createDefaultProfilePhotos(UserEntity u) {

        //Profile picture
        PhotoEntity photo = new PhotoEntity("Profile Picture", "/Medias/defaulProfile.jpg");
        this.photoDao.save(photo);
        MediaEntity m = new MediaEntity("Default Profile Picture", "", u);
        m.setMediaType(photo);
        postService2.createPost(m, u, u, false);
        u.getProfile().setPictureProfile(m);

        //Profile cover picture
        PhotoEntity photo2 = new PhotoEntity("Cover Picture", "/Medias/defaulProfile.jpg");
        this.photoDao.save(photo2);
        MediaEntity m2 = new MediaEntity("Default Cover Picture", "", u);
        m2.setMediaType(photo2);
        postService2.createPost(m2, u, u, false);
        u.getProfile().setPictureCover(m2);

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
     * @param identifiant
     * @param password
     * @return
     */
    @Override
    public UserEntity isValidUser(String identifiant, String password) {
        return this.userDao.connect(identifiant, password);
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
        if (username1 == null || username1.isEmpty() || username2 == null || username2.isEmpty()) {
            return false;
        }
        if (username1.equals(username2)) {
            return false;
        }
        UserEntity friend = this.userDao.findByUsername(username1);
        UserEntity owner = this.userDao.findByUsername(username2);
        FriendEntity fe = this.friendDao.findByFriendShip(friend.getId(), owner.getId());//Variables have bad Name the order doesn't matter for the find
        return friend != null && owner != null && fe != null && fe.getAccepted();
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
    public List<FriendEntity> findFriendToAccept(Long userId) {

        return friendDao.findFriendToAcceptFromUserID(userId);
    }

    /**
     *
     * @param userID
     * @return
     */
    @Override
    public List<FriendEntity> findFriendsListFriendByUserID(Long userID) {
        return this.friendDao.findFriendsByUserID(userID);
    }

    @Override
    public List<FriendEntity> findFriendsListFriendByUserUsername(String username) {
        return this.friendDao.findFriendsByUserUsername(username);
    }

    /**
     *
     * @param userID
     * @return
     */
    @Override
    public List<UserEntity> findFriendsListUserByUserID(Long userID) {
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

    @Override
    public boolean existUser(String email, String username) {
        return (this.userDao.findByEmail(email) != null) || (this.userDao.findByUsername(username) != null);
    }

    @Override
    public List<Long> findUsersIdOfFriends(Long userID) {
        return friendDao.findUsersIdOfFriends(userID);
    } 

    @Override
    public FriendEntity findByFriendShip(Long friendId, Long ownerId) {
        return friendDao.findByFriendShip(friendId, ownerId);
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public boolean save(FriendEntity fe, UserEntity owner, UserEntity friend) {
        if (friend != null && owner != null && fe == null) {
            fe = new FriendEntity(owner, friend);
            friendDao.save(fe);
            //fe = this.friendDao.findByID(fe.getId());//TODO HERE NOT SURE THAT THIS IS NEEDED
            //this.userDao.addFriend(friend, owner, fe);
            return true;
        }
        return false;
    }

    @Override
    public FriendEntity findFriendByID(Long friendId) {
        return friendDao.findByID(friendId);
    }

    @Override
    public boolean updateFriendShip(FriendEntity fe) {
        if (fe != null) {
            fe.setAccepted(Boolean.TRUE);
            friendDao.update(fe);
            return true;
        }
        System.err.println("accept Friendship fe==null");
        return false;
    }

    @Override
    public boolean removeFriend(FriendEntity fe) {
        if (fe != null) {
            friendDao.delete(fe);
            return true;
        }
        return false;
    }

    @Override
    public List<FriendEntity> findFriendsByUserID(Long userID) {
        return this.friendDao.findFriendsByUserID(userID);
    }

    @Override
    public List<FriendEntity> findFriendsByUsername(String username) {
        return this.friendDao.findFriendsByUserUsername(username);
    }

}
