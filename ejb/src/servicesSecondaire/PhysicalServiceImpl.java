/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.PhysicalDAO;
import dao.PhysicalEntity;
import dao.ProfileEntity;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author zdiawara
 */
@Stateless
public class PhysicalServiceImpl implements PhysicalService {

    @EJB
    PhysicalDAO physicalDao;

    /**
     *
     * @param p
     * @return
     */
    @Override
    public Boolean save(PhysicalEntity p) {

        if (p == null) {
            return false;
        }

        Long id = physicalDao.save(p);

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
    public void update(PhysicalEntity p) {
        physicalDao.update(p);
    }

    /**
     *
     * @param p
     */
    @Override
    public void delete(PhysicalEntity p) {
        physicalDao.delete(p);
    }

    /**
     *
     * @param profileId
     * @return
     */
    @Override
    public PhysicalEntity findByProfileId(Long profileId) {
        return physicalDao.findByProfileId(profileId);
    }

    @Override
    public PhysicalEntity createProfilePhysical(ProfileEntity p) {

        PhysicalEntity physic = new PhysicalEntity();
        //physic.setProfile(p);
        physicalDao.save(physic);

        return physic;
    }

}
