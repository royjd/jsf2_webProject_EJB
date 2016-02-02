/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Diawara zakaridia  Karl Lauret
 */
@Local
public interface ExperienceDAO {

    /**
     *
     * @param e experience to save
     * @return the id of the saved experience
     */
    public Long save(ExperienceEntity e);

    /**
     *
     * @param e experience to update
     */
    public void update(ExperienceEntity e);

    /**
     *
     * @param e experience to delete
     */
    public void delete(ExperienceEntity e);

    /**
     *
     * @param id the ID of the experience to find
     * @return
     */
    public ExperienceEntity findById(Long id);

    /**
     * return the list of experiences attached to the given profile
     * 
     * @param profileId the profile id 
     * @return list of experiences
     */
    public List<ExperienceEntity> findExperiencesForProfil(Long profileId);

    /**
     * return the list of experiences attached to the given profile 
     * 
     * @param profileId the profile id
     * @param limit the number of element return 
     * @return list of experiences 
     */
    public List<ExperienceEntity> findExperiencesForProfil(Long profileId , int limit);

    /**
     * return the last experience for a given user id
     * 
     * @param userID the user's 
     * @return experience
     */
    public ExperienceEntity findLastExperienceForUser(Long userID);

    /**
     * return the last experience for the given profile id
     * 
     * @param profileID the profile id
     * @return experience
     */
    public ExperienceEntity findLastExperienceForProfile(Long profileID);
}
