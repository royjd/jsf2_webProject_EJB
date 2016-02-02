/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.PostEntity;
import dao.UserEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Karl Lauret
 */
@Local
public interface PostService2 {

    /**
     * Create a post
     * @param p
     * @param ue
     * @param target
     * @return 
     */
    public PostEntity createPost(PostEntity p, UserEntity ue, UserEntity target);
    
    public PostEntity findByID(Long postID);

    public void update(PostEntity postEntity);

    public List<PostEntity> getRecentPostFromUsersID(List<Long> l);

    public List<PostEntity> getNextPostFromUsersID(List<Long> l, Long postID);

    public List<PostEntity> getNextRecommendationFromUsersID(List<Long> l, Long postID);

    public Object getRecentRecommendationFromUsersID(List<Long> l);

    public PostEntity findAlbum(Long id, String type);

    public PostEntity findAlbum(Long id, Long albumId);

    public List<PostEntity> findByUsernameAndType(String username, String type);

    public void createDefaultAlbums(UserEntity u);
    
}