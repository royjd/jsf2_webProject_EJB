/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
/**
 *
 * @author Karl Lauret
 */
@Entity
@DiscriminatorValue("News")
public class NewsEntity extends PostEntity {


    @OneToOne
    @JoinColumn(name = "media_news_id")
    private MediaEntity media;

    /**
     *
     */
    public NewsEntity() {
        super();
    }

    /**
     *
     * @param title the title of the news
     * @param body the body of the nows
     * @param author the author of the news
     * @param target the target of the news
     * @param media the media attached to the news
     */
    public NewsEntity(String title, String body, UserEntity author, UserEntity target, MediaEntity media) {
        super(title, body, author, target);
        this.media = media;
    }

    /**
     *
     * @param title the title of the news
     * @param body the body of the nows
     * @param author the author of the news
     * @param target the target of the news
     */
    public NewsEntity(String title, String body, UserEntity author, UserEntity target) {
        super(title, body, author, target);
        this.media = null;
    }
    /**
     *
     * @return
     */
    public MediaEntity getMedia() {
        return this.media;
    }

    /**
     *
     * @param media
     */
    public void setMedias(MediaEntity media) {
        this.media = media;
    }

}
