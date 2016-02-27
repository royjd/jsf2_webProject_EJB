/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import commun.Files;
import dao.AlbumEntity;
import dao.CommentEntity;
import dao.MediaEntity;
import dao.NewsEntity;
import dao.PostEntity;
import dao.RecomendationEntity;
import dao.UserEntity;
import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.Part;

/**
 *
 * @author Karl Lauret
 */
@Local
public interface PostService {

  

    public PostEntity createComment(String message, Long authorID, Long parentID, Long mainID);

   
 
   
    //public AlbumEntity createAlbum(String title, String description, String localisation, Long authorId);
    
    public boolean createAlbum(String title, String description, String localisation, Long authorId, List<Files> files, String contextPath);
    
    public PostEntity addPhotoToAlbum(String username, Files file, String path, Long albumId);
   
    /**
     * return the most recent post from the given user id and his friends
     *
     * @param id
     * @return List
     */
    public List<PostEntity> getRecentPostFromFriendAndMe(Long id);

    /**
     * return the next post from the friend and me matching my id and after the
     * post id given
     *
     * @param id
     * @param postId
     * @return List
     */
    public List<PostEntity> getNextPostFromFriendAndMe(Long id, Long postId);

    /**
     * return recent posts from user matching the given user's username
     *
     * @param username
     * @return List
     */
    public List<PostEntity> getRecentPostFromMe(String username);

    /**
     * return the next post from the user matching the username and after the
     * given postid
     *
     * @param username
     * @param postId
     * @return List
     */
    public List<PostEntity> getNextPostFromUserID(String username, Long postId);

    /**
     * return the next recommendation from the user matching the username and
     * after the given postid
     *
     * @param username
     * @param postId
     * @return
     */
    public List<PostEntity> getNextRecommendationFromUserID(String username, Long postId);

    /**
     * return recent posts from user matching the given user's username
     *
     * @param username
     * @return
     */
    public List<PostEntity> getRecentRecommendationFromUserID(String username);


    public PostEntity createNews(String title, String message, Part file, String contextPath, Long authorID, Long targetID);
 

    public PostEntity createRecommendation(String title, String message, Long authorID, String targetUsername);

    public PostEntity createNews(String title, String message, Part file, String realPath, String authorUsername, String targetUsername);


}
