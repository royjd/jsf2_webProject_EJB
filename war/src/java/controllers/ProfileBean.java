/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.ExperienceEntity;
import dao.LocalisationEntity;
import dao.ProfileEntity;
import dao.UserEntity;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;
import services.ProfileService;
import services.UserService;
import servicesSecondaire.ProfileElementaire;
import servicesSecondaire.UserService2;

/**
 *
 * @author Karl Lauret
 */
@Named(value = "profileBean")
@ManagedBean
@ViewScoped
public class ProfileBean implements Serializable {

    //Profile 
    private String username;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String profilePicture;
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
    private String experienceCity;
    private String experienceCityStat;
    private String experienceCityStreet;
    private Integer experienceCityZipcode;

    private Long profileId;

    private Part file;

    private static final String realPath = "/home/SP2MI/zdiawara/Bureau/images";
    //private static final String realPath = "/home/zakaridia/Documents/Depot_Git/File/image";
    //private static final String realPath = "C:/Users/Karl Lauret/AppData/Roaming/NetBeans/8.1/config/GF_4.1.1/domain1/applications/images";

    @EJB
    ProfileService profileService;

    @EJB
    ProfileElementaire profileElementaire;

    @EJB
    UserService userService;

    @EJB
    UserService2 userElementaire;

    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    /**
     * Creates a new instance of ProfileBean
     */
    public ProfileBean() {
        this.experienceId = 0L;
    }

    public String editProfile() {
        Long id = SessionBean.getUserId();
        if (id != null) {
            profileService.editProfile(firstName, lastName, phone, city, country, briefDescription, birthDay, height, weight, gender, id);
            return navigationBean.profile(SessionBean.getUsername());
        }

        return "";// Error page  
    }

    public void loadProfile(Long ID) {

        load(userElementaire.findByID(ID));

        //Experience
        ExperienceEntity e = profileElementaire.findLastExperienceByProfile(this.profileId);
        if (e != null) {
            this.title = e.getTitle();
            this.description = e.getDescription();
            this.realisationDate = e.getRealisationDate();

            //localisation Experience
            this.experienceCity = e.getLocalisation().getCity();
        }
    }

    public void loadProfil(String username) {
        load(userElementaire.findByUsername(username));
    }

    private void load(UserEntity u) {
        if (u != null) {
            ProfileEntity p = u.getProfile();
            this.profileId = p.getId();
            this.city = p.getCity();
            this.country = p.getCountry();
            this.profilePicture = p.getPictureProfile().getMediaType().getLink();
            this.lastName = p.getLastName();
            this.firstName = p.getFirstName();
            this.email = u.getEmail();
            this.username = u.getUsername();
            this.phone = p.getPhone();
            this.briefDescription = p.getDescription();
            this.birthDay = p.getBirthDay();
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

        if (this.experienceId == 0) {
            profileService.createExperience(id,
                    this.title,
                    this.description,
                    this.realisationDate,
                    this.experienceCity,
                    this.experienceCityStat,
                    this.experienceCityStreet,
                    this.experienceCityZipcode);

        } else {
            profileService.editExperience(this.experienceId,
                    id,
                    this.title,
                    this.description,
                    this.realisationDate,
                    this.experienceCity,
                    this.experienceCityStat,
                    this.experienceCityStreet,
                    this.experienceCityZipcode);

        }

        return navigationBean.experience(SessionBean.getUsername());
    }

    public String removeExperience(Long expID) {

        if (SessionBean.isConnect()) {
            Long id = SessionBean.getUserId();
            profileService.deleteExperience(id, expID);
            return navigationBean.experience(SessionBean.getUsername());
        }
        return ""; // error page
    }

    public void loadExperience(Long id) {
        if (id != null && id != 0) {

            ExperienceEntity e = profileElementaire.findExperienceByID(id);
            if (e != null) {
                this.experienceId = e.getId();
                this.title = e.getTitle();
                this.description = e.getDescription();
                this.realisationDate = e.getRealisationDate();
                LocalisationEntity l = e.getLocalisation();
                if (l != null) {
                    this.experienceCity = l.getCity();
                    this.experienceCityStat = l.getStat();
                    this.experienceCityStreet = l.getStreet();
                    this.experienceCityZipcode = l.getZipcode();
                }
            }
        }
    }

    public List<ExperienceEntity> getExperiences(String username, int limit) {
        UserEntity u = userElementaire.findByUsername(username);
        if (u != null) {
            ProfileEntity p = u.getProfile();
            return profileElementaire.findProfileExperiences(p.getId(), limit);
        }
        return null;

    }

    public List<ExperienceEntity> getExperiences(String username) {
        UserEntity u = userElementaire.findByUsername(username);
        if (u != null) {
            ProfileEntity p = u.getProfile();
            return profileElementaire.findProfileExperiences(p.getId());
        }
        return null;
    }

    public String chooseCoverPicture(String username) {
        return profileService.coverUrltmp(username);
    }

    public String defineProfilePicture() {
        if (SessionBean.isConnect()) {
            profileService.defineProfilePicture(file, SessionBean.getUserId(), realPath);
        }
        return navigationBean.profile(SessionBean.getUsername());
    }

    public String defineCoverPicture() {
        if (SessionBean.isConnect()) {
            profileService.defineCoverPicture(file, SessionBean.getUserId(), realPath);
        }
        return navigationBean.profile(SessionBean.getUsername());
    }

    public boolean canModify(String username) {
        return SessionBean.isConnect() && SessionBean.getUsername().equals(username);
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
        return this.realisationDate;
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
        return this.birthDay;
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

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getFormatBirthDay(){
        return this.getFormatDate(this.birthDay);
    }
    
    public String getFormatDateRealisation(){
        return this.getFormatDate(this.realisationDate);
    }
    
    public String getFormatDate(Date date){
        
        if(date==null)
            return "";
        
        String DATE_FORMAT = "yyyy-dd-MM";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }    
}
