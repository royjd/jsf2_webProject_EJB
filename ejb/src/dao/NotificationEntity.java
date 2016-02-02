/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Karl Lauret
 */
@Entity
@DiscriminatorValue("Notification")
public class NotificationEntity extends MessageEntity {

    private static final long serialVersionUID = 1L;

    @OneToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    /**
     *
     */
    public NotificationEntity() {
        super();
    }

    /**
     *
     * @param post the post which triggered the Notification
     * @param subject 
     * @param sendBy the author of the post which triggered the notification
     */
    public NotificationEntity(PostEntity post, String subject, UserEntity sendBy) {
        super(null, subject, sendBy);
        this.post = post;
    }

    /**
     * return the display of the notification
     * @return String
     */
    @Override
    public String getContent() {
        if (post == null) {
            return null;
        } else if (this.post instanceof NewsEntity) {
            return this.getNewsContent();
        } else if (this.post instanceof MediaEntity) {
            return this.getMediaContent();
        } else if (this.post instanceof AlbumEntity) {
            return this.getAlbumContent();
        } else if (this.post instanceof CommentEntity) {
            return this.getCommentContent();
        } else if (this.post instanceof RecomendationEntity) {
            return this.getRecomendationContent();
        } else {
            return null;
        }
    }

    /**
     * return the type of the notification
     * @return
     */
    public String getType() {
        if (post == null) {
            return null;
        } else if (this.post instanceof NewsEntity) {
            return "News";
        } else if (this.post instanceof MediaEntity) {
            return "Media";
        } else if (this.post instanceof AlbumEntity) {
            return "Album";
        } else if (this.post instanceof CommentEntity) {
            return "Comment";
        } else if (this.post instanceof RecomendationEntity) {
            return "Recomendation";
        } else {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public PostEntity getPost() {
        return post;
    }

    /**
     *
     * @param post
     */
    public void setPost(PostEntity post) {
        this.post = post;
    }
//DEFINE THE DISPLAY FOR EACH TYPE 
//TODO put it into jsp files
    private String getNewsContent() {
        return "<h2>News OMG</h2>" + this.post.getTitle() + " @"+this.post.getAuthor().getUsername() + " To " + " @"+this.post.getTarget().getUsername();
    }

    private String getMediaContent() {
        return "<h2>Media OMG</h2>" + " @"+this.post.getAuthor().getUsername() + " Added a Photo";
    }

    private String getAlbumContent() {
        return "<h2>Album OMG</h2>" + this.post.getTitle() + " @"+this.post.getAuthor().getUsername() + " Added an Album";
    }

    private String getCommentContent() {
        return "<h2>Comment OMG</h2>" + this.post.getBody() + " @"+this.post.getAuthor().getUsername() + " Commented the post of @"+ this.post.getTarget().getUsername();
    }

    private String getRecomendationContent() {
        return "<h2>Recomendation OMG</h2>" + this.post.getBody()+ " @"+this.post.getAuthor().getUsername() + " Recommented @"+ this.post.getTarget().getUsername();
    }
}
