/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Karl Lauret
 */
@Local
public interface FriendDAO {

    /**
     *
     * @param u
     * @return
     */
    public FriendEntity save(FriendEntity u);

    /**
     *
     * @param u
     */
    public void update(FriendEntity u);

    /**
     *
     * @param u
     */
    public void delete(FriendEntity u);

    /**
     *
     * @param friendId
     * @return
     */
    public FriendEntity findByID(Long friendId);

    /**
     * confirm the friendship
     * @param fe friendEntity
     */
    public void acceptFriendship(FriendEntity fe);
    
    /**
     * return the list of friends to accept
     * @param userID
     * @return list of friends to accept
     */
    public List<FriendEntity> findFriendToAcceptFromUserID(Long userID);

    /**
     * return the list of friends for a given user id
     * @param userID
     * @return list of friends
     */
    public List<FriendEntity> findFriendsByUserID(Long userID);

    /**
     * return the friend relation between 2 users
     * @param userID1   
     * @param userID2 user2 
     * @return
     */
    public FriendEntity findByFriendShip(Long userID1, Long userID2);
    
    /**
     *
     * @param userID
     * @return
     */
    public List<Long> findUsersIdOfFriends(Long userID);
}
