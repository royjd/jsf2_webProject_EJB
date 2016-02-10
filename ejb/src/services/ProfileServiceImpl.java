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

}
