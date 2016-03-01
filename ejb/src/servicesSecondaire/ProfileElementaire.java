/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.ExperienceEntity;
import dao.ProfileEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Karl Lauret
 */
@Local
public interface ProfileElementaire {
    public Boolean save(ProfileEntity p);
    public void update(ProfileEntity p);
    public void delete(ProfileEntity p) ;
    public Boolean createProfile(ProfileEntity profile);

    public void saveExperience(ExperienceEntity e);

    public ExperienceEntity findExperienceByID(Long experienceID);

    public void updateExperience(ExperienceEntity e);

    public ExperienceEntity findLastExperienceByProfile(Long profileId);

    public ExperienceEntity findExperienceById(Long experienceId);

    public void deleteExperience(ExperienceEntity e);

    public List<ExperienceEntity> findProfileExperiences(Long id, int limit);

    public List<ExperienceEntity> findProfileExperiences(Long id);

}
