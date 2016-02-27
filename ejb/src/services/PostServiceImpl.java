/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import servicesSecondaire.PhotoService;
import dao.AlbumEntity;
import dao.CommentEntity;
import dao.MediaEntity;
import dao.NewsEntity;
import dao.PhotoEntity;
import dao.PostEntity;
import dao.RecomendationEntity;
import dao.UserDAO;
import dao.UserEntity;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.Part;
import servicesSecondaire.UserService2;
import commun.Files;

/**
 *
 * @author Karl Lauret
 */
@Stateless
public class PostServiceImpl implements PostService {

    @EJB
    servicesSecondaire.PhotoService photoService;


    @EJB
    servicesTertiaire.PostService2 postService;


    @EJB
    UserService2 userService2;

    @Override
    public PostEntity createComment(String message, Long authorID, Long parentID, Long mainID) {
        PostEntity parent = postService.findByID(parentID);
        PostEntity main = postService.findByID(mainID);
        UserEntity author = userService2.findByID(authorID);
        CommentEntity comment = new CommentEntity();
        comment.setBody(message);
        comment.setPostParent(parent);
        comment.setPostMain(main);
        return postService.createPost(comment, author, parent.getAuthor(), false);

    }

    @Override
    public PostEntity createNews(String title, String message, Part file, String contextPath, String authorUsernale, String targetUsernale) {
        UserEntity author = userService2.findByUsername(authorUsernale);
        if (author == null) {
            return null;
        }
        UserEntity target;
        if (!Objects.equals(authorUsernale, targetUsernale)) {
            target = userService2.findByUsername(targetUsernale);
            if (target == null) {
                return null;
            }
        } else {
            target = author;
        }
        return this.createNews(title, message, file, contextPath, author, target);

    }

    private PostEntity createNews(String title, String message, Part file, String contextPath, UserEntity author, UserEntity target) {
        NewsEntity news;
        if (file != null && !file.getName().equals("") && contextPath != null) {
            AlbumEntity album = postService.findAlbum(author.getId(), "NewsAlbum");
            PostEntity post = photoService.createPhoto(album, author, file, contextPath, false);
            if (post != null && post.getId() != null) {
                news = new NewsEntity(title, message, author, target, (MediaEntity) post);

            } else {
                news = new NewsEntity(title, message, author, target);
            }

        } else {
            news = new NewsEntity(title, message, author, target);
        }
        return postService.createPost(news, author, target, true);
    }

    @Override
    public PostEntity createNews(String title, String message, Part file, String contextPath, Long authorID, Long targetID) {
        //if username go on wall of the use matching the username
        //target is also the user matching the username        if (pathVariables.containsKey("username")) {
        UserEntity author = userService2.findByID(authorID);
        if (author == null) {
            return null;
        }
        UserEntity target;
        if (!Objects.equals(authorID, targetID)) {
            target = userService2.findByID(targetID);
            if (target == null) {
                return null;
            }
        } else {
            target = author;
        }
        return this.createNews(title, message, file, contextPath, author, target);

    }

    @Override
    public boolean createAlbum(String title, String description, String localisation, Long authorId, List<Files> files, String contextPath) {

        UserEntity author = userService2.findByID(authorId);

        if (author == null) {
            return false;
        }

        AlbumEntity album = new AlbumEntity(title, description, localisation, author);
        PostEntity p = postService.createPost(album, author, author, true);
        if (p == null) {
            return false;
        }
        album.setId(p.getId());

        return photoService.createPhoto(album, author, files, contextPath, true) != null;

    }

   
    @Override
    public PostEntity addPhotoToAlbum(String username, Files file, String path, Long albumId) {
        UserEntity author = userService2.findByUsername(username);
        if (author == null) {
            return null;
        }
        PostEntity post = postService.findByID(albumId);
        if (post == null) {
            return null;
        }
        return photoService.createPhoto((AlbumEntity) post, author, file, path, true);
    }



    /**
     *
     * @param userID
     * @return
     */
    @Override
    public List<PostEntity> getRecentPostFromFriendAndMe(Long userID) {
        List<Long> l = userService2.findUsersIdOfFriends(userID);
        if (l == null) {
            l = new ArrayList<>();
        }
        l.add(userID);//we add he friend owner into it
        return postService.getRecentPostFromUsersID(l);
    }

    /**
     *
     * @param userID
     * @param postID
     * @return
     */
    @Override
    public List<PostEntity> getNextPostFromFriendAndMe(Long userID, Long postID) {
        List<Long> l = userService2.findUsersIdOfFriends(userID);
        l.add(userID);//we add he friend owner into it
        return postService.getNextPostFromUsersID(l, postID);
    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public List<PostEntity> getRecentPostFromMe(String username) {
        UserEntity ue = this.userService2.findByUsername(username);
        List<Long> l = new ArrayList<>();
        l.add(ue.getId());
        return postService.getRecentPostFromUsersID(l);
    }

    /**
     *
     * @param username
     * @param postID
     * @return
     */
    @Override
    public List<PostEntity> getNextPostFromUserID(String username, Long postID) {
        UserEntity ue = this.userService2.findByUsername(username);
        List<Long> l = new ArrayList<>();
        l.add(ue.getId());
        return postService.getNextPostFromUsersID(l, postID);
    }

    /**
     *
     * @param username
     * @param postID
     * @return
     */
    @Override
    public List<PostEntity> getNextRecommendationFromUserID(String username, Long postID) {
        UserEntity ue = this.userService2.findByUsername(username);
        List<Long> l = new ArrayList<>();
        l.add(ue.getId());
        return postService.getNextRecommendationFromUsersID(l, postID);
    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public List<PostEntity> getRecentRecommendationFromUserID(String username) {
        UserEntity ue = this.userService2.findByUsername(username);
        List<Long> l = new ArrayList<>();
        l.add(ue.getId());
        return postService.getRecentRecommendationFromUsersID(l);
    }

    @Override
    public PostEntity createRecommendation(String title, String message, Long authorID, String targetUsername) {
        //if username go on wall of the use matching the username
        //target is also the user matching the username        if (pathVariables.containsKey("username")) {
        UserEntity author = userService2.findByID(authorID);
        if (author == null) {
            return null;
        }
        UserEntity target = userService2.findByUsername(targetUsername);
        if (target == null) {
            return null;
        }

        RecomendationEntity re = new RecomendationEntity(title, message);
        return postService.createPost(re, author, target, true);

    }

   

}
