/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.ExperienceEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author zakaridia
 */
@Local
public interface ExperienceService {

    /**
     * save the experience
     * @param e
     * @return true if ok else false
     */
    public boolean save(ExperienceEntity e);

    /**
     * update the experience
     * @param e ExperienceEntity
     */
    public void update(ExperienceEntity e);

    /**
     * delete the given experience
     * @param e ExperienceEntity
     */
    public void delete(ExperienceEntity e);

    /**
     * find the experience by id
     * @param id
     * @return ExperienceEntity
     */
    public ExperienceEntity findById(Long id);

    /**
     * find the experiences attached to the given profile id
     * 
     * @param profileId
     * @return List
     */
    public List<ExperienceEntity> findExperiencesForProfil(Long profileId);

    /**
     * return the last experience for the given profile id
     * 
     * @param profileId
     * @param limit
     * @return
     */
    public List<ExperienceEntity> findExperiencesForProfil(Long profileId , int limit);

    public ExperienceEntity findLastExperienceForUser(Long userID);

    public ExperienceEntity findLastExperienceForProfile(Long profileID);
    
}
