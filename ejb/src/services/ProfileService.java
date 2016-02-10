/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.ExperienceEntity;
import dao.ProfileEntity;
import dao.UserEntity;
import javax.ejb.Local;

/**
 *
 * @author zakaridia
 */
@Local
public interface ProfileService {

    /**
     * create the profile 
     * @param p
     * @return the profile id created
     */
    public Boolean save(ProfileEntity p);

    /**
     * update the profile given
     * @param p
     */
    public void update(ProfileEntity p);

    /**
     * delete the profile given
     * @param p
     */
    public void delete(ProfileEntity p);


    /**
     * get the last experience of a user
     * 
     * @param userID
     * @return ExperienceEntity
     */
    public ExperienceEntity getLastExperienceByUser(Long userID);

    /**
     * get the last experience of the given profile'id
     * 
     * @param profileID
     * @return ExperienceEntity
     */
    public ExperienceEntity getLastExperienceByProfile(Long profileID);

    public Boolean createProfile(ProfileEntity profile);
    
}
