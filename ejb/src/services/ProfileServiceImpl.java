/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.ExperienceEntity;
import dao.PhysicalEntity;
import dao.ProfileDAO;
import dao.ProfileEntity;
import dao.UserEntity;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import servicesSecondaire.ExperienceService;
import servicesSecondaire.LocalisationService;
import servicesSecondaire.PhotoService;
import servicesSecondaire.PhysicalService;
import servicesSecondaire.PostService2;

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
    ProfileDAO profileDao;

    /**
     *
     * @param p
     * @return
     */
    @Override
    public Long save(ProfileEntity p) {
        return profileDao.save(p);
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
     * @param userId
     * @return
     */
    @Override
    public ProfileEntity findByUserId(Long userId) {
        return profileDao.findByUserId(userId);
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
    public void createUserProfile(UserEntity u) {
        
        
        ProfileEntity p = u.getProfile();
        
        PhysicalEntity physic = physicalService.createProfilePhysical(p);
        
        //set the profile Physic
        p.setPhysical(physic);
        
        //Set the profile owner
        p.setProfileOwner(u);
        
        //set profile default picture
        photoService2.createDefaultProfilePhotos(p);

        profileDao.update(p);
        
        

        

    }

}
