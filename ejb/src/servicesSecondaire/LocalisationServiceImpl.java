/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.LocalisationDAO;
import dao.LocalisationEntity;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author zdiawara
 */
@Stateless
public class LocalisationServiceImpl implements LocalisationService {

    @EJB
    LocalisationDAO localisationDao;
    
    /**
     *
     * @param l
     * @return
     */
    @Override
    public Long save(LocalisationEntity l) {
        return localisationDao.save(l);
    }

    /**
     *
     * @param l
     */
    @Override
    public void update(LocalisationEntity l) {
        localisationDao.update(l);
    }

    /**
     *
     * @param l
     */
    @Override
    public void delete(LocalisationEntity l) {
        localisationDao.delete(l);
    }

    /**
     *
     * @param experienceId
     * @return
     */
    @Override
    public LocalisationEntity findForExperience(Long experienceId) {
        return localisationDao.findForExperience(experienceId);
    }
    
}
