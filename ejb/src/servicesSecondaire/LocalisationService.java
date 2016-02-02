/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.LocalisationEntity;
import javax.ejb.Local;

/**
 *
 * @author zdiawara
 */
@Local
public interface LocalisationService {

    /**
     * save the given localization
     * @param l
     * @return the id of the saved localization
     */
    public Long save(LocalisationEntity l);

    /**
     * update the given localization
     * @param l
     */
    public void update(LocalisationEntity l);

    /**
     * delete the given localization
     * @param l
     */
    public void delete(LocalisationEntity l);

    /**
     * find the localization attached to the given experience id
     * @param experienceId
     * @return LocalisationEntity
     */
    public LocalisationEntity findForExperience(Long experienceId );
}
