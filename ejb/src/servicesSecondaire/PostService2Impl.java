/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import services.*;
import dao.AlbumEntity;
import dao.CommentEntity;
import dao.FriendDAO;
import dao.FriendEntity;
import dao.MediaEntity;
import dao.NewsEntity;
import dao.NotificationEntity;
import dao.PostDAO;
import dao.PostEntity;
import dao.RecomendationEntity;
import dao.UserDAO;
import dao.UserEntity;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Karl Lauret
 */
@Stateless
public class PostService2Impl implements servicesSecondaire.PostService2 {

    @EJB
    PostDAO postDao;

    @EJB
    PhotoService photoService;

    @EJB
    FriendDAO friendDAO;

    @EJB
    MessageService messageService;

    @EJB
    UserDAO userDao;

    @Override
    public PostEntity createPost(PostEntity p, UserEntity ue, UserEntity target) {
        Calendar c = Calendar.getInstance();
        p.setCreatedDate(new Date(c.getTimeInMillis()));
        p.setCreatedTime(new Time(c.getTimeInMillis()));
        p.setAuthor(ue);

        p.setTarget(target);

        Long id = postDao.save(p);
        p.setId(id);
        NotificationEntity not = messageService.addNotification(p, "notification", ue);
        List<FriendEntity> fe = friendDAO.findFriendsByUserID(ue.getId());
        messageService.sendNotifToFriends(not, fe);
        return p;
    }

    /**
     *
     * @param postID
     * @return
     */
    @Override
    public PostEntity findByID(Long postID) {
        return postDao.findByPostId(postID);
    }

    @Override
    public List<PostEntity> findByUsernameAndType(String username, String type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PostEntity> getRecentPostFromUsersID(List<Long> l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PostEntity> getNextPostFromUsersID(List<Long> l, Long postID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PostEntity> getNextRecommendationFromUsersID(List<Long> l, Long postID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getRecentRecommendationFromUsersID(List<Long> l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PostEntity findAlbum(Long id, String type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PostEntity findAlbum(Long id, Long albumId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(PostEntity postEntity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createDefaultAlbums(UserEntity u) {
        AlbumEntity album = new AlbumEntity("Default", "Default album", u);
        postDao.save(album);

        album = new AlbumEntity("News", "News album", u);
        postDao.save(album);

        album = new AlbumEntity("Profile", "Profile album", u);
        postDao.save(album);
    }

}