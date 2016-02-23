/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.AlbumEntity;
import dao.CommentEntity;
import dao.MediaEntity;
import dao.NewsEntity;
import dao.PostEntity;
import dao.RecomendationEntity;
import dao.UserEntity;
import java.io.File;
import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.Part;

/**
 *
 * @author Karl Lauret
 */
@Local
public interface PostService {

    /**
     * return the album matching the type and the given post id
     *
     * @param id post id
     * @param type of the album
     * @return
     */
    public PostEntity findAlbum(Long id, String type);

    /**
     * return the album matching the album id and the given user id
     *
     * @param userId
     * @param albumId
     * @return PostEntity
     */
    public PostEntity findAlbum(Long userId, Long albumId);

    /**
     * create the comment
     *
     * @param body the comment body
     * @param author the comment's author
     * @param parentId the parent post
     * @param mainId the main post
     * @return PostEntity
     */
    public PostEntity createComment(String body, UserEntity author, long parentId, long mainId);

    /**
     * create a comment
     *
     * @param comment
     * @param author
     * @return PostEntity
     */
    public PostEntity createComment(CommentEntity comment, UserEntity author);

    public PostEntity createComment(String message, Long authorID, Long parentID, Long mainID);

    /**
     * create a news
     *
     * @param news
     * @param author
     * @param target
     * @return PostEntity
     */
    public PostEntity createNews(NewsEntity news, UserEntity author, UserEntity target);

    /**
     * create a recommendation
     *
     * @param recom
     * @param author
     * @param target
     * @return PostEntity
     */
    public PostEntity createRecommendation(RecomendationEntity recom, UserEntity author, UserEntity target);

    /**
     * createVideo
     *
     * @param media
     * @param author
     * @return PostEntity
     */
    public PostEntity createVideao(MediaEntity media, UserEntity author);

    /**
     * create album
     *
     * @param album
     * @param author
     * @return PostEntity
     */
    public PostEntity createAlbum(AlbumEntity album, UserEntity author);

    public AlbumEntity createAlbum(String title, String description, String localisation, Long authorId);
    
    public boolean addPhotoToAlbum(String username, Part file, String path, Long albumId);
    /**
     * return the posts attached to the user and matching the type given
     *
     * @param username
     * @param type
     * @return List
     */
    public List<PostEntity> getPostFromUserAndType(String username, String type);

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

    /**
     * create a news
     *
     * @param news
     * @param ue
     * @param target
     * @param mediaEntity
     * @return PostEntity
     */
    public PostEntity createNews(NewsEntity news, UserEntity ue, UserEntity target, PostEntity mediaEntity);

    public PostEntity createNews(String title, String message, Part file, String contextPath, Long authorID, Long targetID);
    public PostEntity findByID(Long postID);

    public PostEntity createRecommendation(String title, String message, Long authorID, String targetUsername);

    public PostEntity createNews(String title, String message, Part file, String realPath, String authorUsername, String targetUsername);


}
