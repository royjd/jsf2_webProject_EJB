/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.FriendDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author klaure01
 */
@Stateless
public class FriendServiceImpl implements FriendService {

    @EJB
    FriendDAO friendDAO;
    
    @Override
    public List<Long> findUsersIdOfFriends(Long userID) {
        return friendDAO.findUsersIdOfFriends(userID);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
