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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import servicesSecondaire.FriendService;
import servicesSecondaire.NotificationService;

/**
 *
 * @author Karl Lauret
 */
@Stateless
public class PostServiceImpl implements PostService {

    @EJB
    servicesSecondaire.PhotoService photoService;

    @EJB
    servicesSecondaire.NotificationService notificationService;

    @EJB
    servicesSecondaire.PostService2 postService;

    @EJB
    servicesSecondaire.FriendService friendService;

    @EJB
    UserDAO userDao;

    /**
     *
     * @param body
     * @param ue
     * @param parentId
     * @param mainId
     * @return
     */
    @Override
    public PostEntity createComment(String body, UserEntity ue, long parentId, long mainId) {
        CommentEntity comment = new CommentEntity();
        comment.setBody(body);
        PostEntity parent = postService.findByID(parentId);
        PostEntity main = postService.findByID(mainId);
        comment.setPostParent(parent);
        comment.setPostMain(main);
        return postService.createPost(comment, ue, parent.getAuthor());
    }

    /**
     *
     * @param comment
     * @param ue
     * @return
     */
    @Override
    public PostEntity createComment(CommentEntity comment, UserEntity ue) {
        PostEntity parent = postService.findByID(comment.getPostParent().getId());
        PostEntity main = postService.findByID(comment.getPostMain().getId());
        comment.setPostParent(parent);
        comment.setPostMain(main);
        return postService.createPost(comment, ue, parent.getAuthor());
    }

    /**
     *
     * @param news
     * @param author
     * @param target
     * @return
     */
    @Override
    public PostEntity createNews(NewsEntity news, UserEntity author, UserEntity target) {
        return postService.createPost(news, author, target);
    }

    /**
     *
     * @param news
     * @param author
     * @param target
     * @param mediaEntity
     * @return
     */
    @Override
    public PostEntity createNews(NewsEntity news, UserEntity author, UserEntity target, PostEntity mediaEntity) {
        if (mediaEntity.getId() != null) {
            news.setMedias((MediaEntity) mediaEntity);
            return postService.createPost(news, author, target);
        }
        return null;
    }

    /**
     *
     * @param recom
     * @param author
     * @param target
     * @return
     */
    @Override
    public PostEntity createRecommendation(RecomendationEntity recom, UserEntity author, UserEntity target) {
        return postService.createPost(recom, author, target);
    }

    /**
     *
     * @param album
     * @param author
     * @param file
     * @return
     */
    @Override
    public PostEntity createPhoto(AlbumEntity album, UserEntity author, File file) {
        PostEntity post = null;
        try {
            PhotoEntity photo = photoService.upload(file, author.getUsername(), album);
            if (photo != null) {
                MediaEntity media = new MediaEntity();
                if ("DefaulAlbum".equals(album.getTitle())) {
                    media = new MediaEntity(album.getTitle(), album.getBody(), author);
                }
                media.setMediaType(photo);
                media.setAlbum(album);
                post = postService.createPost(media, author, author);
            }
        } catch (IOException ex) {
            Logger.getLogger(PostServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return post;
    }

    /**
     *
     * @param album
     * @param author
     * @param files
     * @return
     */
    @Override
    public PostEntity createPhoto(AlbumEntity album, UserEntity author, File[] files) {
        PostEntity post = null;
        int i = 0;
        for (File file : files) {
            post = createPhoto(album, author, file);
            if (post != null) {
                MediaEntity media = new MediaEntity();
                media.setId(post.getId());
                if (i == files.length - 1) {
                    album = (AlbumEntity) postService.findAlbum(author.getId(), album.getId());
                    album.setCover(media);
                    postService.update((PostEntity) album);
                }
            }
            i++;
        }
        return post;
    }

    /**
     *
     * @param media
     * @param author
     * @return
     */
    @Override
    public PostEntity createVideao(MediaEntity media, UserEntity author) {
        return postService.createPost(media, author, author);
    }

    /**
     *
     * @param album
     * @param author
     * @return
     */
    @Override
    public PostEntity createAlbum(AlbumEntity album, UserEntity author) {
        return postService.createPost(album, author, author);
    }

    /**
     *
     * @param username
     * @param type
     * @return
     */
    @Override
    public List<PostEntity> getPostFromUserAndType(String username, String type) {
        return postService.findByUsernameAndType(username, type);
    }

    /**
     *
     * @param userID
     * @return
     */
    @Override
    public List<PostEntity> getRecentPostFromFriendAndMe(Long userID) {
        List<Long> l = friendService.findUsersIdOfFriends(userID);
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
        List<Long> l = friendService.findUsersIdOfFriends(userID);
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
        UserEntity ue = this.userDao.findByUsername(username);
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
        UserEntity ue = this.userDao.findByUsername(username);
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
        UserEntity ue = this.userDao.findByUsername(username);
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
    public Object getRecentRecommendationFromUserID(String username) {
        UserEntity ue = this.userDao.findByUsername(username);
        List<Long> l = new ArrayList<>();
        l.add(ue.getId());
        return postService.getRecentRecommendationFromUsersID(l);
    }

    /**
     *
     * @param id
     * @param type
     * @return
     */
    public PostEntity findAlbum(Long id, String type) {
        return postService.findAlbum(id, type);
    }

    /**
     *
     * @param id
     * @param albumId
     * @return
     */
    public PostEntity findAlbum(Long id, Long albumId) {
        return postService.findAlbum(id, albumId);

    }

}