/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

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
     * accept the friend request
     *
     * @param acceptedBy
     * @param acceptedFrom
     * @return
     */
    public boolean acceptFriendship(Long acceptedBy, Long acceptedFrom);

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

    public boolean removeFriend(Long userID1, Long userID2);

    public List<UserEntity> getFriendListByUsername(String askedBy, String friendOf);
}
