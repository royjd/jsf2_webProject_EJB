/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.PhysicalEntity;
import dao.ProfileEntity;
import javax.ejb.Local;

/**
 *
 * @author zdiawara
 */
@Local
public interface PhysicalService {

    /**
     * save the given PhisicalEntity 
     * @param p
     * @return Long id of the physicalEntity
     */
    public Boolean save(PhysicalEntity p);

    /**
     * update the physical given
     * 
     * @param p
     */
    public void update(PhysicalEntity p);

    /**
     * delete the physical given
     * @param p
     */
    public void delete(PhysicalEntity p);

    /**
     * return the physical matching the given profileid
     * 
     * @param profileId
     * @return PhysicalEntity
     */
    public PhysicalEntity findByProfileId(Long profileId);

    public PhysicalEntity createProfilePhysical(ProfileEntity p);
}
