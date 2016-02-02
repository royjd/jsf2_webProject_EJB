/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author zakaridia
 */
@Local
public interface PhotoDAO {

    /**
     *
     * @param photo
     * @return
     */
    public Long save(PhotoEntity photo);

    /**
     *
     * @param photo
     */
    public void update(PhotoEntity photo);

    /**
     *
     * @param id
     * @return
     */
    public PhotoEntity find(Long id);

    /**
     *
     * @param limit
     * @return
     */
    public List<PhotoEntity> find(int limit);

    /**
     *
     * @return
     */
    public List<PhotoEntity> findAll();

    /**
     *
     * @param photo
     */
    public void delete(PhotoEntity photo);
}
