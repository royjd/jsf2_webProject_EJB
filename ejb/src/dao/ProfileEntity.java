/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author zakaridia
 */
@Entity
@Table(name = "profiles")
public class ProfileEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    @Temporal(TemporalType.DATE)
    private Date birthDay;

    private String phone;

    private String description;

    private String country;

    private String city;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity profileOwner;

    @OneToOne
    @JoinColumn(name = "pictureProfile_id")
    private MediaEntity pictureProfile;
    
    @OneToOne
    @JoinColumn(name = "pictureCover_id")
    private MediaEntity pictureCover;

    @OneToMany(mappedBy = "profile")
    private List<ExperienceEntity> experiences = new ArrayList<>();

    @OneToOne(mappedBy = "profile")
    private PhysicalEntity physical;

    /**
     *
     * @param p
     */
    public void setData(ProfileEntity p) {
        this.description = p.getDescription();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.phone = p.getPhone();
        this.country = p.getCountry();
        this.city = p.getCity();
        this.birthDay = p.getBirthDay();
    }

    /**
     *
     * @return
     */
    public Date getBirthDay() {
        return birthDay;
    }

    /**
     *
     * @param birthDay
     */
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    /**
     *
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     */
    public UserEntity getProfileOwner() {
        return profileOwner;
    }

    /**
     *
     * @param profileOwner
     */
    public void setProfileOwner(UserEntity profileOwner) {
        this.profileOwner = profileOwner;
    }

    /**
     *
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
    public Long getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public MediaEntity getPictureProfile() {
        return pictureProfile;
    }

    /**
     *
     * @param pictureProfile
     */
    public void setPictureProfile(MediaEntity pictureProfile) {
        this.pictureProfile = pictureProfile;
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
    public List<ExperienceEntity> getExperiences() {
        return experiences;
    }

    /**
     *
     * @param experiences
     */
    public void setExperiences(List<ExperienceEntity> experiences) {
        this.experiences = experiences;
    }

    /**
     *
     * @param e
     */
    public void addExperience(ExperienceEntity e) {
        this.experiences.add(e);
    }

    /**
     *
     * @param e
     */
    public void RemoveExperience(ExperienceEntity e) {
        this.experiences.remove(e);
    }

    /**
     *
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
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
        if (!(object instanceof ProfileEntity)) {
            return false;
        }
        ProfileEntity other = (ProfileEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.ProfileEntity[ id=" + id + " ]";
    }

    /**
     *
     * @return
     */
    public PhysicalEntity getPhysical() {
        return physical;
    }

    /**
     *
     * @param physical
     */
    public void setPhysical(PhysicalEntity physical) {
        this.physical = physical;
    }

    /**
     *
     * @return
     */
    public MediaEntity getPictureCover() {
        return pictureCover;
    }

    /**
     *
     * @param pictureCover
     */
    public void setPictureCover(MediaEntity pictureCover) {
        this.pictureCover = pictureCover;
    }
    
    

}
