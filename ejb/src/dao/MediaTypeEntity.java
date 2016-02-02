/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 *
 * @author zakaridia
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "media_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Media")
@Table(name = "Medias")
public class MediaTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String link;
    
    @OneToOne(mappedBy = "mediaType")
    private MediaEntity media;
    
    /**
     *
     */
    public MediaTypeEntity(){
        
    }

    /**
     *
     * @param link
     */
    public MediaTypeEntity(String link) {
        this.link = link;
    }

    /**
     *
     * @param link the link to the saved media
     * @param media 
     */
    public MediaTypeEntity(String link, MediaEntity media) {
        this.link = link;
        this.media = media;
    }

    /**
     *
     * @return
     */
    public MediaEntity getMedia() {
        return media;
    }

    /**
     *
     * @param media
     */
    public void setMedia(MediaEntity media) {
        this.media = media;
    }
    
    /**
     *
     * @return
     */
    public String getLink() {
        return link;
    }

    /**
     *
     * @param link
     */
    public void setLink(String link) {
        this.link = link;
    }
    
    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MediaTypeEntity)) {
            return false;
        }
        MediaTypeEntity other = (MediaTypeEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.Medias[ id=" + id + " ]";
    }
    
}
