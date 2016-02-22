/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.ExperienceEntity;
import dao.LocalisationEntity;
import dao.PhysicalEntity;
import dao.ProfileEntity;
import dao.UserEntity;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import services.ProfileService;
import services.UserService;
import servicesSecondaire.ExperienceService;
import servicesSecondaire.LocalisationService;
import servicesSecondaire.PhysicalService;

/**
 *
 * @author Karl Lauret
 */
@Named(value = "profileBean")
@ManagedBean
@ViewScoped
public class ProfileBean {

    //Profile
    private String username;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String profilePicture;
    private String coverPicture;
    private String briefDescription;
    private Date birthDay;

    //Physical
    private String gender;
    private Double height;
    private Double weight;

    //Experience
    private Long experienceId;
    private String title;
    private String description;
    private Date realisationDate;

    //localisation Experience
    private Long localisationId;
    private String experienceCity;
    private String experienceCityStat;
    private String experienceCityStreet;
    private Integer experienceCityZipcode;

    private Long profileId;

    @EJB
    ProfileService profileService;

    @EJB
    PhysicalService PhysicalService;

    @EJB
    ExperienceService experienceService;
    
    @EJB
    LocalisationService localisationService;

    @EJB
    UserService userService;

    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    /**
     * Creates a new instance of ProfileBean
     */
    public ProfileBean() {
        this.experienceId = 0L;
        this.localisationId = 0L;
    }

    public String editProfile() {
        Long id = SessionBean.getUserId();
        if (id != null) {
            UserEntity u = userService.findByID(id);
            ProfileEntity p = u.getProfile();

            // Set data about profile
            p.setCountry(country);
            p.setCity(city);
            p.setLastName(lastName);
            p.setFirstName(firstName);
            p.setBirthDay(birthDay);
            p.setPhone(phone);
            p.setDescription(briefDescription);

            profileService.update(p); // Update the profile

            //Set data about physic
            PhysicalEntity physic = p.getPhysical();
            physic.setGender(gender);
            physic.setHeight(height);
            physic.setWeight(weight);

            PhysicalService.update(physic); // Update the physic 

            return navigationBean.profile(SessionBean.getUsername());
        }

        return "";// Error page  
    }

    public void loadProfile(Long ID) {

        load(userService.findByID(ID));

        //Experience
        ExperienceEntity e = profileService.getLastExperienceByProfile(this.profileId);
        if (e != null) {
            this.title = e.getTitle();
            this.description = e.getDescription();
            this.realisationDate = e.getRealisationDate();

            //localisation Experience
            this.experienceCity = e.getLocalisation().getCity();
        }
    }

    public void loadProfil(String username) {
        load(userService.findByUsername(username));
    }

    private void load(UserEntity u) {
        if (u != null) {
            ProfileEntity p = u.getProfile();
            this.profileId = p.getId();
            this.city = p.getCity();
            this.country = p.getCountry();
            this.profilePicture = p.getPictureProfile().getMediaType().getLink();
            this.coverPicture = p.getPictureCover().getMediaType().getLink();
            this.lastName = p.getLastName();
            this.firstName = p.getFirstName();
            this.email = u.getEmail();
            this.username = u.getUsername();
            this.phone = p.getPhone();
            this.briefDescription = p.getDescription();
            //Physical
            this.gender = p.getPhysical().getGender();
            this.height = p.getPhysical().getHeight();
            this.weight = p.getPhysical().getWeight();
        } else {
            //error page
        }

    }

    public String manageExperience() {
        Long id = SessionBean.getUserId();
        if (id == null) {
            // Not connected
        }
        ExperienceEntity e = new ExperienceEntity(this.title, this.description, this.realisationDate);
        LocalisationEntity l = new LocalisationEntity(this.experienceCity, this.experienceCityStat, this.experienceCityStreet, this.experienceCityZipcode);
        e.setProfile(userService.findByID(id).getProfile()); // Set the profile 
        if (this.experienceId == 0) {
            e.setLocalisation(l);
            experienceService.save(e);
        } else {
            e.setId(this.experienceId);
            l.setId(this.localisationId);
            e.setLocalisation(l);
            experienceService.update(e);
            localisationService.update(l);
        }
        return navigationBean.experience(SessionBean.getUsername());
    }

    public String removeExperience() {
        Long id = SessionBean.getUserId();
        if (id != null) {
            ExperienceEntity e = experienceService.findById(this.experienceId);
            if(e!=null)
                experienceService.delete(e);
            return navigationBean.experience(SessionBean.getUsername());
        }
        return ""; // error page
    }

    public void loadExperience(Long id) {
        if (id != null && id != 0) {

            ExperienceEntity e = experienceService.findById(id);
            if (e != null) {
                this.experienceId = e.getId();
                this.title = e.getTitle();
                this.description = e.getDescription();
                this.realisationDate = e.getRealisationDate();
                LocalisationEntity l = e.getLocalisation();
                if (l != null) {
                    this.localisationId = l.getId();
                    this.experienceCity = l.getCity();
                    this.experienceCityStat = l.getStat();
                    this.experienceCityStreet = l.getStreet();
                    this.experienceCityZipcode = l.getZipcode();
                }
            }
        }
    }

    public List<ExperienceEntity> getExperiences(String username, int limit) {
        UserEntity u = userService.findByUsername(username);
        if (u != null) {
            ProfileEntity p = u.getProfile();
            return profileService.getProfileExperiences(p.getId(), limit);
        }
        return null;

    }

    public List<ExperienceEntity> getExperiences(String username) {
        UserEntity u = userService.findByUsername(username);
        if (u != null) {
            ProfileEntity p = u.getProfile();
            return profileService.getProfileExperiences(p.getId());
        }
        return null;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public ProfileService getProfileService() {
        return profileService;
    }

    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRealisationDate() {
        return realisationDate;
    }

    public void setRealisationDate(Date realisationDate) {
        this.realisationDate = realisationDate;
    }

    public String getExperienceCity() {
        return experienceCity;
    }

    public void setExperienceCity(String experienceCity) {
        this.experienceCity = experienceCity;
    }

    public String getExperienceCityStat() {
        return experienceCityStat;
    }

    public void setExperienceCityStat(String experienceCityStat) {
        this.experienceCityStat = experienceCityStat;
    }

    public String getExperienceCityStreet() {
        return experienceCityStreet;
    }

    public void setExperienceCityStreet(String experienceCityStreet) {
        this.experienceCityStreet = experienceCityStreet;
    }

    public Integer getExperienceCityZipcode() {
        return experienceCityZipcode;
    }

    public void setExperienceCityZipcode(Integer experienceCityZipcode) {
        this.experienceCityZipcode = experienceCityZipcode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    public Long getExperienceId() {
        return experienceId;
    }

}
