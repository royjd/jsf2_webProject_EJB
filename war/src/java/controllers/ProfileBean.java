/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.ExperienceEntity;
import dao.ProfileEntity;
import dao.UserEntity;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import services.ProfileService;
import services.UserService;

/**
 *
 * @author Karl Lauret
 */
@Named(value = "profileBean")
@ManagedBean
@RequestScoped
public class ProfileBean {

    //Profile
    private String username;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String city;
    private String Country;
    private String profilePicture;
    private String coverPicture;

    //Physical
    private String gender;
    private Double height;
    private Double weight;

    //Experience
    private String title;
    private String description;
    private Date realisationDate;

    //localisation Experience
    private String experienceCity;
    private String experienceCityStat;
    private String experienceCityStreet;
    private Integer experienceCityZipcode;

    @EJB
    ProfileService profileService;
    
    @EJB
    UserService userService;

    /**
     * Creates a new instance of ProfileBean
     */
    public ProfileBean() {
    }

    public void loadProfile(Long ID) {
        load(userService.findByID(ID));
    }
    
    public void loadProfil(String username){
        load(userService.findByUsername(username));       
    }
    
    private void load(UserEntity u){
        if(u==null){
            // error page
        }
        ProfileEntity p = u.getProfile();
        this.city = p.getCity();
        this.Country = p.getCountry();
        this.profilePicture = p.getPictureProfile().getMediaType().getLink();
        this.coverPicture = p.getPictureCover().getMediaType().getLink();
        this.lastName = p.getLastName();
        this.firstName = p.getFirstName();
        this.email = u.getEmail();
        this.username = u.getUsername();
        
        //Physical
        this.gender = p.getPhysical().getGender();
        this.height = p.getPhysical().getHeight();
        this.weight = p.getPhysical().getWeight();
        
        //Experience
        ExperienceEntity e = profileService.getLastExperienceByProfile(p.getId());
        if(e!=null){
            this.title = e.getTitle();
            this.description = e.getDescription();
            this.realisationDate = e.getRealisationDate();

            //localisation Experience
            this.experienceCity = e.getLocalisation().getCity();
        }

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
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

    
    
}
