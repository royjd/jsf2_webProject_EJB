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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Karl Lauret Zakaridia Diawara
 */
@Entity
@DiscriminatorValue("Album")
public class AlbumEntity extends PostEntity {

    @OneToOne
    private MediaEntity cover;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "album")
    private List<MediaEntity> medias = new ArrayList<>();

    private String localisation;

    /**
     * Default constructor
     */
    public AlbumEntity() {
        super();
    }

    /**
     * Constructor
     *
     * @param title String the title of the Album
     * @param body String the description of the album
     * @param author UserEntity the author of the album
     */
    public AlbumEntity(String title, String body, UserEntity author) {
        super(title, body, author, author);
    }

    public AlbumEntity(String title, String body, String localisation, UserEntity author) {
        super(title, body, author, author);
        this.localisation = localisation;
    }

    /**
     * add a media to the media list of the album
     *
     * @param me MediaEntity
     */
    public void addMedia(MediaEntity me) {
        this.medias.add(me);
    }

//GETTER SETTER ================================================================
    /**
     *
     * @return MediaEntity
     */
    public MediaEntity getCover() {
        return cover;
    }

    /**
     *
     * @param cover MediaEntity
     */
    public void setCover(MediaEntity cover) {
        this.cover = cover;
    }

    /**
     *
     * @return List
     */
    public List<MediaEntity> getMedias() {
        return medias;
    }

    /**
     * Set the Album's list of media
     *
     * @param medias the medias of the album
     */
    public void setMedias(List<MediaEntity> medias) {
        this.medias = medias;
    }

    /**
     *
     * @return String
     */
    public String getLocalisation() {
        return localisation;
    }

    /**
     *
     * @param localisation String
     */
    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

}
