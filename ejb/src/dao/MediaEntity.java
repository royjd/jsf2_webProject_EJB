/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Karl Lauret
 */
@Entity
@DiscriminatorValue("Media")
public class MediaEntity extends PostEntity {

    @ManyToOne
    @JoinColumn(name = "media_album_id")
    private AlbumEntity album;

    @OneToOne(mappedBy = "media")
    private NewsEntity news;

    @OneToOne
    @JoinColumn(name = "media_type_id")
    private MediaTypeEntity mediaType;

    @OneToOne(mappedBy = "pictureProfile")
    private ProfileEntity profile;

    private String localisation;

    /**
     *
     */
    public MediaEntity() {
        super();
    }

    /**
     *
     * @param title the title of the media
     * @param body the description of the media
     * @param author the author of the media
     */
    public MediaEntity(String title, String body, UserEntity author) {
        super(title, body, author, author);
          this.news =null;
    }

   /* @Override
    public String getHomeBootstrapDisplay() {

        return "<div>" + this.getWallBootstrapHeaderDisplay()
                + "<p>" + this.getBody() + "<p>"
                + "<a href=\"#\">"
                + "            <div class=\"img-overlay\">"
                + "                <img src=\"http://placehold.it/500x500\" class=\"img-responsive\"/>"
                + "            </div>"
                + "</a>"

                +"</div>";
        
    }*/

    /**
     *
     * @return
     */
    public String getPhotoDisplay() {
        return "            <div class=\"img-overlay\">"
                + "                     <a href=\"#\"><img src=\"http://placehold.it/500x500\" class=\"img-responsive\"/></a>"
                + "                      <div class=\"text project-overlay\" style=\"text-align:center;display:none;\">"
                + "                         <a href=\"#\"><h4>this.getAlbum().getTitle()</h4></a>"
                + "                         <p>" + this.getBody() + "</p>"
                + "                     </div>"
                + "            </div>";
    }

    /**
     *
     * @return
     */
    public AlbumEntity getAlbum() {
        return album;
    }

    /**
     *
     * @param album
     */
    public void setAlbum(AlbumEntity album) {
        this.album = album;
    }

    /**
     *
     * @return
     */
    public NewsEntity getNews() {
        return news;
    }

    /**
     *
     * @return
     */
    public ProfileEntity getProfile() {
        return profile;
    }

    /**
     *
     * @param profile
     */
    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
    }

    /**
     *
     * @param news
     */
    public void setNews(NewsEntity news) {
        this.news = news;
    }

    /**
     *
     * @return
     */
    public boolean isPhoto() {
        return true;
    }

    /**
     *
     * @return
     */
    public MediaTypeEntity getMediaType() {
        return mediaType;
    }

    /**
     *
     * @param mediaType
     */
    public void setMediaType(MediaTypeEntity mediaType) {
        this.mediaType = mediaType;
    }

    /**
     *
     * @return
     */
    public String getLocalisation() {
        return localisation;
    }

    /**
     *
     * @param localisation
     */
    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

}
