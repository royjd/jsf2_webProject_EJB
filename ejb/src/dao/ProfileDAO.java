/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.ejb.Local;

/**
 *
 * @author zakaridia
 */
@Local
public interface ProfileDAO {

    /**
     *
     * @param p
     * @return
     */
    public Long save(ProfileEntity p);

    /**
     *
     * @param p
     */
    public ProfileEntity update(ProfileEntity p);

    /**
     *
     * @param p
     */
    public void delete(ProfileEntity p);

}
