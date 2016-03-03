/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.AlbumEntity;
import dao.ExperienceEntity;
import dao.LocalisationEntity;
import dao.MediaEntity;
import dao.PhysicalEntity;
import dao.PostEntity;
import dao.ProfileDAO;
import dao.ProfileEntity;
import dao.UserEntity;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.Part;
import servicesSecondaire.PhotoService;
import servicesSecondaire.PostService2;
import servicesSecondaire.ProfileElementaire;
import servicesSecondaire.UserService2;

/**
 *
 * @author zakaridia
 */
@Stateless
public class ProfileServiceImpl implements ProfileService {

    @EJB
    PhotoService photoService2;

    @EJB
    PostService2 postService2;

    @EJB
    PostService postService;

    @EJB
    UserService2 userService2;

    @EJB
    ProfileElementaire profileElementaire;

    @Override
    public boolean defineProfilePicture(Part file, Long userId, String context) {
        return this.definePicture(file, userId, context, "profile");
    }

    @Override
    public boolean defineCoverPicture(Part file, Long userId, String context) {
        return this.definePicture(file, userId, context, "cover");
    }

    private boolean definePicture(Part file, Long userId, String context, String type) {
        UserEntity u = userService2.findByID(userId);
        if (u == null) {
            return false;
        }
        AlbumEntity album = postService2.findAlbum(u.getId(), "ProfileAlbum");
        if (album == null) {
            return false;
        }
        MediaEntity media = (MediaEntity) photoService2.createPhoto(album, u, file, context);
        PostEntity post = postService.createPost(media, u, u, false);
        photoService2.setAlbumCover(album, media);

        if (post == null) {
            return false;
        }

        ProfileEntity p = u.getProfile();
        switch (type) {
            case "cover":
                p.setPictureCover((MediaEntity) post);
                break;
            case "profile":
                p.setPictureProfile((MediaEntity) post);
                break;
        }
        profileElementaire.update(p);
        return true;
    }

    @Override
    public String coverUrltmp(String username) {
        UserEntity u = userService2.findByUsername(username);
        if (u == null) {
            return "";
        }
        return u.getProfile().getPictureCover().getMediaType().getLink();
    }

    @Override
    public void editProfile(String firstName, String lastName, String phone, String city, String country, String description, Date birthDay, Double height, Double weight, String gender, Long userId) {
        UserEntity u = userService2.findByID(userId);
        if (u != null) {

            ProfileEntity profile = u.getProfile();

            profile.setLastName(lastName);
            profile.setFirstName(firstName);
            profile.setDescription(description);
            profile.setPhone(phone);
            profile.setCity(city);
            profile.setCountry(country);
            profile.setBirthDay(birthDay);

            PhysicalEntity physical = profile.getPhysical();
            physical.setGender(gender);
            physical.setHeight(height);
            physical.setWeight(weight);

            profileElementaire.update(profile);
        }
    }

    @Override
    public void createExperience(Long userID, String title, String description, Date realisationDate, String experienceCity, String experienceCityStat, String experienceCityStreet, Integer experienceCityZipcode) {

        ExperienceEntity e = new ExperienceEntity(title, description, realisationDate);
        LocalisationEntity l = new LocalisationEntity(experienceCity, experienceCityStat, experienceCityStreet, experienceCityZipcode);
        e.setProfile(userService2.findByID(userID).getProfile()); // Set the profile 
        e.setLocalisation(l);
        profileElementaire.saveExperience(e);

    }

    @Override
    public void editExperience(Long experienceID, Long userID, String title, String description, Date realisationDate, String experienceCity, String experienceCityStat, String experienceCityStreet, Integer experienceCityZipcode) {
        ExperienceEntity e = profileElementaire.findExperienceByID(experienceID);
        if (e == null) {
            return;
        }
        e.setTitle(title);
        e.setDescription(description);
        e.setRealisationDate(realisationDate);
        LocalisationEntity l = e.getLocalisation();
        l.setCity(experienceCity);
        l.setStat(experienceCityStat);
        l.setStreet(experienceCityStreet);
        l.setZipcode(experienceCityZipcode);

        profileElementaire.updateExperience(e);
    }

    @Override
    public void deleteExperience(Long id, Long experienceId) {
        UserEntity ue = userService2.findByID(id);
        ExperienceEntity e = profileElementaire.findExperienceByID(experienceId);
        if (e != null && ue.getProfile().getId().equals(e.getProfile().getId())) {
            profileElementaire.deleteExperience(e);
        }

    }

}
