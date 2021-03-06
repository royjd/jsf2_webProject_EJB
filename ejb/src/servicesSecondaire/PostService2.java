/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.AlbumEntity;
import dao.PostEntity;
import dao.UserEntity;
import java.io.File;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Karl Lauret
 */
@Local
public interface PostService2 {

    public PostEntity findByID(Long postID);


    public List<PostEntity> getRecentPostFromUsersID(List<Long> l);

    public List<PostEntity> getNextPostFromUsersID(List<Long> l, Long postID);

    public List<PostEntity> getNextRecommendationFromUsersID(List<Long> l, Long postID);

    public List<PostEntity> getRecentRecommendationFromUsersID(List<Long> l);

    public AlbumEntity findAlbum(Long id, String type);

    public PostEntity findAlbum(Long id, Long albumId);
    
    public List<PostEntity> loadMedias(Long albumId);

    public List<PostEntity> loadMedias(String username);
    
    public List<PostEntity> findByUsernameAndType(String username, String type);

    public void createDefaultAlbums(UserEntity u);

    public Long save(PostEntity p);

    
}
