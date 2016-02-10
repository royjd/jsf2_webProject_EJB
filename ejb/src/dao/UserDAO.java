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
 * @author Karl Lauret
 */
@Local
public interface UserDAO {

    /**
     *
     * @param u
     * @return
     */
    public Long save(UserEntity u);

    /**
     *
     * @param u
     */
    public void update(UserEntity u);

    /**
     *
     * @param u
     */
    public void delete(UserEntity u);

    /**
     *
     * @param id
     * @return
     */
    public UserEntity findByID(Long id);

    /**
     *
     * @param email
     * @return
     */
    public UserEntity findByEmail(String email);
    
    /**
     *
     * @param username
     * @return
     */
    public UserEntity findByUsername(String username);

    /*public void addFriend(UserEntity friend, UserEntity owner, FriendEntity fe);*/

    /**
     * return the users with an email or a username matching the given param
     * 
     * @param param
     * @return
     */
    public List<UserEntity> findBysearch(String param);


    public UserEntity connect(String identifiant, String password);
}
