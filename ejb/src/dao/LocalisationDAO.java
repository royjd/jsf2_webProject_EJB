/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.ejb.Local;

/**
 *
 * @author zdiawara
 */
@Local
public interface LocalisationDAO {

    /**
     *
     * @param l
     * @return
     */
    public Long save(LocalisationEntity l);

    /**
     *
     * @param l
     */
    public void update(LocalisationEntity l);

    /**
     *
     * @param l
     */
    public void delete(LocalisationEntity l);

    /**
     * return the localization for a given experience id
     * @param experienceId
     * @return
     */
    public LocalisationEntity findForExperience(Long experienceId );
}
