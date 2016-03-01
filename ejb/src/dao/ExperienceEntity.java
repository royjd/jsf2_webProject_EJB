/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author zakaridia
 */
@Entity
@Table(name = "experiences")//TODO ASK ZAK ABOUT THE EXTENDS
public class ExperienceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date realisationDate;

    @Lob //Force to set the type of the column to clob in java db => TEXT in mysql
    private String description;

    @ManyToOne
    @JoinColumn(name = "profile_fk")
    private ProfileEntity profile;

    @OneToOne
    @JoinColumn(name = "localisation_id")
    private LocalisationEntity localisation;

    /**
     *
     */
    public ExperienceEntity() {

    }

    /**
     *
     * @param title the title of the experience
     * @param description the description of the experience
     * @param realisationDate
     */
    public ExperienceEntity(String title, String description, Date realisationDate) {
        this.title = title;
        this.description = description;
        this.realisationDate = realisationDate;
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

    /**
     *
     * @return
     */
    public Date getRealisationDate() {
        return realisationDate;
    }

    /**
     *
     * @param realisationDate
     */
    public void setRealisationDate(Date realisationDate) {
        this.realisationDate = realisationDate;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExperienceEntity)) {
            return false;
        }
        ExperienceEntity other = (ExperienceEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.ExperienceEntity[ id=" + id + " ]";
    }

    /**
     *
     * @return
     */
    public LocalisationEntity getLocalisation() {
        return localisation;
    }

    /**
     *
     * @param localisation
     */
    public void setLocalisation(LocalisationEntity localisation) {
        this.localisation = localisation;
    }

}
