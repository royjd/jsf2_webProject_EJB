/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import commun.FriendOrNot;
import dao.FriendEntity;
import dao.ProfileEntity;
import dao.UserEntity;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Karl Lauret
 */
@Local
public interface UserService {

    public Long add(String email, String username, String password, String firstName, String lastName);

    /**
     * delete the given user
     *
     * @param u
     * @return
     */
    public boolean delete(UserEntity u);

    /**
     * return the user if we find a user with the same email or username and
     * password
     *
     * @param identifiant
     * @param password
     * @return UserEntity if found null otherwise
     */
    public UserEntity isValidUser(String identifiant, String password);

    /**
     * return the user matching the given userID
     *
     * @param userID
     * @return UserEntity
     */
    public UserEntity findByID(Long userID);

    /**
     * return the user matching the given user's email
     *
     * @param email
     * @return UserEntity
     */
    public UserEntity findByEmail(String email);

    /**
     * return the user matching the given user's username
     *
     * @param username
     * @return UserEntity
     */
    public UserEntity findByUsername(String username);

    /**
     * add a friend
     *
     * @param ownerId
     * @param friendId
     * @return true if it was ok else false
     */
    public boolean addFriend(Long ownerId, Long friendId);

    /**
     * remove a friend
     *
     * @param friendId
     * @return true if it was ok else false
     */
    public boolean removeFriend(Long friendId);

    /**
     * search users by email or username
     *
     * @param param
     * @param id
     * @return List
     */
    public List<FriendOrNot> search(String param, Long id);

    /**
     * return friends to accept
     *
     * @param id
     * @return List
     */
    public List<FriendEntity> getFriendToAccept(Long id);

    /**
     * accept the friend request
     *
     * @param acceptedBy
     * @param acceptedFrom
     * @return
     */
    public boolean acceptFriendship(Long acceptedBy, Long acceptedFrom);

    /**
     * denied a friend request
     *
     * @param deniedBy
     * @param deniedFrom
     * @return
     */
    public boolean deniedFriendship(Long deniedBy, Long deniedFrom);

    /**
     * return the friends friended by the given user'id
     *
     * @param id
     * @return List
     */
    public List<FriendEntity> getFriendsListFriendByUserID(Long id);

    /**
     * return the list of user who are friend with the given userID
     *
     * @param userID
     * @return List
     */
    public List<UserEntity> getFriendsListUserByUserID(Long userID);

    /**
     * return friends to accept attached to the given user
     *
     * @param ue
     * @return List
     */
    public List<UserEntity> getFriendToAccept(UserEntity ue);

    /**
     * return friends of a user
     *
     * @param ue
     * @return List
     */
    public List<FriendEntity> getFriends(UserEntity ue);

    public List<FriendEntity> getFriends(Long userID) ;
            /**
     * test if two user are friend
     *
     * @param userID1
     * @param userID2
     * @return true if they are else false
     */

    public boolean isFriend(Long userID1, Long userID2);

    /**
     * test if two user are friend
     *
     * @param username1
     * @param username2
     * @return true if they are else false
     */
    public boolean isFriend(String username1, String username2);

}
