/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import servicesSecondaire.PhotoService;
import dao.ExperienceDAO;
import dao.FriendDAO;
import dao.FriendEntity;
import dao.MediaEntity;
import dao.PhotoEntity;
import dao.PhysicalDAO;
import dao.PostDAO;
import dao.UserDAO;
import dao.UserEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import servicesSecondaire.ProfileElementaire;
import servicesSecondaire.PostService2;
import servicesSecondaire.UserService2;

/**
 *
 * @author Karl Lauret
 */
@Stateless
public class UserServiceImpl implements UserService {

    @EJB
    PostService postService;

    @EJB
    PostService2 postService2;

    @EJB
    UserService2 userService;

    @EJB
    PhotoService photoService;

    @EJB
    ProfileElementaire profileElementaire;

    /**
     *
     * @param email
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @return
     */
    @Override
    public Long add(String email, String username, String password, String firstName, String lastName) {
        UserEntity u = userService.create(email, username, password, firstName, lastName);

        if (u == null) {
            return null;
        }

        //Profile picture
        MediaEntity m = photoService.createDefaultPhoto(u, "Default profile Picture", "Profile Picture", "/Medias/defaulProfile.jpg");
        postService.createPost(m, u, u, false);
        u.getProfile().setPictureProfile(m);

        //Profile cover picture
        m = photoService.createDefaultPhoto(u, "Default Cover Picture", "Cover Picture", "/Medias/defaulProfile.jpg");
        postService.createPost(m, u, u, false);
        u.getProfile().setPictureCover(m);

        profileElementaire.update(u.getProfile());
        postService2.createDefaultAlbums(u);

        return u.getId();
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
        UserEntity friend = userService.findByID(friendId);
        UserEntity owner = userService.findByID(ownerId);
        FriendEntity fe = userService.findByFriendShip(friendId, ownerId);//Variables have bad Name the order doesn't matter for the find
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
        UserEntity friend = userService.findByUsername(username1);
        UserEntity owner = userService.findByUsername(username2);
        FriendEntity fe = userService.findByFriendShip(friend.getId(), owner.getId());//Variables have bad Name the order doesn't matter for the find
        return friend != null && owner != null && fe != null && fe.getAccepted();
    }

    /**
     *
     * @param username1
     * @param username2
     * @return
     */
    @Override
    public boolean isFriendOrHasRequested(String username1, String username2) {
        if (username1 == null || username1.isEmpty() || username2 == null || username2.isEmpty()) {
            return false;
        }
        if (username1.equals(username2)) {
            return false;
        }
        UserEntity friend = userService.findByUsername(username1);
        UserEntity owner = userService.findByUsername(username2);
        FriendEntity fe = userService.findByFriendShip(friend.getId(), owner.getId());//Variables have bad Name the order doesn't matter for the find
        return friend != null && owner != null && fe != null;
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
            System.err.println("accept Friendship false");
            return false;
        }
        FriendEntity fe = userService.findByFriendShip(acceptedBy, acceptedFrom);

        return userService.updateFriendShip(fe);
    }

    @Override
    public boolean removeFriend(Long userID1, Long userID2) {
        if (userID1.equals(userID2)) {
            return false;
        }
        FriendEntity fe = userService.findByFriendShip(userID1, userID2);
        userService.removeFriend(fe);
        return false;
    }

    @Override
    public List<UserEntity> getFriendListByUsername(String askedBy, String friendOf) {
        List<FriendEntity> friends = userService.findFriendsListFriendByUserUsername(friendOf);
        List<UserEntity> results = new ArrayList<>();
        if (askedBy != null) {
            for (FriendEntity friend : friends) {
                UserEntity ue;
                if (friend.getFriend().getUsername().equals(friendOf)) {
                    ue = friend.getOwner();
                } else {
                    ue = friend.getFriend();
                }

                results.add(ue);
            }
        } else {
            for (FriendEntity friend : friends) {
                UserEntity ue;
                if (friend.getFriend().getUsername().equals(friendOf)) {
                    ue = friend.getOwner();
                } else {
                    ue = friend.getFriend();
                }
                results.add(ue);
            }
        }
        return results;
    }

}
