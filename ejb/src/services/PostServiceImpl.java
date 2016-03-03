/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.AlbumEntity;
import dao.CommentEntity;
import dao.FriendEntity;
import dao.MediaEntity;
import dao.NewsEntity;
import dao.PostEntity;
import dao.RecomendationEntity;
import dao.UserEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.Part;
import servicesSecondaire.UserService2;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Map;

/**
 *
 * @author Karl Lauret
 */
@Stateless
public class PostServiceImpl implements PostService {

    @EJB
    servicesSecondaire.PhotoService photoService;

    @EJB
    servicesSecondaire.PostService2 postService2;

    @EJB
    MessageService messageService;
    @EJB
    UserService2 userService2;

    @Override
    public PostEntity createPost(PostEntity p, UserEntity ue, UserEntity target, Boolean display) {
        Calendar c = Calendar.getInstance();
        p.setCreatedDate(new Date(c.getTimeInMillis()));
        p.setCreatedTime(new Time(c.getTimeInMillis()));
        p.setAuthor(ue);
        p.setDisplay(display);
        p.setTarget(target);

        Long id = postService2.save(p);
        p.setId(id);
        List<FriendEntity> fe = userService2.findFriendsByUserID(ue.getId());
        messageService.sendNotifToFriends(fe, p, ue);
        return p;
    }

    @Override
    public PostEntity createComment(String message, Long authorID, Long parentID, Long mainID) {
        PostEntity parent = postService2.findByID(parentID);
        PostEntity main = postService2.findByID(mainID);
        UserEntity author = userService2.findByID(authorID);
        CommentEntity comment = new CommentEntity();
        comment.setBody(message);
        comment.setPostParent(parent);
        comment.setPostMain(main);
        return this.createPost(comment, author, parent.getAuthor(), false);

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
            AlbumEntity album = postService2.findAlbum(author.getId(), "NewsAlbum");
            PostEntity media = photoService.createPhoto(album, author, file, contextPath);
            PostEntity post = this.createPost(media, author, author, false);
            photoService.setAlbumCover(album, (MediaEntity) post);

            if (post != null && post.getId() != null) {
                news = new NewsEntity(title, message, author, target, (MediaEntity) post);

            } else {
                news = new NewsEntity(title, message, author, target);
            }

        } else {
            news = new NewsEntity(title, message, author, target);
        }
        return this.createPost(news, author, target, true);
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
    public boolean createAlbum(String title, String description, String localisation, Long authorId, Map<String, InputStream> files, String contextPath) {

        UserEntity author = userService2.findByID(authorId);

        if (author == null) {
            return false;
        }

        AlbumEntity album = new AlbumEntity(title, description, localisation, author);
        PostEntity p = this.createPost(album, author, author, true);
        if (p == null) {
            return false;
        }
        album.setId(p.getId());
        PostEntity post = null;
        for (Map.Entry<String, InputStream> entry : files.entrySet()) {
            String fileName = entry.getKey();
            InputStream inputStream = entry.getValue();
            post = photoService.createPhoto(album, author, fileName, inputStream, contextPath);
            post = this.createPost(post, author, author, false);

        }
        photoService.setAlbumCover(album, (MediaEntity) post);

        return post != null;
    }

    @Override
    public PostEntity addPhotoToAlbum(String username, String fileName, InputStream inputstream, String path, Long albumId) {
        UserEntity author = userService2.findByUsername(username);
        if (author == null) {
            return null;
        }
        PostEntity album = postService2.findByID(albumId);
        if (album == null) {
            return null;
        }
        MediaEntity media = (MediaEntity) photoService.createPhoto((AlbumEntity) album, author, fileName, inputstream, path);
        PostEntity post = this.createPost(media, author, author, true);
        photoService.setAlbumCover((AlbumEntity) album, (MediaEntity) post);
        return post;
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
        return postService2.getRecentPostFromUsersID(l);
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
        return postService2.getNextPostFromUsersID(l, postID);
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
        return postService2.getRecentPostFromUsersID(l);
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
        return postService2.getNextPostFromUsersID(l, postID);
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
        return postService2.getNextRecommendationFromUsersID(l, postID);
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
        return postService2.getRecentRecommendationFromUsersID(l);
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
        return this.createPost(re, author, target, true);

    }

}
