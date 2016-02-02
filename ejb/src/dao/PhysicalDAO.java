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
public interface PhysicalDAO {

    /**
     *
     * @param p
     * @return
     */
    public Long save(PhysicalEntity p);

    /**
     *
     * @param p
     */
    public void update(PhysicalEntity p);

    /**
     *
     * @param p
     */
    public void delete(PhysicalEntity p);

    /**
     * return the physical attribute of the given profile
     * 
     * @param profileId
     * @return PhysicalEntity
     */
    public PhysicalEntity findByProfileId(Long profileId);
}
