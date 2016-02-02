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
public interface PostDAO {

    /**
     *
     * @param p
     * @return
     */
    public Long save(PostEntity p);

    /**
     *
     * @param p
     */
    public void update(PostEntity p);

    /**
     *
     * @param p
     */
    public void delete(PostEntity p);

    /**
     * return the post matching the given post id
     * @param postId
     * @return PostEntity
     */
    public PostEntity findByPostId(Long postId);
    
    /**
     * return the album attached to the given used and matching the given type
     * 
     * @param userId the owner of the album
     * @param type the type of the album
     * @return PostEntity
     */
    public PostEntity findAlbum(Long userId , String type);
    
    /**
     * return the album matching the given album id
     * 
     * @param userId the owner id
     * @param albumId 
     * @return PostEntity
     */
    public PostEntity findAlbum(Long userId, Long albumId);

    /**
     * return the list of post matching the type given and owned by the given user
     * 
     * @param username 
     * @param type
     * @return
     */
    public List<PostEntity> findByUsernameAndType(String username, String type);

    /**
     * return the 5 most recent post posted 
     * by the used present into the given list of user's id
     * 
     * @param usersID
     * @return List
     */
    public List<PostEntity> getRecentPostFromUsersID(List<Long> usersID);

    /**
     * return 5 posts older than the given post  
     * and posted by the user present in the given list of user's id
     * 
     * @param userID
     * @param postID
     * @return
     */
    public List<PostEntity> getNextPostFromUsersID(List<Long> userID, Long postID);

    /**
     * return 5 recommendation older than the given post 
     * and received by the user present in the given list of user's id
     * @param l
     * @param postID
     * @return
     */
    public List<PostEntity> getNextRecommendationFromUsersID(List<Long> l, Long postID);
    
    /**
     * return the 5 most recent recommendation posted 
     * and received by the user present in the given list of user's id
     * 
     * @param usersID
     * @return
     */
    public List<PostEntity> getRecentRecommendationFromUsersID(List<Long> usersID);


}
