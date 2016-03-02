/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.AlbumEntity;
import dao.FriendDAO;
import dao.PostDAO;
import dao.PostEntity;
import dao.UserDAO;
import dao.UserEntity;
import java.util.List;
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
        PostEntity post = postDao.findAlbum(id, type);
        return (AlbumEntity) post;
    }

    @Override
    public List<PostEntity> loadMedias(Long albumId) {
        return postDao.loadMedias(albumId);
    }

    @Override
    public List<PostEntity> loadMedias(String username) {
        return postDao.loadMedias(username);
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


    @Override
    public Long save(PostEntity p) {
        return postDao.save(p);
    }

}
