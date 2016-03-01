/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesTertiaire;

import services.*;
import dao.AlbumEntity;
import dao.FriendDAO;
import dao.FriendEntity;
import dao.NotificationEntity;
import dao.PostDAO;
import dao.PostEntity;
import dao.UserDAO;
import dao.UserEntity;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import servicesSecondaire.MessageElementaire;

/**
 *
 * @author Karl Lauret
 */
@Stateless
public class PostService2Impl implements servicesTertiaire.PostService2 {

    @EJB
    PostDAO postDao;

    @EJB
    FriendDAO friendDAO;

    @EJB
    MessageElementaire messageElementaire;
    @EJB
    MessageService messageService;//TODO COMPISIT HERE WTF
    @EJB
    UserDAO userDao;

    @Override
    public PostEntity createPost(PostEntity p, UserEntity ue, UserEntity target, Boolean display) {
        Calendar c = Calendar.getInstance();
        p.setCreatedDate(new Date(c.getTimeInMillis()));
        p.setCreatedTime(new Time(c.getTimeInMillis()));
        p.setAuthor(ue);
        p.setDisplay(display);
        p.setTarget(target);

        Long id = postDao.save(p);
        p.setId(id);
        NotificationEntity not = messageElementaire.addNotification(p, "notification", ue);
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
        return postDao.findByUsernameAndType(username, type);
    }

    @Override
    public List<PostEntity> getRecentPostFromUsersID(List<Long> l) {
        return postDao.getRecentPostFromUsersID(l);
    }

    @Override
    public List<PostEntity> getNextPostFromUsersID(List<Long> l, Long postID) {
        return postDao.getNextPostFromUsersID(l, postID);
    }

    @Override
    public List<PostEntity> getNextRecommendationFromUsersID(List<Long> l, Long postID) {
        return postDao.getNextPostFromUsersID(l, postID);
    }

    @Override
    public List<PostEntity> getRecentRecommendationFromUsersID(List<Long> l) {
        return postDao.getRecentRecommendationFromUsersID(l);
    }

    @Override
    public AlbumEntity findAlbum(Long id, String type) {
        System.err.println(" userID = " +id +"; type"+type  );
        PostEntity post = postDao.findAlbum(id, type);
        System.err.println(" poste = " +post );
        /*AlbumEntity album = new AlbumEntity();
        album.setId(post.getId());
        album.setTitle(type);*/
        return (AlbumEntity)post;
    }

    @Override
    public List<PostEntity> loadMedias(Long albumId) {
        return postDao.loadMedias(albumId);
    }

    @Override
    public PostEntity findAlbum(Long id, Long albumId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public void createDefaultAlbums(UserEntity u) {
        AlbumEntity album = new AlbumEntity("DefaultAlbum", "Default album", u);
        album.setCover(u.getProfile().getPictureProfile());
        postDao.save(album);

        album = new AlbumEntity("NewsAlbum", "News album", u);
        album.setCover(u.getProfile().getPictureProfile());
        postDao.save(album);

        album = new AlbumEntity("ProfileAlbum", "Profile album", u);
        album.setCover(u.getProfile().getPictureProfile());
        postDao.save(album);
    }

}