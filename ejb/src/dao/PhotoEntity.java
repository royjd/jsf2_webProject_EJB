/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author zakaridia
 */
@Entity
@DiscriminatorValue("photo")
public class PhotoEntity extends MediaTypeEntity {
    
    private String caption;
        
    private boolean largeSource;
    
    private boolean mediumSource;

    private boolean smallSource;

    /**
     *
     */
    public PhotoEntity() {
        super();
    }

    /**
     *
     * @param caption
     * @param link
     */
    public PhotoEntity(String caption , String link) {
        super(link);
        this.caption = caption;
    }

    /**
     *
     * @param link
     */
    public PhotoEntity(String link) {
        super(link);
    }

    /**
     *
     * @return
     */
    public String getCaption() {
        return caption;
    }

    /**
     *
     * @param caption
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     *
     * @return
     */
    public boolean isLargeSource() {
        return largeSource;
    }

    /**
     *
     * @param largeSource
     */
    public void setLargeSource(boolean largeSource) {
        this.largeSource = largeSource;
    }

    /**
     *
     * @return
     */
    public boolean isMediumSource() {
        return mediumSource;
    }

    /**
     *
     * @param mediumSource
     */
    public void setMediumSource(boolean mediumSource) {
        this.mediumSource = mediumSource;
    }

    /**
     *
     * @return
     */
    public boolean isSmallSource() {
        return smallSource;
    }

    /**
     *
     * @param smallSource
     */
    public void setSmallSource(boolean smallSource) {
        this.smallSource = smallSource;
    }
    
    
}
