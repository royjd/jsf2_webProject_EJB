/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.ExperienceDAO;
import dao.ExperienceEntity;
import dao.LocalisationDAO;
import dao.LocalisationEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author zakaridia
 */
@Stateless
public class ExperienceServiceImpl implements ExperienceService {
    @EJB
    ExperienceDAO experienceDao;
    
    @EJB
    LocalisationDAO localisationDao;
    
    /**
     * 
     * @param e
     * @return
     */
    @Override
    public boolean save(ExperienceEntity e){
        LocalisationEntity localisation = e.getLocalisation();
        Long id = localisationDao.save(localisation);
        localisation.setId(id);   
        experienceDao.save(e);
        return true;
    }

    /**
     *
     * @param profileId
     * @return
     */
    @Override
    public List<ExperienceEntity> findExperiencesForProfil(Long profileId) {
        return experienceDao.findExperiencesForProfil(profileId);
    }

    /**
     *
     * @param e
     */
    @Override
    public void update(ExperienceEntity e) {
        experienceDao.update(e);
    }

    /**
     *
     * @param e
     */
    @Override
    public void delete(ExperienceEntity e){
        experienceDao.delete(e);
        localisationDao.delete(e.getLocalisation());
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ExperienceEntity findById(Long id) {
        return experienceDao.findById(id);
    }

    /**
     *
     * @param profileId
     * @param limit
     * @return
     */
    @Override
    public List<ExperienceEntity> findExperiencesForProfil(Long profileId, int limit) {
        return experienceDao.findExperiencesForProfil(profileId, limit);
    }

    @Override
    public ExperienceEntity findLastExperienceForUser(Long userID) {
        return experienceDao.findLastExperienceForUser(userID);
    }

    @Override
    public ExperienceEntity findLastExperienceForProfile(Long profileID) {
        return experienceDao.findLastExperienceForProfile(profileID);
    }
    
}
