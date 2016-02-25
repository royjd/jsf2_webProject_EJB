/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.AlbumEntity;
import dao.ExperienceEntity;
import dao.MediaEntity;
import dao.PhysicalEntity;
import dao.PostEntity;
import dao.ProfileDAO;
import dao.ProfileEntity;
import dao.UserEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.Part;
import servicesSecondaire.ExperienceService;
import servicesSecondaire.LocalisationService;
import servicesSecondaire.PhotoService;
import servicesSecondaire.PhysicalService;
import servicesSecondaire.PostService2;
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
    LocalisationService localisationService;

    @EJB
    ExperienceService experienceService;

    @EJB
    PhysicalService physicalService;

    @EJB
    PostService2 postService2;

    @EJB
    UserService2 userService2;

    @EJB
    ProfileDAO profileDao;

    /**
     *
     * @param p
     * @return
     */
    @Override
    public Boolean save(ProfileEntity p) {
        if (p == null) {
            return false;
        }

        Long id = profileDao.save(p);

        if (id == null) {
            return false;
        }

        p.setId(id);

        return true;
    }

    /**
     *
     * @param p
     */
    @Override
    public void update(ProfileEntity p) {
        profileDao.update(p);
    }

    /**
     *
     * @param p
     */
    @Override
    public void delete(ProfileEntity p) {
        profileDao.delete(p);
    }

    /**
     *
     * @param userID
     * @return
     */
    @Override
    public ExperienceEntity getLastExperienceByUser(Long userID) {
        return experienceService.findLastExperienceForUser(userID);
    }

    /**
     *
     * @param profileID
     * @return
     */
    @Override
    public ExperienceEntity getLastExperienceByProfile(Long profileID) {
        return experienceService.findLastExperienceForProfile(profileID);
    }

    @Override
    public Boolean createProfile(ProfileEntity profile) {
        return physicalService.save(profile.getPhysical()) && this.save(profile);
    }

    @Override
    public List<ExperienceEntity> getProfileExperiences(Long profileID, int limit) {
        return experienceService.findExperiencesForProfil(profileID, limit);
    }

    @Override
    public List<ExperienceEntity> getProfileExperiences(Long profileID) {
        return experienceService.findExperiencesForProfil(profileID);
    }

    @Override
    public boolean defineProfilePicture(Part file, Long userId, String context) {
        return  this.definePicture(file, userId, context, "profile");
    }

    @Override
    public boolean defineCoverPicture(Part file, Long userId, String context) {
        return  this.definePicture(file, userId, context, "cover");
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
        PostEntity post = photoService2.createPhoto(album, u, file, context, false);
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
        profileDao.update(p);
        return true;
    }

    @Override
    public String coverUrl(String username) {
        UserEntity u = userService2.findByUsername(username);
        if(u == null){
            return "";
        }
        return u.getProfile().getPictureCover().getMediaType().getLink();
    }

}
