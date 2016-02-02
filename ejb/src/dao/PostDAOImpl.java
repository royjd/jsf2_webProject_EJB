/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Karl Lauret
 */
@Stateless
public class PostDAOImpl implements PostDAO {

    @PersistenceContext(unitName = "fanfareFinalPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     *
     * @param em
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     *
     * @param p
     * @return
     */
    @Override
    public Long save(PostEntity p) {

        p = this.em.merge(p);

        this.em.persist(p);
        UserEntity ue = p.getAuthor();
        ue.addPost(p);
        this.em.merge(ue);
        ue = p.getTarget();
        ue.addPost(p);
        this.em.merge(ue);
        if (p instanceof CommentEntity) {
            PostEntity pe = ((CommentEntity) p).getPostParent();
            pe.addComment((CommentEntity) p);
            this.em.merge(pe);
        }
        return p.getId();
    }

    /**
     *
     * @param p
     */
    @Override
    public void update(PostEntity p) {
        this.em.merge(p);
    }

    /**
     *
     * @param p
     */
    @Override
    public void delete(PostEntity p) {
        p = this.em.merge(p);
        this.em.remove(p);
    }

    /**
     *
     * @param postId
     * @return
     */
    @Override
    public PostEntity findByPostId(Long postId) {
        try {
            return (PostEntity) this.em.createQuery("SELECT p FROM PostEntity p where p.id = :userId")
                    .setParameter("userId", postId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     *
     * @param username
     * @param type
     * @return
     */
    @Override
    public List<PostEntity> findByUsernameAndType(String username, String type) {
        try {
            switch (type) {
                case "photo":
                case "video": {
                    List<PostEntity> postEntities = this.em.createQuery("SELECT t FROM MediaEntity t where t.author.username = :value1 order by t.id desc")//t.type = photo when photo and video added
                            .setParameter("value1", username).getResultList();

                    return postEntities;
                }
                case "recommendation": {
                    List<PostEntity> postEntities = this.em.createQuery("SELECT t FROM RecomendationEntity t where t.target.username = :value1 order by t.id desc")//target here 
                            .setParameter("value1", username).getResultList();

                    return postEntities;
                }
                case "news": {
                    List<PostEntity> postEntities = this.em.createQuery("SELECT t FROM NewsEntity t where t.author.username = :value1 OR t.target.username order by t.id desc")//target or author
                            .setParameter("value1", username).getResultList();

                    return postEntities;
                }
                case "album": {
                    List<PostEntity> postEntities = this.em.createQuery("SELECT t FROM AlbumEntity t where t.author.username = :value1 order by t.id desc")//target or author
                            .setParameter("value1", username).getResultList();

                    return postEntities;
                }
                default: {
                    List<PostEntity> postEntities = this.em.createQuery("SELECT t FROM PostEntity t where "
                            + "TYPE(t) <> CommentEntity "
                            + "AND ("
                            + "((t.author.username = :value1 OR t.target.username = :value1) AND (TYPE(t) = MediaEntity OR TYPE(t) = NewsEntity))"
                            + " OR (t.target.username = :value1 AND TYPE(t) = RecomendationEntity)"
                            + ") order by t.id desc")
                            .setParameter("value1", username).getResultList();

                    return postEntities;
                }
            }

        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     *
     * @param usersID
     * @return
     */
    @Override
    public List<PostEntity> getRecentPostFromUsersID(List<Long> usersID) {
        List<PostEntity> postEntities = this.em.createQuery("SELECT t FROM PostEntity t where "
                + "TYPE(t) <> CommentEntity "
                + "AND ("
                + "((t.author.id IN (:inclList) OR t.target.id IN (:inclList)) AND ((TYPE(t) = MediaEntity) OR TYPE(t) = NewsEntity))"
                + " OR (t.target.id IN (:inclList) AND TYPE(t) = RecomendationEntity)"
                + ")"
                + " order by t.id desc")
                .setMaxResults(5)
                .setParameter("inclList", usersID).getResultList();

        return postEntities;
    }

    /**
     *
     * @param usersID
     * @param postID
     * @return
     */
    @Override
    public List<PostEntity> getNextPostFromUsersID(List<Long> usersID, Long postID) {
        List<PostEntity> postEntities = this.em.createQuery("SELECT t FROM PostEntity t where "
                + "TYPE(t) <> CommentEntity "
                + "AND (t.id < :postID ) AND ("
                + "((t.author.id IN (:inclList) OR t.target.id IN (:inclList)) AND ((TYPE(t) = MediaEntity ) OR TYPE(t) = NewsEntity))"
                + " OR (t.target.id IN (:inclList) AND TYPE(t) = RecomendationEntity)"
                + ")"
                + " order by t.id desc")
                .setParameter("postID", postID)
                .setParameter("inclList", usersID)
                .setMaxResults(5)
                .getResultList();

        return postEntities;
    }

    /**
     *
     * @param usersID
     * @param postID
     * @return
     */
    @Override
    public List<PostEntity> getNextRecommendationFromUsersID(List<Long> usersID, Long postID) {
        List<PostEntity> postEntities;
        postEntities = this.em.createQuery("SELECT t FROM PostEntity t where "
                + " TYPE(t) = RecomendationEntity"
                + " AND (t.id < :postID ) AND "
                + " (t.target.id IN (:inclList))"
                + " order by t.id desc")
                .setParameter("postID", postID)
                .setParameter("inclList", usersID)
                .setMaxResults(5)
                .getResultList();

        return postEntities;
    }

    /**
     *
     * @param usersID
     * @return
     */
    @Override
    public List<PostEntity> getRecentRecommendationFromUsersID(List<Long> usersID) {
        List<PostEntity> postEntities = this.em.createQuery("SELECT t FROM PostEntity t where "
                + "TYPE(t) = RecomendationEntity "
                + "AND"
                + " (t.target.id IN (:inclList))"
                + " order by t.id desc")
                .setMaxResults(5)
                .setParameter("inclList", usersID).getResultList();

        return postEntities;
    }

    /**
     *
     * @param userId
     * @param type
     * @return
     */
    @Override
    public PostEntity findAlbum(Long userId, String type) {
        try {
            Query q = this.em.createQuery("SELECT p FROM PostEntity p where p.author.id = :userId and TYPE(p) = AlbumEntity and p.title=:type");
            q.setParameter("userId", userId);
            q.setParameter("type", type);
            return (PostEntity) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     *
     * @param userId
     * @param albumId
     * @return
     */
    @Override
    public PostEntity findAlbum(Long userId, Long albumId) {
        try {
            Query q = this.em.createQuery("SELECT p FROM PostEntity p where p.author.id = :userId and p.id =:albumId");
            q.setParameter("userId", userId);
            q.setParameter("albumId", albumId);
            return (PostEntity) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
