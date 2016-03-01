/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.ExperienceDAO;
import dao.ExperienceEntity;
import dao.LocalisationDAO;
import dao.PhysicalDAO;
import dao.ProfileDAO;
import dao.ProfileEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Karl Lauret
 */
@Stateless
public class ProfileElementaireImpl implements ProfileElementaire {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    ProfileDAO profileDao;

    @EJB
    PhysicalDAO physicalDao;

    @EJB
    ExperienceDAO experienceDao;

    @EJB
    LocalisationDAO localisationDao;

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

    @Override
    public Boolean createProfile(ProfileEntity profile) {
        return physicalDao.save(profile.getPhysical()) != null && this.save(profile);
    }

    @Override
    public void saveExperience(ExperienceEntity e) {
        experienceDao.save(e);
    }

    @Override
    public ExperienceEntity findExperienceByID(Long experienceID) {
        return experienceDao.findById(experienceID);
    }

    @Override
    public void updateExperience(ExperienceEntity e) {
        experienceDao.update(e);
        localisationDao.update(e.getLocalisation());
    }

    @Override
    public ExperienceEntity findLastExperienceByProfile(Long profileId) {
        return experienceDao.findLastExperienceForProfile(profileId);
    }

    @Override
    public ExperienceEntity findExperienceById(Long experienceId) {
        return experienceDao.findById(experienceId);
    }

    @Override
    public void deleteExperience(ExperienceEntity e) {
        experienceDao.delete(e);
    }

    @Override
    public List<ExperienceEntity> findProfileExperiences(Long id, int limit) {
        return experienceDao.findExperiencesForProfil(id, limit);
    }

    @Override
    public List<ExperienceEntity> findProfileExperiences(Long id) {
        return experienceDao.findExperiencesForProfil(id);
    }

}
