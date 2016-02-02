/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author klaure01
 */
@Local
public interface FriendService {

    public List<Long> findUsersIdOfFriends(Long userID);
    
}
